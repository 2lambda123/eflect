package si.um.feri.lpm.green.server;

import io.grpc.stub.StreamObserver;
import si.um.feri.lpm.green.grpc.common.Fitness;
import si.um.feri.lpm.green.grpc.sunflow.SunflowKnobs;
import si.um.feri.lpm.green.grpc.sunflow.SunflowServiceGrpc;
import si.um.feri.lpm.green.sunflowload.SunflowRunner;

class SunflowServiceImpl extends SunflowServiceGrpc.SunflowServiceImplBase {
    Meter meter;

    public SunflowServiceImpl(Meter meter) {
        this.meter = meter;
    }

    @Override
    public void fitness(SunflowKnobs request, StreamObserver<Fitness> responseObserver) {

        final var knobs = new si.um.feri.lpm.green.sunflowload.SunflowKnobs(
                request.getThreads(),
                request.getResolution(),
                request.getAaMin(),
                request.getAaMax());

        final var runner = new SunflowRunner(knobs);
        final var energy = meter.measureEnergy(runner);
        final var distance = runner.measureDistance();
        final var reply = Fitness.newBuilder()
                .setEnergy(energy)
                .setDistance(distance)
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}