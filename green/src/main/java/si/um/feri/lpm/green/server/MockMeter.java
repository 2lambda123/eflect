package si.um.feri.lpm.green.server;

public class MockMeter implements Meter {
    public static double ENERGY = 1;

    @Override
    public Measurement measure(Runnable runnable) {
        runnable.run();
        return new Measurement(ENERGY);
    }
}
