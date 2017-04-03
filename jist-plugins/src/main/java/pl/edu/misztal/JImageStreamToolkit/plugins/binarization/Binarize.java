package pl.edu.misztal.JImageStreamToolkit.plugins.binarization;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;

import java.awt.*;

/**
 * Created by krzys on 25.03.2017.
 */
public class Binarize extends Plugin {
    private final int threshold;
    private int signal = Color.BLACK.getRGB();
    private int background = Color.WHITE.getRGB();

    public Binarize(int threshold) {
        this.threshold = threshold;
    }

    public Binarize(int threshold, Color signal, Color background) {
        this.threshold = threshold;
        this.signal = signal.getRGB();
        this.background = background.getRGB();
    }

    @Override
    public void process(Image imgIn, Image imgOut) {
        for (int jx = 0; jx < imgIn.getWidth(); ++jx) {
            for (int iy = 0; iy < imgIn.getHeight(); ++iy) {
                if (imgIn.getRed(jx, iy) > this.threshold) {
                    imgOut.setRGB(jx, iy, this.signal);
                } else {
                    imgOut.setRGB(jx, iy, this.background);
                }
            }
        }
    }
}
