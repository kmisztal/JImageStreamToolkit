package pl.edu.misztal.JImageStreamToolkit.executors.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import pl.edu.misztal.JImageStreamToolkit.executors.StepHandlerExecutor;
import pl.edu.misztal.JImageStreamToolkit.image.Image;

import java.io.File;
import java.io.IOException;

public class HTMLExecutor extends StepHandlerExecutor {
    public HTMLExecutor(File... files) throws IOException {
        super(files);
    }

    public HTMLExecutor(Image... images) {
        super(images);
    }

    @Override
    protected void postExecuteCase() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("example.html").getFile());

        Document doc;
        try {
            doc = Jsoup.parse(file, "UTF-8", "https://github.com/kmisztal/JImageStreamToolkit/");
        } catch (IOException e) {

        }
    }
}