package pl.edu.misztal.JImageStreamToolkit.plugins.morphology;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;
import pl.edu.uj.JImageStream.filters.morphology.ErosionFilter;

public class Erosion extends Plugin {
    private int kernelSize = 3;
    private int kernelShape = ErosionFilter.BALL_KERNEL;

    public Erosion(int kernelSize, int kernelShape) {
        this.kernelSize = kernelSize;
        this.kernelShape = kernelShape;
    }

    public Erosion(int kernelSize) {
        this.kernelSize = kernelSize;
    }

    public Erosion() {
    }

    protected void process(Image imgIn, Image imgOut) {
        imgOut.setBufferedImage(
                imgIn.parallelStream()
                        .apply(new ErosionFilter(this.kernelSize, this.kernelShape))
                        .collect(new pl.edu.uj.JImageStream.collectors.BufferedImageCollector())
        );
    }
}
