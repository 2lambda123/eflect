package si.um.feri.lpm.green.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import si.um.feri.lpm.green.Measurement;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class GreenServer {

    private Server server;
    private Meter meter;


    public GreenServer(Meter meter) {
        this.meter = meter;
    }

    public void start() throws IOException {
        int port = 50051;
        server = ServerBuilder.forPort(port)
                .addService(new SunflowServiceImpl(meter))
                .build()
                .start();
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        var server = new GreenServer(new EflectMeter());
        server.start();
        server.blockUntilShutdown();
    }
}
