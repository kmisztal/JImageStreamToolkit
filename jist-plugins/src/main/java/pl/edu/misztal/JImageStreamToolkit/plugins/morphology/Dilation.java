package pl.edu.misztal.JImageStreamToolkit.plugins.morphology;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;
import pl.edu.uj.JImageStream.filters.morphology.DilationFilter;

public class Dilation extends Plugin {
    private int kernelSize = 3;
    private int kernelShape = DilationFilter.BALL_KERNEL;

    public Dilation(int kernelSize, int kernelShape) {
        this.kernelSize = kernelSize;
        this.kernelShape = kernelShape;
    }

    public Dilation(int kernelSize) {
        this.kernelSize = kernelSize;
    }

    public Dilation() {
    }

    protected void process(Image imgIn, Image imgOut) {
        imgOut.setBufferedImage(
                imgIn.parallelStream()
                        .apply(new DilationFilter(this.kernelSize, this.kernelShape))
                        .collect(new pl.edu.uj.JImageStream.collectors.BufferedImageCollector())
        );
    }
}
