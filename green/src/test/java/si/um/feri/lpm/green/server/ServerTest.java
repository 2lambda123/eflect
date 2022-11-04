package si.um.feri.lpm.green.server;

import io.grpc.ManagedChannelBuilder;
import si.um.feri.lpm.green.grpc.sunflow.SunflowKnobs;
import si.um.feri.lpm.green.grpc.sunflow.SunflowServiceGrpc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @org.junit.jupiter.api.Test
    void testSunflow() throws IOException {
        var server = new Server(new MockMeter());
        server.start();

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
        assertEquals(MockMeter.ENERGY, response.getEnergy());
    }
}