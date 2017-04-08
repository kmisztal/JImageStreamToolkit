package pl.edu.misztal.JImageStreamToolkit.executors.stephandlergui;

import pl.edu.misztal.JImageStreamToolkit.executors.Executor;
import pl.edu.misztal.JImageStreamToolkit.executors.utils.TimeExecution;
import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.MultiPlugin;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class StepHandlerExecutor extends Executor {
    private String title;
    private StepHandlerExecutorGUI imageList;
    private ProcessingProgress progress;
    private boolean fullscrean = false;

    public StepHandlerExecutor(String filename) throws IOException {
        super(new File(filename));
    }

    public StepHandlerExecutor(String filename, String title) throws IOException {
        super(new File(filename));
        this.title = title;
    }

    public StepHandlerExecutor(File file) throws IOException {
        super(file);
    }

    @Override
    public void executeCase() {
        if (imageList == null) {
            imageList = new StepHandlerExecutorGUI(title, fullscrean);
            progress = new ProcessingProgress(imageList, getPlugins().size());
            imageList.setVisible(true);
        }


        TimeExecution te = new TimeExecution();
        te.startEvent();

        imageList.addImage(currentImage.clone(), new Plugin() {

            @Override
            public void process(Image imgIn, Image imgOut) {
                //intentionally empty
            }

            @Override
            public String getInfo() {
                return "Original Image";
            }

            @Override
            public String getName() {
                return getInfo();
            }


        });

        getPlugins().stream().forEach((p) -> {
            if (p instanceof MultiPlugin) {
                MultiPlugin mp = (MultiPlugin) p;

                mp.getPlugins().stream().forEach((pp) -> {
                    //TODO: make it nicer
                    te.startJob(pp.getName());

                    pp.apply(currentImage);
                    imageList.addImage(currentImage.clone(), pp);
                    progress.increment();
                    te.endJob(true);
                });
            } else {
                te.startJob(p.getName());

                p.apply(currentImage);
                imageList.addImage(currentImage.clone(), p);
                progress.increment();
                te.endJob(true);
            }
        });


        te.stopEvent();
        te.printEventExecutionTime();
    }

    public StepHandlerExecutor setFullscrean(boolean fullscrean) {
        this.fullscrean = fullscrean;
        return this;
    }
}
