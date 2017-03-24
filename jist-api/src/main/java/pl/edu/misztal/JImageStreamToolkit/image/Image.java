package pl.edu.misztal.JImageStreamToolkit.image;

import pl.edu.uj.JImageStream.model.StreamableImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image extends StreamableImage implements Cloneable {
    public Image(File file) throws IOException {
        super(file);
    }

    public Image(String filename) throws IOException {
        super(new File(filename));
    }

    public Image(BufferedImage bufferedImage) {
        super(bufferedImage);
    }

    private static BufferedImage copyImage(BufferedImage source) {
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
            ret.setBufferedImage(copyImage(bufferedImage));
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
}
