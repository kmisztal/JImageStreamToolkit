package pl.edu.misztal.JImageStreamToolkit.image;

import pl.edu.uj.JImageStream.api.core.Collector;

import java.awt.image.BufferedImage;

class ImageCollector implements Collector<Image> {
    @Override
    public Image collect(BufferedImage bufferedImage) {
        logger.info(this.getClass() + " successfully collected image");
        return new Image(bufferedImage);
    }
}