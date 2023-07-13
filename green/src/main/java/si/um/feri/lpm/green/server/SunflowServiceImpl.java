package si.um.feri.lpm.green.server;

import io.grpc.stub.StreamObserver;
import si.um.feri.lpm.green.grpc.common.Fitness;
import si.um.feri.lpm.green.grpc.sunflow.SunflowKnobs;
import si.um.feri.lpm.green.grpc.sunflow.SunflowServiceGrpc;
import si.um.feri.lpm.green.sunflowload.Filter;
import si.um.feri.lpm.green.sunflowload.SunflowRunnerFactory;

class SunflowServiceImpl extends SunflowServiceGrpc.SunflowServiceImplBase {
    Meter meter;
    SunflowRunnerFactory factory;

    public SunflowServiceImpl(Meter meter) {
        factory = new SunflowRunnerFactory();
        this.meter = meter;
    }

    @Override
    public void fitness(SunflowKnobs request, StreamObserver<Fitness> responseObserver) {

        final var knobs = new si.um.feri.lpm.green.sunflowload.SunflowKnobs(
                request.getThreads(),
                request.getResolution(),
                request.getAaMin(),
                request.getAaMax(),
                request.getBucketSize(),
                request.getAoSamples(),
                Filter.values()[request.getFilter().getNumber()]);

        GreenLogger.get().info(String.format("green: knobs threads %d res %d aamin %d aamax %d buck %d ao %d",
            knobs.threads(),
            knobs.resolution(),
            knobs.aaMin(),
            knobs.aaMax(),
            knobs.bucketSize(),
            knobs.aoSamples()));

        final var runner = factory.new Runner(knobs);
        final var measurements = meter.measure(runner);
        final var distance = runner.imageDifference().mse();
        final var reply = Fitness.newBuilder()
                .setEnergy(measurements.energy())
                .setTime(measurements.time())
                .setDistance(distance)
                .setKilled(runner.killed())
                .build();

        GreenLogger.get().info(String.format("green: killed %b", runner.killed()));

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}