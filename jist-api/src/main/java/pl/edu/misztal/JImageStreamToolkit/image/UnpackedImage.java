package pl.edu.misztal.JImageStreamToolkit.image;

import pl.edu.uj.JImageStream.model.Pixel;

import java.awt.image.BufferedImage;

class UnpackedImage {
    private pl.edu.uj.JImageStream.model.UnpackedImage unpackedImage;

    UnpackedImage(BufferedImage bufferedImage) {
        this.unpackedImage = new pl.edu.uj.JImageStream.model.UnpackedImage(bufferedImage);
    }

    void setPixel(int x, int y, Pixel pixel) {
        this.unpackedImage.setPixel(x, y, pixel.getAlpha(), pixel.getRed(), pixel.getGreen(), pixel.getBlue());
    }

    Pixel getPixel(int x, int y) {
        return new Pixel(this.unpackedImage.getPixel(x, y));
    }

    public void update() {
        unpackedImage.update();
    }

    BufferedImage getBufferdImage() {
        return unpackedImage.getBufferedImage();
    }

}
