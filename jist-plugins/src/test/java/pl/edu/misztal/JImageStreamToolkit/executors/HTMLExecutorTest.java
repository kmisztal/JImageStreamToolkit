package pl.edu.misztal.JImageStreamToolkit.executors;

import org.junit.Test;
import pl.edu.misztal.JImageStreamToolkit.executors.html.HTMLExecutor;
import pl.edu.misztal.JImageStreamToolkit.plugin.MultiPlugin;
import pl.edu.misztal.JImageStreamToolkit.plugins.binarization.OtsuBinarization;
import pl.edu.misztal.JImageStreamToolkit.plugins.color.GrayScale;

import java.io.File;

public class HTMLExecutorTest {
    @Test
    public void executeCase() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("lena.png").getFile());
        HTMLExecutor executor = new HTMLExecutor(file);
        executor.add(new GrayScale());
        executor.add(new OtsuBinarization());
        executor.execute();

        executor.save("e:\\github\\JImageStreamToolkit\\results");

//        executor.open();

//        System.in.read();
    }


    @Test
    public void executeCaseMultiPlugin() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("lena.png").getFile());
        HTMLExecutor executor = new HTMLExecutor(file);
        MultiPlugin mp = new MultiPlugin();
        mp.add(new GrayScale());
        mp.add(new OtsuBinarization());
        executor.add(mp);
        executor.execute();

        executor.save("e:\\github\\JImageStreamToolkit\\results_mp");

//        System.in.read();
    }

}