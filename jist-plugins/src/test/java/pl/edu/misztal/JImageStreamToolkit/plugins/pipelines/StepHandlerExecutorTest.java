package pl.edu.misztal.JImageStreamToolkit.plugins.pipelines;

import pl.edu.misztal.JImageStreamToolkit.executors.stephandlergui.StepHandlerExecutor;
import pl.edu.misztal.JImageStreamToolkit.plugins.color.GrayScale;

import java.io.File;

/**
 * Created by krzys on 03.04.2017.
 */
public class StepHandlerExecutorTest {
    @org.junit.Test
    public void executeCase() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("lena.png").getFile());
        StepHandlerExecutor executor = new StepHandlerExecutor(file);

        executor.add(new GrayScale());
        executor.execute();

        System.in.read();
    }
}