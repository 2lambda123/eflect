package si.um.feri.lpm.green.server;

import io.grpc.ManagedChannelBuilder;
import si.um.feri.lpm.green.grpc.sunflow.Filter;
import si.um.feri.lpm.green.grpc.sunflow.SunflowKnobs;
import si.um.feri.lpm.green.grpc.sunflow.SunflowServiceGrpc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ServerTest {

    @org.junit.jupiter.api.Test
    void test() throws IOException {
        var server = new Server(new MockMeter());
        server.start();

        var request = SunflowKnobs.newBuilder()
                .setThreads(4)
                .setResolution(640)
                .setAaMin(1)
                .setAaMax(2)
                .setBucketSize(32)
                .setAoSamples(64)
                .setFilter(Filter.BLACKMAN_HARRIS)
                .build();

        var channel = ManagedChannelBuilder.forTarget("localhost:50051")
                .usePlaintext()
                .build();

        var stub = SunflowServiceGrpc.newBlockingStub(channel);
        var response = stub.fitness(request);
        assertEquals(MockMeter.ENERGY, response.getEnergy());
        assertEquals(MockMeter.TIME, response.getTime());
        assertEquals(0.0, response.getDistance());
    }
}