package pl.edu.misztal.JImageStreamToolkit.plugins.color;

import org.junit.Test;
import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.ui.ImageFrame;
import pl.edu.misztal.JImageStreamToolkit.ui.histogram.HistogramGUI;

import java.awt.*;
import java.io.File;

/**
 * Created by krzys on 02.04.2017.
 */
public class HistogramEqualizationTest {
    @Test
    public void process() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("hist.png").getFile());

        Image image = new Image(file);
        new ImageFrame("Original", image).display();
        new HistogramGUI(image.clone(), false).display();
        new HistogramEqualization().process(image);

        Graphics2D g = image.getBufferedImage().createGraphics();
        g.setColor(Color.RED);
        g.fillOval(100, 100, 20, 20);
        g.dispose();

        new ImageFrame("After", image).display();
        new HistogramGUI(image.clone(), false).display();

        System.in.read();
    }

}