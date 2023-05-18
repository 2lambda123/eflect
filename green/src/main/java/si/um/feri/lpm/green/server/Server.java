package si.um.feri.lpm.green.server;

import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Server {

    private io.grpc.Server server;
    private Meter meter;


    public Server(Meter meter) {
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
        var server = new Server(new CombinedMeter());
        server.start();
        server.blockUntilShutdown();
    }
}
