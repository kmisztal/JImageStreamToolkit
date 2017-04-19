package pl.edu.misztal.JImageStreamToolkit.plugins.morphology;

import org.junit.Test;
import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.ui.ImageFrame;

import java.io.File;

public class ErosionTest {
    @Test
    public void process() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("morph2.png").getFile());

        Image image = new Image(file);
        new ImageFrame(image).display();
        new Erosion(4).apply(image);
        new ImageFrame(image).display();

        System.in.read();
    }

}