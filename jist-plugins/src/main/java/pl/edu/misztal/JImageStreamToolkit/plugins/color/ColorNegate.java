package pl.edu.misztal.JImageStreamToolkit.plugins.color;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;

public class ColorNegate extends Plugin {
    @Override
    protected void process(Image imgIn, Image imgOut) {
        imgOut.setBufferedImage(
                imgIn.parallelStream()
                        .apply(new pl.edu.uj.JImageStream.filters.arithmetic.InvertFilter())
                        .collect(new pl.edu.uj.JImageStream.collectors.BufferedImageCollector())
        );
    }
}
