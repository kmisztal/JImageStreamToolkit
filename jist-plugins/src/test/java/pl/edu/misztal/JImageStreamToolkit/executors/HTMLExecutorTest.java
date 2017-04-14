package pl.edu.misztal.JImageStreamToolkit.executors;

import org.junit.Test;
import pl.edu.misztal.JImageStreamToolkit.executors.html.HTMLExecutor;
import pl.edu.misztal.JImageStreamToolkit.plugins.color.GrayScale;

import java.io.File;

/**
 * Created by krzys on 14.04.2017.
 */
public class HTMLExecutorTest {
    @Test
    public void executeCase() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("lena.png").getFile());
        HTMLExecutor executor = new HTMLExecutor(file);
        executor.add(new GrayScale());
        executor.execute();

//        System.in.read();
    }

}