package si.um.feri.lpm.green.sunflowload;

import org.sunflow.SunflowAPI;
import org.sunflow.core.Display;
import org.sunflow.image.Color;
import org.sunflow.system.UI;
import si.um.feri.lpm.green.sunflowload.scenes.CornellBox;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;
import java.util.Timer;
import java.util.TimerTask;

public class SunflowRunnerFactory {

    BufferedImage referenceImage;
    long referenceDuration;

    // Striped down org.sunflow.core.display.FastDisplay
    static class BufferedImageDisplay implements Display {

        private int[] pixels;
        BufferedImage image;

        public synchronized void imageBegin(int w, int h, int bucketSize) {
            pixels = new int[w * h];
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        }

        public void imagePrepare(int x, int y, int w, int h, int id) {}

        public void imageUpdate(int x, int y, int w, int h, Color[] data, float[] alpha) {
            int iw = image.getWidth();
            int off = x + iw * y;
            iw -= w;
            for (int j = 0, index = 0; j < h; j++, off += iw)
                for (int i = 0; i < w; i++, index++, off++)
                    pixels[off] = 0xFF000000 | data[index].toRGB();
        }

        public void imageFill(int x, int y, int w, int h, Color c, float alpha) {
            int iw = image.getWidth();
            int off = x + iw * y;
            iw -= w;
            int rgb = 0xFF000000 | c.toRGB();
            for (int j = 0, index = 0; j < h; j++, off += iw)
                for (int i = 0; i < w; i++, index++, off++)
                    pixels[off] = rgb;
        }

        public synchronized void imageEnd() {
            image.setRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        }

        public BufferedImage getImage() {
            return image;
        }
    }

    // https://stackoverflow.com/questions/4216123/how-to-scale-a-bufferedimage
    public static BufferedImage resize(BufferedImage source, int width, int height) {
        BufferedImage resized = new BufferedImage(width, height, source.getType());
        Graphics2D g = resized.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(source, 0, 0, width, height, 0, 0, source.getWidth(), source.getHeight(), null);
        g.dispose();
        return resized;
    }

    private static synchronized BufferedImage render(SunflowKnobs knobs) {
        final var scene = new CornellBox(knobs);
        scene.build();
        final var display = new BufferedImageDisplay();
        scene.render(SunflowAPI.DEFAULT_OPTIONS, display);
        return display.getImage();
    }

    public SunflowRunnerFactory() {
        this(SunflowKnobs.REFERENCE);
    }

    public SunflowRunnerFactory(SunflowKnobs referenceKnobs) {
        Instant start = Instant.now();
        this.referenceImage = render(referenceKnobs);
        referenceDuration = Duration.between(start, Instant.now()).toMillis();
    }

    public class Runner implements Runnable {
        BufferedImage image;
        BufferedImage resizedImage;
        SunflowKnobs knobs;
        double tolerance = 0.5;
        boolean killed = false;

        public Runner(SunflowKnobs knobs) {
            this.knobs = knobs;
        }

        public Runner(SunflowKnobs knobs, double tolerance) {
            this.knobs = knobs;
            this.tolerance = tolerance;
        }

        @Override
        public void run() {
            var timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    UI.taskCancel();
                    killed = true;
                }
            }, referenceDuration + (long)(tolerance * referenceDuration)); // Tolerance is added to account for the variability of the runtimes
            this.image = render(this.knobs);
            timer.cancel();
            this.resizedImage = resize(this.image, referenceImage.getWidth(), referenceImage.getHeight());
        }

        public boolean killed() {
            return killed;
        }

        public ImageDifference.Result imageDifference() {
            return ImageDifference.calculate(referenceImage, this.resizedImage);
        }
    }

}
