package stephandlergui;

import pl.edu.misztal.JImageStreamToolkit.executors.Executor;
import pl.edu.misztal.JImageStreamToolkit.executors.utils.Pair;
import pl.edu.misztal.JImageStreamToolkit.executors.utils.TimeExecution;
import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.MultiPlugin;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class HTMLStepHandlerExecutor extends Executor {
    private String title;
    private ArrayList<Pair<Image, Plugin>> imageList;
    private ProcessingProgress progress;

    public HTMLStepHandlerExecutor(String filename) throws IOException {
        super(new File(filename));
    }

    public HTMLStepHandlerExecutor(String filename, String title) throws IOException {
        super(new File(filename));
        this.title = title;
    }

    public HTMLStepHandlerExecutor(File file) throws IOException {
        super(file);
    }

    @Override
    public void executeCase() {
        if (imageList == null) {
            imageList = new ArrayList<>();
            progress = new ProcessingProgress(null, getPlugins().size());
        }

        TimeExecution te = new TimeExecution();
        te.startEvent();

        imageList.add(new Pair<>(currentImage.clone(), new Plugin() {

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

        getPlugins().stream().forEach((p) -> {
            if (p instanceof MultiPlugin) {
                MultiPlugin mp = (MultiPlugin) p;

                mp.getPlugins().stream().forEach((pp) -> {
                    //TODO: make it nicer
                    te.startJob(pp.getName());

                    pp.apply(currentImage);
                    imageList.add(new Pair<>(currentImage.clone(), pp));
                    progress.increment();
                    te.endJob(true);
                });
            } else {
                te.startJob(p.getName());

                p.apply(currentImage);
                imageList.add(new Pair<>(currentImage.clone(), p));
                progress.increment();
                te.endJob(true);
            }
        });


        te.stopEvent();
        te.printEventExecutionTime();
    }
}
