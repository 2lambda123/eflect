package si.um.feri.lpm.green.sunflowload;

public class Main {
    public static void main(String[] args) {
        //final var runner = new SunflowRunner(new SunflowKnobs((int)6.700394102746045, (int)116.77794291077436, (int)-2.5658260699149356, (int)-1.5169675399851819));
        final var factory = new SunflowRunnerFactory();
        final var runner = factory.new Runner(new SunflowKnobs((int)6.700394102746045, (int)116.77794291077436, (int)-2.5658260699149356, (int)-1.5169675399851819));
        runner.run();
        System.out.println(runner.measureDistance());
    }
}
