package pl.edu.misztal.JImageStreamToolkit.plugins.binarization;

import org.junit.Test;
import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugins.color.GrayScale;
import pl.edu.misztal.JImageStreamToolkit.ui.ImageFrame;

import java.io.File;

public class OtsuBinarizationTest {
    @Test
    public void process() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("lena.png").getFile());

        Image image = new Image(file);
        new GrayScale().process(image);
        new OtsuBinarization().process(image);
        new ImageFrame(image).display();
    }

}