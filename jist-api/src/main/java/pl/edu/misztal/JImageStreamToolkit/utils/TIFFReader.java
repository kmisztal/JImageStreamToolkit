package pl.edu.misztal.JImageStreamToolkit.utils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class TIFFReader {
    static BufferedImage load(File file) throws IOException {
        // Create input stream
        ImageInputStream input = ImageIO.createImageInputStream(file);

        try {
            // Get the reader
            Iterator<ImageReader> readers = ImageIO.getImageReaders(input);

            if (!readers.hasNext()) {
                throw new IllegalArgumentException("No reader for: " + file);
            }

            ImageReader reader = readers.next();

            try {
                reader.setInput(input);

                // Optionally, listen for read warnings, progress, etc.
//                reader.addIIOReadWarningListener(...);
//                reader.addIIOReadProgressListener(...);

                ImageReadParam param = reader.getDefaultReadParam();

                // Optionally, control read settings like sub sampling, source region or destination etc.
//                param.setSourceSubsampling(...);
//                param.setSourceRegion(...);
//                param.setDestination(...);
                // ...

                // Finally read the image, using settings from param
                BufferedImage image = reader.read(0, param);

                return image;

                // Optionally, read thumbnails, meta data, etc...
//                int numThumbs = reader.getNumThumbnails(0);
                // ...
            } finally {
                // Dispose reader in finally block to avoid memory leaks
                reader.dispose();
            }
        } finally {
            // Close stream in finally block to avoid resource leaks
            input.close();
        }
    }
}
