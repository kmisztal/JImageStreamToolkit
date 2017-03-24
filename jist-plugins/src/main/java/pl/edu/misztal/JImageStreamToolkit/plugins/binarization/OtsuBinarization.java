package pl.edu.misztal.JImageStreamToolkit.plugins.binarization;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;


public class OtsuBinarization extends Plugin {
    public void process(Image imgIn, Image imgOut) {
        imgOut.setBufferedImage(
                imgIn.parallelStream()
                        .apply(new pl.edu.uj.JImageStream.filters.color.OtsuBinarization())
                        .collect(new pl.edu.uj.JImageStream.collectors.BufferedImageCollector())
        );
    }
}
