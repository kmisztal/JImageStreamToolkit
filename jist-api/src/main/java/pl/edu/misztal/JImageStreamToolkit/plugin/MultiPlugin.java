package pl.edu.misztal.JImageStreamToolkit.plugin;

import pl.edu.misztal.JImageStreamToolkit.image.Image;

import java.util.ArrayList;

public class MultiPlugin extends Plugin {
    private ArrayList<Plugin> plugins = new ArrayList<>();

    public MultiPlugin() {
    }

    public void add(Plugin plugin) {
        plugin.setAttributes(this.getAttributes());
        this.plugins.add(plugin);
    }

    /**
     * methods to add new plugin with its attributes
     *
     * @param plugin - Plugin class
     * @param params - attributes of given plugin
     */
    public void add(Plugin plugin, Object... params) {
        if (params.length % 2 != 0) {
            throw new RuntimeException("Wrong plugin initialization");
        }
        for (int i = 0; i < params.length; ) {
            plugin.setAttribute((String) params[i++], params[i++]);
        }
        plugin.setAttributes(this.getAttributes());
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
