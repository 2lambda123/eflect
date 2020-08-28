package clerk.attribution;

import static jrapl.util.EnergyCheckUtils.SOCKETS;

import chappie.attribution.EnergyAttributer;
import chappie.profiling.Sample;
import chappie.profiling.Profile;
import chappie.profiling.TimestampedSample;
import clerk.sampling.tasks.TasksSample;
import clerk.sampling.cpu.CPUSample;
import java.time.Instant;
import java.util.ArrayList;
import java.util.TreeMap;

/**
* Computes an estimate of application energy consumption from data across the
* runtime. This implements the same logic used in our data processing codebase
* (src/python/attribution) to compute runtime attribution. Data is stored in
* a typed collection and picked up by a processing method.
*/
public final class ClerkEnergyAttributer implements EnergyAttributer {
  private final TreeMap<Instant, PreAttribution> data = new TreeMap<>();

  /** Puts the data in relative timestamp-indexed storage to keep things sorted. */
  @Override
  public void add(Sample s) {
    if (s instanceof TimestampedSample) {
      synchronized(this) {
        Instant timestamp = ((TimestampedSample) s).getTimestamp();
        data.putIfAbsent(timestamp, new PreAttribution());
        data.get(timestamp).add(s);
      }
    }
  }

  @Override
  public Iterable<Profile> process() {
    int attempts = 0;
    ArrayList<Profile> attributions = new ArrayList<>();
    PreAttribution preAttributer = new PreAttribution();
    synchronized (this) {
      for (Instant timestamp: data.keySet()) {
        preAttributer = preAttributer.merge(data.get(timestamp));
        // if (preAttributer.valid()) {
        if (preAttributer.valid() || (attempts++ >= 25 && preAttributer.attributable())) {
          attributions.add(preAttributer.process());
          preAttributer = new PreAttribution();
          attempts = 0;
        }
      }
      data.clear();
      if (preAttributer.attributable()) {
        attributions.add(preAttributer.process());
      } else {
        data.put(preAttributer.getTimestamp(), preAttributer);
      }
    }
    return attributions;
  }
}
