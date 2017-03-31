package pl.edu.misztal.JImageStreamToolkit.image;

import pl.edu.misztal.JImageStreamToolkit.utils.ColorHelper;
import pl.edu.misztal.JImageStreamToolkit.utils.IOHelper;
import pl.edu.uj.JImageStream.api.ImageStream;
import pl.edu.uj.JImageStream.model.StreamableImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Image implements Cloneable {
    private static int type = BufferedImage.TYPE_INT_ARGB;
    private BufferedImage bufferedImage;
    private int width;
    private int height;
    private String descrition = "";

    public Image(final int width, final int height) {
        this(width, height, type);
    }

    public Image(final int width, final int height, final int imageType) {
        this.width = width;
        this.height = height;
        this.bufferedImage = new BufferedImage(width, height, imageType);
    }

    public Image(File file) throws IOException {
        this(IOHelper.load(file));
    }

    public Image(String filename) {
        this(IOHelper.load(filename));
    }

    public Image(String filename, int imageType) {
        this(IOHelper.load(filename, imageType));
    }

    public Image(BufferedImage img) {
        width = img.getWidth();
        height = img.getHeight();
        //to make sure that we have a correct image type
        if (img.getType() != type) {
            final BufferedImage convertedImg = new BufferedImage(width, height, type);
            convertedImg.getGraphics().drawImage(img, 0, 0, null);
            this.bufferedImage = convertedImg;
        } else {
            this.bufferedImage = img;
        }
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

    public void setBufferedImage(BufferedImage img) {
        width = img.getWidth();
        height = img.getHeight();
        //to make sure that we have a correct image type
        if (img.getType() != type) {
            final BufferedImage convertedImg = new BufferedImage(width, height, type);
            convertedImg.getGraphics().drawImage(img, 0, 0, null);
            this.bufferedImage = convertedImg;
        } else {
            this.bufferedImage = img;
        }

    }

    @Override
    public Image clone() {
        try {
            return this.getClass().getConstructor(BufferedImage.class).newInstance(copyBufferedImage(bufferedImage));
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException ignored) {
        }
        return null;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public final int getHeight() {
        return height;
    }

    public final int getWidth() {
        return width;
    }

    private boolean checkX(final int x) {
        return (x >= 0 && x <= width);
    }

    private boolean checkY(final int y) {
        return (y >= 0 && y <= height);
    }

    public void fillWithColor(Color c) {
        Graphics2D g = bufferedImage.createGraphics();
        g.setColor(c);
        g.fillRect(0, 0, width, height);
        g.dispose();
    }

    public void clearImage(Color c) {
        fillWithColor(c);
    }

    /**
     * Gets the type
     */
    public int getType() {
        return bufferedImage.getType();
    }

    public int getRGB(final int x, final int y) {
        return bufferedImage.getRGB(x, y);
    }

    public void setRGB(int x, int y, int i) {
        bufferedImage.setRGB(x, y, i);
    }

    public void setRGB(int x, int y, int r, int g, int b) {
        bufferedImage.setRGB(x, y, ColorHelper.getRGB(r, g, b));
    }

    public void setRGB(int x, int y, int r, int g, int b, int a) {
        bufferedImage.setRGB(x, y, ColorHelper.getRGB(r, g, b, a));
    }

    public int getRed(final int x, final int y) {
        return ColorHelper.RED.getColor(bufferedImage.getRGB(x, y));
    }

    public int getGreen(final int x, final int y) {
        return ColorHelper.GREEN.getColor(bufferedImage.getRGB(x, y));
    }

    public int getBlue(final int x, final int y) {
        return ColorHelper.BLUE.getColor(bufferedImage.getRGB(x, y));
    }

    public int getRGBSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return bufferedImage.getRGB(x, y);
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    public void setRGBSecure(int x, int y, int i) {
        if (checkX(x) && checkY(y)) {
            bufferedImage.setRGB(x, y, i);
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    public int getRedSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return ColorHelper.RED.getColor(bufferedImage.getRGB(x, y));
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    public int getGreenSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return ColorHelper.GREEN.getColor(bufferedImage.getRGB(x, y));
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    public int getBlueSecure(final int x, final int y) {
        if (checkX(x) && checkY(y)) {
            return ColorHelper.BLUE.getColor(bufferedImage.getRGB(x, y));
        } else {
            throw new RuntimeException("Position outside the image: [" + x + ";" + y + "]");
        }
    }

    public BufferedImage getCopyOfBufferedImage() {
        return copyBufferedImage(bufferedImage);
    }

    public void save(String filemane) {
        IOHelper.save(filemane, getBufferedImage());
    }

    public ImageStream parallelStream() {
        return new StreamableImage(this.bufferedImage).parallelStream();
    }

    public ImageStream stream() {
        return new StreamableImage(this.bufferedImage).stream();
    }
}
