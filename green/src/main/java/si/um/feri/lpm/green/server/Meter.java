package si.um.feri.lpm.green.server;

import si.um.feri.lpm.green.Measurement;

public interface Meter {

    public Measurement measure(Runnable runnable);
}
