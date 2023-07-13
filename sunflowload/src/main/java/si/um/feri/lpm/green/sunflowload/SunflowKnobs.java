package si.um.feri.lpm.green.sunflowload;

public record SunflowKnobs(int threads, int resolution, int aaMin, int aaMax, int bucketSize, int aoSamples, Filter filter) {

    // The number of threads must be 1, otherwise we could exclude potentially more energy efficient configurations with low number of threads
    public static final SunflowKnobs REFERENCE = new SunflowKnobs(1, 640, 1, 2, 32, 64, Filter.BLACKMAN_HARRIS);

    // A faster reference image for testing
    public static final SunflowKnobs FAST_REFERENCE = new SunflowKnobs(7, 640, 1, 2, 32, 64, Filter.BLACKMAN_HARRIS);

    public static final SunflowKnobs DEFAULT = new SunflowKnobs(1, 64, -1, 1, 32, 32, Filter.TRIANGLE);
}
