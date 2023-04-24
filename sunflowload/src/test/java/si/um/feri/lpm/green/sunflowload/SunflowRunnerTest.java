package si.um.feri.lpm.green.sunflowload;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SunflowRunnerTest {

    @Test
    void measureDistanceReference() {
        final var runner = new SunflowRunner(SunflowKnobs.REFERENCE);
        runner.run();
        assertEquals(0.0, runner.measureDistance());
    }


    @Test
    void measureDistanceDefault() {
        final var runner = new SunflowRunner(SunflowKnobs.DEFAULT);
        runner.run();
        assertTrue(runner.measureDistance() > 0.0);
    }
}