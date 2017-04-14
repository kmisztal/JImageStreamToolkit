package pl.edu.misztal.JImageStreamToolkit.executors.html;

import pl.edu.misztal.JImageStreamToolkit.executors.StepHandlerExecutor;
import pl.edu.misztal.JImageStreamToolkit.image.Image;

import java.io.File;
import java.io.IOException;

public class HTMLStepHandlerExecutor extends StepHandlerExecutor {
    public HTMLStepHandlerExecutor(File... files) throws IOException {
        super(files);
    }

    public HTMLStepHandlerExecutor(Image... img) {
        super(img);
    }
}