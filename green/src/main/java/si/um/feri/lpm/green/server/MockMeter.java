package si.um.feri.lpm.green.server;

public class MockMeter implements Meter {
    public static double ENERGY = 1;
    public static long TIME = 0;

    @Override
    public Measurements measure(Runnable runnable) {
        runnable.run();
        return new Measurements(ENERGY, TIME);
    }
}
