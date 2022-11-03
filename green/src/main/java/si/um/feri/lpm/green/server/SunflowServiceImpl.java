package si.um.feri.lpm.green.server;

import io.grpc.stub.StreamObserver;
import si.um.feri.lpm.green.grpc.FitnessReply;
import si.um.feri.lpm.green.grpc.SunflowKnobsRequest;
import si.um.feri.lpm.green.grpc.SunflowServiceGrpc;
import si.um.feri.lpm.green.sunflowload.SunflowKnobs;
import si.um.feri.lpm.green.sunflowload.SunflowRunner;

class SunflowServiceImpl extends SunflowServiceGrpc.SunflowServiceImplBase {
    Meter meter;

    public SunflowServiceImpl(Meter meter) {
        this.meter = meter;
    }

    @Override
    public void fitness(SunflowKnobsRequest request, StreamObserver<FitnessReply> responseObserver) {

        var knobs = new SunflowKnobs(
                request.getThreads(),
                request.getResolution(),
                request.getAaMin(),
                request.getAaMax());

        var measurement = meter.measure(new SunflowRunner(knobs));
        var reply = FitnessReply.newBuilder()
                .setEnergy(measurement.energy())
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}