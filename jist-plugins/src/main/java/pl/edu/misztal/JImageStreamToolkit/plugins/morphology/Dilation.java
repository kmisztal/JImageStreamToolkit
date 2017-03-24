package pl.edu.misztal.JImageStreamToolkit.plugins.morphology;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;

public class Dilation extends Plugin {
    private final int kernelSize;

    public Dilation(int kernelSize) {
        this.kernelSize = kernelSize;
    }

    public Dilation() {
        this.kernelSize = 3;
    }

    public void process(Image imgIn, Image imgOut) {
        imgOut.setBufferedImage(
                imgIn.parallelStream()
                        .apply(new pl.edu.uj.JImageStream.filters.morphology.ErosionFilter(this.kernelSize))
                        .collect(new pl.edu.uj.JImageStream.collectors.BufferedImageCollector())
        );
    }
}
