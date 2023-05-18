package si.um.feri.lpm.kaze;

import io.grpc.ManagedChannelBuilder;
import si.um.feri.lpm.grpc.kaze.KazeKnobs;
import si.um.feri.lpm.grpc.kaze.KazeServiceGrpc;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {
    @org.junit.jupiter.api.Test
    void testKaze() throws IOException {
        var request = KazeKnobs.newBuilder()
                .setSimNum(1)
                .addAllBuildingHeights(List.of(20, 25, 21, 19, 22, 29, 21, 25, 22, 15, 25, 21, 26, 15, 20, 12, 19, 25, 20, 15, 25, 21, 26, 15, 20, 12, 30, 38))
                .setSurfPressure(101420)
                .setSurfRoughness(0.0001)
                .setSurfTemp(350)
                .setInitWindSpeedH(5.4)
                .setInitWindSpeedV(1.5)
                .build();

        var channel = ManagedChannelBuilder.forTarget("localhost:50051")
                .usePlaintext()
                .build();

        var stub = KazeServiceGrpc.newBlockingStub(channel);
        var response = stub.fitness(request);
        assertEquals(1, response.getSimNum());
        assertEquals(0.7316241737170276, response.getPosition());
        assertTrue(response.getCollision());
    }
}