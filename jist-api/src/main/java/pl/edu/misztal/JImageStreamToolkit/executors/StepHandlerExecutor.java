package pl.edu.misztal.JImageStreamToolkit.executors;

import pl.edu.misztal.JImageStreamToolkit.executors.gui.ProcessingProgress;
import pl.edu.misztal.JImageStreamToolkit.executors.utils.Pair;
import pl.edu.misztal.JImageStreamToolkit.executors.utils.TimeExecution;
import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.image.Images;
import pl.edu.misztal.JImageStreamToolkit.plugin.MultiPlugin;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class StepHandlerExecutor extends Executor {
    protected ArrayList<Pair<Images, Plugin>> imageList;
    private ProcessingProgress progress;

    public StepHandlerExecutor(File... files) throws IOException {
        super(files);
    }

    public StepHandlerExecutor(Image... img) {
        super(img);
    }

    @Override
    public void executeCase() {
        if (imageList == null) {
            imageList = new ArrayList<>();
            progress = new ProcessingProgress(null, getPlugins().size());
        }

        TimeExecution te = new TimeExecution();
        te.startEvent();

        imageList.add(new Pair<>(currentImages.clone(), new Plugin() {

            @Override
            protected void process(Image imgIn, Image imgOut) {
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
        }));

        getPlugins().forEach((p) -> {
            if (p instanceof MultiPlugin) {
                MultiPlugin mp = (MultiPlugin) p;

                mp.getPlugins().forEach((pp) -> {
                    //TODO: make it nicer
                    te.startJob(pp.getName());

                    pp.apply(currentImages);
                    imageList.add(new Pair<>(currentImages.clone(), pp));
                    progress.increment();
                    te.endJob(false);
                });
            } else {
                te.startJob(p.getName());

                p.apply(currentImages);
                imageList.add(new Pair<>(currentImages.clone(), p));
                progress.increment();
                te.endJob(false);
            }
        });

        te.stopEvent();
        System.out.println(te.printEventExecutionTime());
    }
}

