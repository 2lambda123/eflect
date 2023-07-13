package si.um.feri.lpm.green.server;

import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.Tag;
import si.um.feri.lpm.green.grpc.sunflow.Filter;
import si.um.feri.lpm.green.grpc.sunflow.SunflowKnobs;
import si.um.feri.lpm.green.grpc.sunflow.SunflowServiceGrpc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PostDeployServerTest {

    @Tag("PostDeploy")
    @org.junit.jupiter.api.Test
    void testDefault() throws IOException {
        var request = SunflowKnobs.newBuilder()
                .setThreads(4)
                .setResolution(320)
                .setAaMin(-1)
                .setAaMax(1)
                .setBucketSize(32)
                .setAoSamples(32)
                .setFilter(Filter.TRIANGLE)
                .build();

        var channel = ManagedChannelBuilder.forTarget("localhost:50051")
                .usePlaintext()
                .build();

        var stub = SunflowServiceGrpc.newBlockingStub(channel);
        var response = stub.fitness(request);
        System.out.println(response.getEnergy());
        System.out.println(response.getTime());
        System.out.println(response.getDistance());
        assertTrue(response.getEnergy() > 0.0);
        assertTrue(response.getTime() > 0);
        assertTrue(response.getDistance() > 0.0);
    }

    @Tag("PostDeploy")
    @org.junit.jupiter.api.Test
    void testQuick() throws IOException {
        var request = SunflowKnobs.newBuilder()
                .setThreads(8)
                .setResolution(64)
                .setAaMin(-1)
                .setAaMax(-2)
                .setBucketSize(32)
                .setAoSamples(32)
                .setFilter(Filter.TRIANGLE)
                .build();

        var channel = ManagedChannelBuilder.forTarget("localhost:50051")
                .usePlaintext()
                .build();

        var stub = SunflowServiceGrpc.newBlockingStub(channel);
        var response = stub.fitness(request);
        System.out.println(response.getEnergy());
        System.out.println(response.getTime());
        System.out.println(response.getDistance());
        assertEquals(0.0, response.getEnergy());
        assertTrue(response.getTime() > 0);
        assertTrue(response.getDistance() > 0.0);
    }
}
