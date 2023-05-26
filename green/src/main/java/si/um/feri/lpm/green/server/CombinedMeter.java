package si.um.feri.lpm.green.server;

import eflect.Eflect;
import eflect.data.EnergyFootprint;

import java.time.Duration;
import java.time.Instant;


public class CombinedMeter implements Meter {

    public Measurements measure(Runnable runnable) {
        Eflect.getInstance().start(4);
        Instant start = Instant.now();
        runnable.run();
        Instant end = Instant.now();
        Eflect.getInstance().stop();

        double energy = 0;
        for (EnergyFootprint footprint : Eflect.getInstance().read()) {
            energy += footprint.energy;
        }
        long time = Duration.between(start, end).toMillis();
        return new Measurements(energy, time);
    }
}
