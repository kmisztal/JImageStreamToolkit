package pl.edu.misztal.JImageStreamToolkit.plugins.morphology;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;

/**
 * Created by krzys on 24.03.2017.
 */
public class Erosion extends Plugin {
    private final int kernelSize;

    public Erosion(int kernelSize) {
        this.kernelSize = kernelSize;
    }

    public Erosion() {
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
