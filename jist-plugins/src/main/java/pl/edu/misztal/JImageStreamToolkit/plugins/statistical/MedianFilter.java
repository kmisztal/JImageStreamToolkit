package pl.edu.misztal.JImageStreamToolkit.plugins.statistical;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;


public class MedianFilter extends Plugin {
    private final int kernelSize;

    public MedianFilter(int kernelSize) {
        this.kernelSize = kernelSize;
    }

    public MedianFilter() {
        this.kernelSize = 3;
    }

    public void process(Image imgIn, Image imgOut) {
        imgOut.setBufferedImage(
                imgIn.parallelStream()
                        .apply(new pl.edu.uj.JImageStream.filters.statistical.MedianFilter(this.kernelSize))
                        .collect(new pl.edu.uj.JImageStream.collectors.BufferedImageCollector())
        );
    }
}
