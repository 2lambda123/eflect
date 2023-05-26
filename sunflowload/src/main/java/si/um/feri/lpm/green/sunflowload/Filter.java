package si.um.feri.lpm.green.sunflowload;

public enum Filter {
    BOX,
    TRIANGLE,
    CATMULL_ROM,
    MITCHELL,
    LANCZOS,
    BLACKMAN_HARRIS,
    SINC,
    GAUSSIAN;

    @Override
    public String toString() {
        return switch (this) {
            case BOX -> "box";
            case TRIANGLE -> "triangle";
            case CATMULL_ROM -> "catmull-rom";
            case MITCHELL -> "mitchell";
            case LANCZOS -> "lanczos";
            case BLACKMAN_HARRIS -> "blackman-harris";
            case SINC -> "sinc";
            case GAUSSIAN -> "gaussian";
        };
    }
}
