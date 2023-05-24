package si.um.feri.lpm.green.sunflowload;

public class Main {

    public static final int clamp(int x, int min, int max) {
        if (x > max)
            return max;
        if (x > min)
            return x;
        return min;
    }
    public static void main(String[] args) {

        System.out.println(clamp(6, -4, 5));
        //final var runner = new SunflowRunner(new SunflowKnobs((int)6.700394102746045, (int)116.77794291077436, (int)-2.5658260699149356, (int)-1.5169675399851819));
        /*final var factory = new SunflowRunnerFactory();
        final var runner = factory.new Runner(new SunflowKnobs((int)6.700394102746045, (int)116.77794291077436, (int)-2.5658260699149356, (int)-1.5169675399851819));
        runner.run();
        System.out.println(runner.measureDistance());*/
    }
}
