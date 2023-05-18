package si.um.feri.lpm.kaze;

import static org.junit.jupiter.api.Assertions.*;

class KazeTest {

    si.um.feri.lpm.green
    var request = SunflowKnobs.newBuilder()
            .setThreads(1)
            .setResolution(64)
            .setAaMin(-1)
            .setAaMax(1)
            .build();

    var channel = ManagedChannelBuilder.forTarget("localhost:50051")
            .usePlaintext()
            .build();

    var stub = SunflowServiceGrpc.newBlockingStub(channel);
    var response = stub.fitness(request);
        System.out.print(response.getEnergy());
    assertTrue(response.getEnergy() > 0.0);
    assertTrue(response.getDistance() > 0.0);

}