package si.um.feri.lpm.green.server;

import eflect.Eflect;
import eflect.data.EnergyFootprint;

import java.time.Duration;
import java.time.Instant;

class Accumulator implements Runnable {
    private static final long WAIT_TIME_MS = 60000;

    private double energy;
    private int samplesCount;

    public Accumulator() {
        this.energy = 0;
        this.samplesCount = 0;
    }

    public void update() {
        final var samples = Eflect.getInstance().read();
        samplesCount += samples.size();
        for (EnergyFootprint footprint : samples) {
            energy += footprint.energy;
        }
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                Thread.sleep(WAIT_TIME_MS);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Eflect.getInstance().stop();
            update();
            Eflect.getInstance().start();
        }
    }

    public double getEnergy() {
        return energy;
    }

    public int getSamplesCount() {
        return samplesCount;
    }
}

public class CombinedMeter implements Meter {

    public synchronized Measurements measure(Runnable runnable) {
        GreenLogger.get().info(String.format("green: start thread %s gc total %d max %d free %d",
                Thread.currentThread().getName(),
                Runtime.getRuntime().totalMemory(),
                Runtime.getRuntime().maxMemory(),
                Runtime.getRuntime().freeMemory()));

        final var accumulator = new Accumulator();

        Eflect.getInstance().start(4);
        Instant start = Instant.now();
        runnable.run();
        Instant end = Instant.now();
        Eflect.getInstance().stop();

        accumulator.update();

        GreenLogger.get().info(String.format("green: stop thread %s gc total %d max %d free %d",
                Thread.currentThread().getName(),
                Runtime.getRuntime().totalMemory(),
                Runtime.getRuntime().maxMemory(),
                Runtime.getRuntime().freeMemory()));

        long time = Duration.between(start, end).toMillis();

        GreenLogger.get().info(String.format("green: energy %f samples %d time %d", accumulator.getEnergy(), accumulator.getSamplesCount(), time));

        return new Measurements(accumulator.getEnergy(), time);
    }
}
