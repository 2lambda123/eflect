package si.um.feri.lpm.green.sunflowload;

import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import dev.brachtendorf.jimagehash.hashAlgorithms.PerceptiveHash;
import org.sunflow.SunflowAPI;
import org.sunflow.core.Display;
import org.sunflow.image.Color;
import si.um.feri.lpm.green.sunflowload.scenes.CornellBox;
import java.awt.image.BufferedImage;

public class SunflowRunnerFactory {

    Hash referenceHash;
    HashingAlgorithm hasher;

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

    private synchronized BufferedImage render(SunflowKnobs knobs) {
        final var scene = new CornellBox(knobs);
        scene.build();
        final var display = new BufferedImageDisplay();
        scene.render(SunflowAPI.DEFAULT_OPTIONS, display);
        return display.getImage();
    }


    public SunflowRunnerFactory() {
        this.hasher = new PerceptiveHash(32);
        this.referenceHash = this.hasher.hash(this.render(SunflowKnobs.REFERENCE));
    }

    public class Runner implements Runnable {
        Hash hash;
        SunflowKnobs knobs;

        public Runner(SunflowKnobs knobs) {
            this.knobs = knobs;
        }

        @Override
        public void run() {
            this.hash = hasher.hash(render(this.knobs));
        }

        public double measureDistance() {
            return referenceHash.normalizedHammingDistance(this.hash);
        }
    }

}
