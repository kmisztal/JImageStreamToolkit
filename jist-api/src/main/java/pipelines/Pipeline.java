package pipelines;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;

import java.util.ArrayList;

public class Pipeline extends Plugin {
    private ArrayList<Plugin> plugins = new ArrayList<>();

    public Pipeline(Plugin plugin) {
        plugins.add(plugin);
    }

    @Override
    public void process(Image imgIn, Image imgOut) {
        Image tmp = imgIn.clone();
        for (Plugin p : plugins) {
            p.process(tmp);
        }
        imgOut.setBufferedImage(tmp.getBufferedImage());
    }
}
