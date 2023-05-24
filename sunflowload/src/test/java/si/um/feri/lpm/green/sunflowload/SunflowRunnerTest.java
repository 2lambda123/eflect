package si.um.feri.lpm.green.sunflowload;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SunflowRunnerTest {

    @Test
    void measureDistanceReference() {
        final var factory = new SunflowRunnerFactory();
        final var runner = factory.new Runner(SunflowKnobs.REFERENCE);
        runner.run();
        assertEquals(0.0, runner.measureDistance());
    }


    @Test
    void measureDistanceDefault() throws InterruptedException {
        final var factory = new SunflowRunnerFactory();
        final var runner = factory.new Runner(SunflowKnobs.DEFAULT);
        runner.run();
        assertTrue(runner.measureDistance() > 0.0);
    }

    //final var runner = factory.new Runner(new SunflowKnobs((int)6.700394102746045, (int)116.77794291077436, (int)-2.5658260699149356, (int)-1.5169675399851819));
}