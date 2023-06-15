package si.um.feri.lpm.green.sunflowload;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

class DisplayImage
{
    DisplayImage(BufferedImage image)
    {
        JFrame frame = new JFrame("Add an image to JFrame");
        ImageIcon icon = new ImageIcon(image);
        frame.add(new JLabel(icon));
        frame.pack();
        frame.setVisible(true);
    }
}

public class Main {

    public static void main(String[] args) throws IOException {

        /*final var ks = List.of(
            new SunflowKnobs(7, 605,  2, -1, 32, 42, Filter.TRIANGLE),
            new SunflowKnobs(7, 625,  2,  3, 32, 52, Filter.TRIANGLE),
            new SunflowKnobs(7, 623, -2,  1, 32, 52, Filter.TRIANGLE),
            new SunflowKnobs(7, 387, -1,  1, 32, 61, Filter.TRIANGLE),
            new SunflowKnobs(7, 625, -2, -1, 32, 47, Filter.TRIANGLE),
            new SunflowKnobs(7, 560, -1, -1, 32, 36, Filter.TRIANGLE),
            new SunflowKnobs(5, 390, -2, -1, 32, 38, Filter.TRIANGLE),
            new SunflowKnobs(6, 320, -1, -1, 32, 21, Filter.TRIANGLE),
            new SunflowKnobs(7, 182, -1, -1, 32, 19, Filter.TRIANGLE));

        final var factory = new SunflowRunnerFactory();
        for (var k: ks) {
            final var runner = factory.new Runner(k);
            runner.run();
            final var diff = runner.imageDifference();
            System.out.println(String.format("(FINAL %d) MSE: %f PSNR: %f", 0, diff.mse(), diff.psnr()));
            //ImageIO.write(runner.resizedImage, "png", new File(String.format("images/final_%d_%d_%d_%d_%d.png", k.threads(), k.resolution(), k.aaMin(), k.aaMax(), k.aoSamples())));
        }*/

        //final var k = new SunflowKnobs(7, 610,  4, 4, 32, 42, Filter.LANCZOS);
        final var k = new SunflowKnobs(7, 625,  4, 4, 200, 42, Filter.TRIANGLE);
        final var factory = new SunflowRunnerFactory();
        final var runner = factory.new Runner(k);
        Instant start = Instant.now();
        runner.run();
        Instant end = Instant.now();
        final var diff = runner.imageDifference();
        long time = Duration.between(start, end).toMillis();
        System.out.println(String.format("(PROB %d) MSE: %f PSNR: %f TIME %d", 0, diff.mse(), diff.psnr(), time));
        ImageIO.write(runner.resizedImage, "png", new File(String.format("images/prob3_%d_%d_%d_%d_%d.png", k.threads(), k.resolution(), k.aaMin(), k.aaMax(), k.aoSamples())));

        /*final var factory = new SunflowRunnerFactory();
        //final var runner = factory.new Runner(new SunflowKnobs((int)6.700394102746045, (int)116.77794291077436, (int)-2.5658260699149356, (int)-1.5169675399851819));



        final var runner = factory.new Runner(new SunflowKnobs(4, 320, -2, -2, 32, 32, Filter.TRIANGLE));
        runner.run();
        final var diff = runner.imageDifference();*/
        //System.out.println(String.format("(PATH %d) MSE: %f PSNR: %f", 0, diff.mse(), diff.psnr()));

        /*for (int  i = 2; i <= 320; i *= 2) {
            final var runner = factory.new Runner(new SunflowKnobs(4, 320, -2, -2, i, 32, Filter.TRIANGLE));
            runner.run();
            final var diff = runner.imageDifference();
            System.out.println(String.format("(PATH %d) MSE: %f PSNR: %f", 0, diff.mse(), diff.psnr()));
            //new DisplayImage(runner.resizedImage);
            ImageIO.write(runner.resizedImage, "png", new File("images/bucket" + Integer.toString(i) + ".png"));
        }*/
    }
}