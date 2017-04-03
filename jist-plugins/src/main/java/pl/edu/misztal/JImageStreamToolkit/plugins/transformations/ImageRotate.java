package pl.edu.misztal.JImageStreamToolkit.plugins.transformations;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;


public class ImageRotate extends Plugin {
    private final double angle;
    private Integer origin_x = null;
    private Integer origin_y = null;

    public ImageRotate(double angle) {
        this.angle = angle;
    }

    public ImageRotate(double angle, int origin_x, int origin_y) {
        this.angle = angle;
        this.origin_x = origin_x;
        this.origin_y = origin_y;
    }

    @Override
    public void process(Image imgIn, Image imgOut) {
        BufferedImage bufferedImage = imgIn.getCopyOfBufferedImage();

        int origin_x_local;
        int origin_y_local;
        if (origin_x == null && origin_y == null) {
            origin_x_local = bufferedImage.getWidth() / 2;
            origin_y_local = bufferedImage.getHeight() / 2;
        } else {
            origin_x_local = origin_x;
            origin_y_local = origin_y;
        }

        AffineTransform transform = new AffineTransform();
        transform.rotate(this.angle, origin_x_local, origin_y_local);
        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        imgOut.setBufferedImage(op.filter(bufferedImage, null));
    }
}
