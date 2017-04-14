package pl.edu.misztal.JImageStreamToolkit.executors;

import pl.edu.misztal.JImageStreamToolkit.executors.gui.GUIExecutor;
import pl.edu.misztal.JImageStreamToolkit.executors.html.HTMLExecutor;
import pl.edu.misztal.JImageStreamToolkit.image.Image;

import java.io.File;
import java.io.IOException;

public class ExecutorsFactory {

    public static HTMLExecutor getHTMLExecutor(File... files) throws IOException {
        return new HTMLExecutor(files);
    }

    public static HTMLExecutor getHTMLExecutor(Image... images) throws IOException {
        return new HTMLExecutor(images);
    }

    public static GUIExecutor getGUIExecutor(File... files) throws IOException {
        return new GUIExecutor(files);
    }

    public static GUIExecutor getGUIExecutor(Image... images) throws IOException {
        return new GUIExecutor(images);
    }
}
