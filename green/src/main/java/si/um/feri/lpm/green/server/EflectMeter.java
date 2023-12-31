package si.um.feri.lpm.green.server;

import eflect.Eflect;
import eflect.data.EnergyFootprint;

public class EflectMeter implements Meter {

    @Override
    public Measurements measure(Runnable runnable) {
        Eflect.getInstance().start();
        runnable.run();
        Eflect.getInstance().stop();

        double energy = 0;
        for (EnergyFootprint footprint : Eflect.getInstance().read()) {
            energy += footprint.energy;
        }
        return new Measurements(energy, 0);
    }
}
