package si.um.feri.lpm.green.sunflowload;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SunflowRunnerTest {

    @Test
    void measureDistanceReference() {
        final var factory = new SunflowRunnerFactory();
        final var runner = factory.new Runner(SunflowKnobs.REFERENCE);
        runner.run();
        assertEquals(0.0, runner.imageDifference().mse());
    }


    @Test
    void measureDistanceDefault() throws InterruptedException {
        final var factory = new SunflowRunnerFactory();
        final var runner = factory.new Runner(SunflowKnobs.DEFAULT);
        runner.run();
        assertTrue(runner.imageDifference().mse() > 0.0);
    }
}