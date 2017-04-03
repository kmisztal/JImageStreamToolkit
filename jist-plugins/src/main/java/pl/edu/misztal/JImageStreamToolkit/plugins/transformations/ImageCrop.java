package pl.edu.misztal.JImageStreamToolkit.plugins.transformations;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;
import pl.edu.uj.JImageStream.api.core.Filter;
import pl.edu.uj.JImageStream.collectors.BufferedImageCollector;
import pl.edu.uj.JImageStream.model.Pixel;

import java.awt.*;

public class ImageCrop extends Plugin {
    private int x_crop_min;
    private int y_crop_min;
    private int x_crop_max;
    private int y_crop_max;
    private Pixel background = new Pixel(255, 255, 255, 255);

    public ImageCrop(int x_crop_min, int y_crop_min, int x_crop_max, int y_crop_max) {
        this.x_crop_min = x_crop_min;
        this.y_crop_min = y_crop_min;
        this.x_crop_max = x_crop_max;
        this.y_crop_max = y_crop_max;
    }

    public ImageCrop(Color background, int x_crop_min, int y_crop_min, int x_crop_max, int y_crop_max) {
        this.x_crop_min = x_crop_min;
        this.y_crop_min = y_crop_min;
        this.x_crop_max = x_crop_max;
        this.y_crop_max = y_crop_max;
        this.background = new Pixel(background.getRed(), background.getGreen(), background.getBlue(), background.getAlpha());
    }

    @Override
    protected void process(Image imgIn, Image imgOut) {
        imgOut.setBufferedImage(imgIn.parallelStream().apply(new Filter() {

            @Override
            public void apply(int x, int y) {
                if (x < x_crop_min
                        || x > getSourceWidth() - x_crop_max
                        || y < y_crop_min
                        || y > getSourceHeight() - y_crop_max)
                    setPixel(x, y, background);
                else {
                    setPixel(x, y, getPixel(x, y));
                }
            }
        }).collect(new BufferedImageCollector()));
    }
}
