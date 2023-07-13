package si.um.feri.lpm.green.sunflowload;


import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

        // awk '{print "new SunflowKnobs(" $1 ", " $2 ", " $3 ", " $4 ", " $5 ", " $6 ", Filter.values()[" $7 "])" }'

        // 1.79061279296875   21101.335207311935   5  605  3   3   339  32  5
        // 4.137607421875     768.5492520625387    4  605  -3  1   68   32  5
        // 7.31381103515625   279.9603491460172    5  591  -1  0   230  32  5
        // 14.78446044921875  127.02707059461135   5  606  -3  -1  246  62  2
        // 24.24114013671875  44.42939841294691    5  507  -3  -1  148  31  0
        // 36.10549072265625  18.32114694441504    5  341  -1  -1  57   54  0
        // 47.56075927734375  3.2344698387192516   5  307  -2  -1  74   28  0


        final var ks = List.of(
                //new SunflowKnobs(5, 605, 3, 3, 339, 32, Filter.values()[5]),
                //new SunflowKnobs(4, 605, -3, 1, 68, 32, Filter.values()[5]),
                //new SunflowKnobs(5, 591, -1, 0, 230, 32, Filter.values()[5]),
                //new SunflowKnobs(5, 606, -3, -1, 246, 62, Filter.values()[2]),
                //new SunflowKnobs(5, 507, -3, -1, 148, 31, Filter.values()[0]),
                //new SunflowKnobs(5, 341, -1, -1, 57, 54, Filter.values()[0]),
                //new SunflowKnobs(5, 307, -2, -1, 74, 28, Filter.values()[0]));
                //new SunflowKnobs(7,  605,  1,   1,   62,   63, Filter.values()[5])
                //new SunflowKnobs(7,  605, 1,   1,   66,   31, Filter.values()[5])
                SunflowKnobs.REFERENCE
            );


        final var factory = new SunflowRunnerFactory();
        for (var k: ks) {
            final var runner = factory.new Runner(k);
            runner.run();
            final var diff = runner.imageDifference();
            System.out.printf("(FFINAL %d) MSE: %f PSNR: %f %b%n", 0, diff.mse(), diff.psnr(), runner.killed());
            //ImageIO.write(runner.resizedImage, "png", new File(String.format("images/best_%d_%d_%d_%d_%d_%d_%s.png", k.threads(), k.resolution(), k.aaMin(), k.aaMax(), k.bucketSize(), k.aoSamples(), k.filter().toString())));
        }

        //final var k = new SunflowKnobs(7, 610,  4, 4, 32, 42, Filter.LANCZOS);
        //final var k = new SunflowKnobs(7, 625,  4, 4, 200, 42, Filter.TRIANGLE);
        //final var factory = new SunflowRunnerFactory();
        //final var runner = factory.new Runner(k);
        //Instant start = Instant.now();
        //runner.run();
        //Instant end = Instant.now();
        //final var diff = runner.imageDifference();
        //long time = Duration.between(start, end).toMillis();
        //System.out.println(String.format("(PROB %d) MSE: %f PSNR: %f TIME %d", 0, diff.mse(), diff.psnr(), time));
        //ImageIO.write(runner.resizedImage, "png", new File(String.format("images/prob3_%d_%d_%d_%d_%d.png", k.threads(), k.resolution(), k.aaMin(), k.aaMax(), k.aoSamples())));

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