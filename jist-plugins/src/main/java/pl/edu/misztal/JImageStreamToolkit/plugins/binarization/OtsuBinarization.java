package pl.edu.misztal.JImageStreamToolkit.plugins.binarization;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;
import pl.edu.uj.JImageStream.api.core.Filter;
import pl.edu.uj.JImageStream.model.Pixel;

import java.util.stream.IntStream;


public class OtsuBinarization extends Plugin {
    private boolean weighted = false;
    private int threshold;

    public OtsuBinarization() {
    }

    public OtsuBinarization(boolean weighted) {
        this.weighted = weighted;
    }

    protected void process(Image imgIn, Image imgOut) {
        imgOut.setBufferedImage(
                imgIn.parallelStream()
                        .apply(new Filter() {
                            @Override
                            public void setUp() {
                                super.setUp();
                                int sourceHeight = this.getSourceHeight();
                                int sourceWidth = this.getSourceWidth();
                                double[] histogram = new double[256];

                                int total;
                                int i;
                                for (total = 0; total < sourceHeight; ++total) {
                                    for (int j = 0; j < sourceWidth; ++j) {
                                        i = (int) IntStream.of(this.getPixel(j, total).getColors()).limit(3L).average().getAsDouble();
                                        ++histogram[i];
                                    }
                                }

                                total = sourceHeight * sourceWidth;
                                float sum = 0.0F;

                                for (i = 0; i < 256; ++i) {
                                    sum = (float) ((double) sum + (double) i * histogram[i]);
                                }

                                float sumB = 0.0F;
                                int wB = 0;
                                float varMax = 0.0F;
                                threshold = 0;

                                for (int k = 0; k < 256; ++k) {
                                    if (weighted) {
                                        histogram[k] *= Math.exp((double) (-k) / 100.0D);
                                    }

                                    wB = (int) ((double) wB + histogram[k]);
                                    if (wB != 0) {
                                        int wF = total - wB;
                                        if (wF == 0) {
                                            break;
                                        }

                                        sumB += (float) ((double) k * histogram[k]);
                                        float mB = sumB / (float) wB;
                                        float mF = (sum - sumB) / (float) wF;
                                        float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
                                        if (varBetween > varMax) {
                                            varMax = varBetween;
                                            threshold = k;
                                        }
                                    }
                                }

                                if (threshold == 0) {
                                    ++threshold;
                                }
                            }

                            @Override
                            public void apply(int x, int y) {
                                Pixel pixel = this.getPixel(x, y);
                                if (IntStream.of(pixel.getColors()).limit(3L).average().getAsDouble() < (double) threshold) {
                                    this.setPixel(x, y, new Pixel(0, 0, 0, 255));
                                } else {
                                    this.setPixel(x, y, new Pixel(255, 255, 255, 255));
                                }
                            }
                        })
                        .collect(new pl.edu.uj.JImageStream.collectors.BufferedImageCollector())
        );

        setAttribute("threshold", threshold);
        setAttribute("weighted", weighted);

    }
}
