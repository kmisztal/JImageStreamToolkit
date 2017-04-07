![JImageStreamToolkit Logo](https://github.com/kmisztal/JImageStreamToolkit/blob/master/JImageStreamToolkit_logo.png)

JImageStreamToolkit is a framework to quickly process Image files.

Currently available plugins
- binarization
    - Binarize
    - OtsuBinarization (adapter to JImageStream)
- color
    - ColorNegate (adapter to JImageStream)
    - GrayScale (adapter to JImageStream)
    - HistogramEqualization (adapter to JImageStream)
    - CLAHistogramEqualization: (adapter to JImageStream implementation of [Contrast Limited Adaptive Histogram Equalization](https://en.wikipedia.org/wiki/Adaptive_histogram_equalization))
- morphology
    - Dilatation (adapter to JImageStream)
    - Erosion (adapter to JImageStream)
- statistical
    - MedianFilter (adapter to JImageStream)
- transformations
    - ImageCrop
    - ImageRotate