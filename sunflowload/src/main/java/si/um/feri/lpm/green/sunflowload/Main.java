package si.um.feri.lpm.green.sunflowload;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
        //final var factory = new SunflowRunnerFactory();
        //final var runner = factory.new Runner(new SunflowKnobs((int)6.700394102746045, (int)116.77794291077436, (int)-2.5658260699149356, (int)-1.5169675399851819));

        //final var runner = factory.new Runner(new SunflowKnobs(4, 640, 1, 2, 32, 10, Filter.BLACKMAN_HARRIS));
        //runner.run();
        //final var diff = runner.imageDifference();
        //System.out.println(String.format("(PATH %d) MSE: %f PSNR: %f", 0, diff.mse(), diff.psnr()));
        //new DisplayImage(runner.resizedImage);
        //ImageIO.write(runner.resizedImage, "png", new File("images/igi" + Integer.toString(0) + ".png"));
    }
}