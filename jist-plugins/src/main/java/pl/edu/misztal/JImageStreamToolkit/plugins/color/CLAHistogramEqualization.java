package pl.edu.misztal.JImageStreamToolkit.plugins.color;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.uj.JImageStream.collectors.BufferedImageCollector;
import pl.edu.uj.JImageStream.filters.statistical.CLAHEFilter;

public class CLAHistogramEqualization {
    protected void process(Image imgIn, Image imgOut) {
        imgOut.setBufferedImage(
                imgIn.parallelStream()
                        .apply(new CLAHEFilter())
                        .collect(new BufferedImageCollector())
        );
    }
}
