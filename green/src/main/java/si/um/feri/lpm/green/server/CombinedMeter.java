package si.um.feri.lpm.green.server;

import eflect.Eflect;
import eflect.data.EnergyFootprint;

import java.time.Duration;
import java.time.Instant;


public class CombinedMeter implements Meter {

    public synchronized Measurements measure(Runnable runnable) {
        GreenLogger.get().info(String.format("green: start thread %s gc total %d max %d free %d",
                Thread.currentThread().getName(),
                Runtime.getRuntime().totalMemory(),
                Runtime.getRuntime().maxMemory(),
                Runtime.getRuntime().freeMemory()));

        Eflect.getInstance().start();
        Instant start = Instant.now();
        runnable.run();
        Instant end = Instant.now();
        Eflect.getInstance().stop();
        GreenLogger.get().info(String.format("green: stop thread %s gc total %d max %d free %d",
                Thread.currentThread().getName(),
                Runtime.getRuntime().totalMemory(),
                Runtime.getRuntime().maxMemory(),
                Runtime.getRuntime().freeMemory()));

        final var samples = Eflect.getInstance().read();
        double energy = 0;
        for (EnergyFootprint footprint : samples) {
            energy += footprint.energy;
        }

        final long time = Duration.between(start, end).toMillis();
        GreenLogger.get().info(String.format("green: energy %f samples %d time %d", energy, samples.size(), time));

        return new Measurements(energy, time);
    }
}
