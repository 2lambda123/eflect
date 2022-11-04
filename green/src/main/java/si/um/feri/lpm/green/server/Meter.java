package si.um.feri.lpm.green.server;

public interface Meter {

    public Measurement measure(Runnable runnable);
}
