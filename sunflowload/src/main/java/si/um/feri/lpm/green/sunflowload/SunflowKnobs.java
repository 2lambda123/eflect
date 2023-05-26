package si.um.feri.lpm.green.sunflowload;

public record SunflowKnobs(int threads, int resolution, int aaMin, int aaMax, int bucketSize, int aoSamples, Filter filter) {

    public static final SunflowKnobs REFERENCE = new SunflowKnobs(4, 640, 1, 2, 32, 64, Filter.BLACKMAN_HARRIS);

    public static final SunflowKnobs DEFAULT = new SunflowKnobs(1, 64, -1, 1, 32, 32, Filter.TRIANGLE);
}
