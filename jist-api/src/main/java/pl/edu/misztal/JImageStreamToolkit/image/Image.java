package pl.edu.misztal.JImageStreamToolkit.image;

import pl.edu.uj.JImageStream.model.Pixel;
import pl.edu.uj.JImageStream.model.StreamableImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image extends StreamableImage implements Cloneable {
    private UnpackedImage unpackedImage;

    public Image(File file) throws IOException {
        super(file);
        this.unpackedImage = new UnpackedImage(this.bufferedImage);
    }

    public Image(String filename) throws IOException {
        super(new File(filename));
        this.unpackedImage = new UnpackedImage(this.bufferedImage);
    }

    public Image(BufferedImage bufferedImage) {
        super(bufferedImage);
        this.unpackedImage = new UnpackedImage(this.bufferedImage);
    }

    private static BufferedImage copyBufferedImage(BufferedImage source) {
        BufferedImage b = new BufferedImage(source.getWidth(), source.getHeight(), source.getType());
        Graphics g = b.getGraphics();
        g.drawImage(source, 0, 0, null);
        g.dispose();
        return b;
    }

    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    @Override
    public Image clone() {
        Image ret = null;
        try {
            ret = (Image) super.clone();
            ret.setBufferedImage(copyBufferedImage(bufferedImage));
        } catch (CloneNotSupportedException ignored) {
        }
        return ret;
    }

    public int getWidth() {
        return this.bufferedImage.getWidth();
    }

    public int getHeight() {
        return this.bufferedImage.getHeight();
    }

    public final Pixel getPixel(int x, int y) {
        return unpackedImage.getPixel(x, y);
    }

    public final int getRed(int x, int y) {
        return unpackedImage.getPixel(x, y).getRed();
    }

    public final int getGreen(int x, int y) {
        return unpackedImage.getPixel(x, y).getGreen();
    }

    public final int getBlue(int x, int y) {
        return unpackedImage.getPixel(x, y).getBlue();
    }

    public final void setPixel(int x, int y, Pixel pixel) {
        unpackedImage.setPixel(x, y, pixel);
    }

    public final void update(boolean t) {
        unpackedImage.update();
        bufferedImage = unpackedImage.getBufferdImage();
    }

}
