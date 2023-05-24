package si.um.feri.lpm.green.server;

import eflect.Eflect;
import eflect.data.EnergyFootprint;

import java.time.Duration;
import java.time.Instant;


public class CombinedMeter implements Meter {

    public Measurements measure(Runnable runnable) {
        Eflect.getInstance().start();
        Instant start = Instant.now();
        runnable.run();
        try {
            Thread.sleep(64);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
