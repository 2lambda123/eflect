package si.um.feri.lpm.green.sunflowload;

// From http://projectsweb.cs.washington.edu/research/projects/compression/UWIC.v2004.03.02/Tools.java

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class ImageDifference {

    public record Result(double mse, double psnr) {};

    public static long unsigned(long l) {
        long x = l; x <<= 32; x >>>= 32;
        return x;
    }

    /**
     * Computes the PSNR of two images (which must have the same dimensions and type).
     */
    public static Result calculate(BufferedImage im1, BufferedImage im2) {
        assert(im1.getType() == im2.getType()
                && im1.getHeight() == im2.getHeight()
                && im1.getWidth() == im2.getWidth());

        double mse = 0;
        int width = im1.getWidth();
        int height = im1.getHeight();
        Raster r1 = im1.getRaster();
        Raster r2 = im2.getRaster();
        for (int j = 0; j < height; j++)
            for (int i = 0; i < width; i++)
                mse += Math.pow(r1.getSample(i, j, 0) - r2.getSample(i, j, 0), 2);
        mse /= (double) (width * height);
        int maxVal = 255;
        double x = Math.pow(maxVal, 2) / mse;
        double psnr = 10.0 * logbase10(x);
        return new Result(mse, psnr);
    }

    /**
     * Returns the base-10 logarithm of a number
     */
    public static double logbase10(double x) {
        return Math.log(x) / Math.log(10);
    }
}