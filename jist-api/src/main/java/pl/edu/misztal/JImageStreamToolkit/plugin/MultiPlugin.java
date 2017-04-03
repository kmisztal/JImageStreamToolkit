package pl.edu.misztal.JImageStreamToolkit.plugin;

import pl.edu.misztal.JImageStreamToolkit.image.Image;

import java.util.ArrayList;

public class MultiPlugin extends Plugin {
    private ArrayList<Plugin> plugins = new ArrayList<>();

    public MultiPlugin(Plugin plugin) {
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
