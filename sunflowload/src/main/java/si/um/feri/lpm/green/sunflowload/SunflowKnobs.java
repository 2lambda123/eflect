package si.um.feri.lpm.green.sunflowload;

public record SunflowKnobs(int threads, int resolution, int aaMin, int aaMax) {
    public static final SunflowKnobs REFERENCE = new SunflowKnobs(1, 640, 1, 2);

    public static final SunflowKnobs DEFAULT = new SunflowKnobs(1, 64, -1, 1);
}
