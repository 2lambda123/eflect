package si.um.feri.lpm.green.server;

public interface Meter {

    public Measurements measure(Runnable runnable);
}
