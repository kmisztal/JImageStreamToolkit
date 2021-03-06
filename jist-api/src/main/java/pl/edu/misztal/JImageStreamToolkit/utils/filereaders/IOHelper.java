package pl.edu.misztal.JImageStreamToolkit.utils.filereaders;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IOHelper {

    public static BufferedImage load(String filename) {
        return load(new File(filename));
    }

    public static BufferedImage load(String filename, int imageType) {
        try {
            return convert(ImageIO.read(new File(filename)), imageType);
        } catch (IOException ex) {
            throw new RuntimeException("Wrong local file name");
        }
    }

    private static BufferedImage convert(BufferedImage bi, int imageType) {
        final BufferedImage bw = new BufferedImage(bi.getWidth(), bi.getHeight(), imageType);
        final Graphics2D g = bw.createGraphics();
        g.drawRenderedImage(bi, null);
        g.dispose();
        return bw;
    }

    public static void save(String filePath, BufferedImage image) {
        File l_file = new File(filePath);
        try {
            if (filePath.toUpperCase().endsWith(".JPEG") || filePath.toUpperCase().endsWith(".JPG")) {
                ImageIO.write(image, "JPEG", l_file);
            } else if (filePath.toUpperCase().endsWith(".PNG")) {
                ImageIO.write(image, "PNG", l_file);
            } else {
                ImageIO.write(image, filePath.substring(filePath.lastIndexOf('.') + 1), l_file);
            }
        } catch (Exception ignored) {
        }
    }

    public static BufferedImage load(File file) {
        try {
            BufferedImage ret;
            if (file.getName().toUpperCase().endsWith(".TIFF") || file.getName().toUpperCase().endsWith(".TIF")) {
                ret = TIFFReader.load(file);
            } else {
                ret = ImageIO.read(file);
            }
            if (ret == null) {
                throw new RuntimeException("Wrong file format");
            }
            return ret;
        } catch (IOException | RuntimeException ex) {
            return null;
        }
    }
}