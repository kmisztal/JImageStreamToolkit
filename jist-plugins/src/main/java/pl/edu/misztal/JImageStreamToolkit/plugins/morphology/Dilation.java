package pl.edu.misztal.JImageStreamToolkit.plugins.morphology;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;
import pl.edu.uj.JImageStream.filters.morphology.DilationFilter;

public class Dilation extends Plugin {
    private int kernelRadius = 3;
    private int kernelShape = DilationFilter.BALL_KERNEL;

    public Dilation(int kernelRadius, int kernelShape) {
        this.kernelRadius = kernelRadius;
        this.kernelShape = kernelShape;
    }

    public Dilation(int kernelRadius) {
        this.kernelRadius = kernelRadius;
    }

    public Dilation() {
    }

    protected void process(Image imgIn, Image imgOut) {
        imgOut.setBufferedImage(
                imgIn.parallelStream()
                        .apply(new DilationFilter(this.kernelRadius, this.kernelShape))
                        .collect(new pl.edu.uj.JImageStream.collectors.BufferedImageCollector())
        );
        setAttribute("kernelRadius", this.kernelRadius);
        setAttribute("kernelShape",
                this.kernelShape == 0 ? "SQUARE_KERNEL"
                        : this.kernelShape == 1 ? "BALL_KERNEL"
                        : this.kernelShape == 2 ? "VERTICAL_LINE_KERNEL"
                        : this.kernelShape == 3 ? "HORIZONTAL_LINE_KERNEL" : "N/A"
        );
    }
}
