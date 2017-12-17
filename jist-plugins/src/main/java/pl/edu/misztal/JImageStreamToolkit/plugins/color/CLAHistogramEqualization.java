package pl.edu.misztal.JImageStreamToolkit.plugins.color;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;
import pl.edu.uj.JImageStream.collectors.BufferedImageCollector;
import pl.edu.uj.JImageStream.filters.equalization.CLAHEFilter;

public class CLAHistogramEqualization extends Plugin {

    private int BINS = 255;
    private int BLOCK_RADIUS = 63;
    private double SLOPE = 6.0;

    public CLAHistogramEqualization() {
    }

    public CLAHistogramEqualization(double slope, int blockRadius, int bins) {
        this.SLOPE = slope;
        this.BLOCK_RADIUS = blockRadius;
        this.BINS = bins;
    }

    protected void process(Image imgIn, Image imgOut) {
        imgOut.setBufferedImage(
                imgIn.parallelStream()
                        .apply(new CLAHEFilter(this.SLOPE, this.BLOCK_RADIUS, this.BINS))
                        .collect(new BufferedImageCollector())
        );
    }
}
