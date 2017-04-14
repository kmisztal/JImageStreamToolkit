package pl.edu.misztal.JImageStreamToolkit.plugins.pipelines;

import pl.edu.misztal.JImageStreamToolkit.executors.Executor;
import pl.edu.misztal.JImageStreamToolkit.executors.ExecutorsFactory;
import pl.edu.misztal.JImageStreamToolkit.executors.StepHandlerExecutor;
import pl.edu.misztal.JImageStreamToolkit.plugin.MultiPlugin;
import pl.edu.misztal.JImageStreamToolkit.plugins.binarization.OtsuBinarization;
import pl.edu.misztal.JImageStreamToolkit.plugins.color.GrayScale;

import java.io.File;

public class GUIExecutorTest {
    @org.junit.Test
    public void executeCase() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("lena.png").getFile());
        Executor executor = ExecutorsFactory.getGUIExecutor(file);
        executor.add(new GrayScale());
        executor.execute();

        System.in.read();
    }

    @org.junit.Test
    public void executeMultiPluginCase() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("lena.png").getFile());
        StepHandlerExecutor executor = new StepHandlerExecutor(file);

        MultiPlugin mp = new MultiPlugin();
        mp.add(new GrayScale());
        mp.add(new OtsuBinarization());
        executor.add(mp);
        executor.execute();

//        System.in.read();
    }
}