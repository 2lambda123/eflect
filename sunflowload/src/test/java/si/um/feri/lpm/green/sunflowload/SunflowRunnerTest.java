package si.um.feri.lpm.green.sunflowload;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SunflowRunnerTest {

    final SunflowRunnerFactory factory = new SunflowRunnerFactory(SunflowKnobs.FAST_REFERENCE);

    @Test
    void measureDistanceReference() {
        final var runner = factory.new Runner(SunflowKnobs.FAST_REFERENCE);
        runner.run();
        assertEquals(0.0, runner.imageDifference().mse());
    }


    @Test
    void measureDistanceDefault() throws InterruptedException {
        final var runner = factory.new Runner(SunflowKnobs.DEFAULT);
        runner.run();
        assertTrue(runner.imageDifference().mse() > 0.0);
    }

    @Test
    void timeoutReference() {
        final var runner = factory.new Runner(SunflowKnobs.FAST_REFERENCE);
        runner.run();
        assertFalse(runner.killed());
    }

    @Test
    void timeoutDefault() {
        final var runner = factory.new Runner(SunflowKnobs.DEFAULT);
        runner.run();
        assertFalse(runner.killed());
    }

    @Test
    void timeoutSlow1() {
        final var runner = factory.new Runner(new SunflowKnobs(7, 640, 2, 2, 32, 64, Filter.BLACKMAN_HARRIS));
        runner.run();
        assertTrue(runner.killed());
    }

    @Test
    void timeoutSlow2() {
        final var runner = factory.new Runner(new SunflowKnobs(7, 640, 3, 3, 32, 64, Filter.BLACKMAN_HARRIS));
        runner.run();
        assertTrue(runner.killed());
    }
}