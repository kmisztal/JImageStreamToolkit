package pl.edu.misztal.JImageStreamToolkit.executors;

import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.image.Images;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Executor {
    private final Images originalImages = new Images();
    protected Images currentImages = new Images();
    private int currentPlugin = 0;

    private ArrayList<Plugin> plugins = new ArrayList<>();

    public Executor(File... files) throws IOException {
        originalImages.addAll(files);
    }

    public Executor(Image... img) {
        originalImages.addAll(img);
    }

    /**
     * add plugin
     *
     * @param plugin
     */
    public void add(Plugin plugin) {
        plugins.add(plugin);
    }

    public void add(Plugin... plugin) {
        Collections.addAll(plugins, plugin);
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
        plugins.add(plugin);
    }

    public abstract void executeCase();

    public final void execute() {
        preExecuteCase();
        currentImages.fillWithCopy(originalImages);
        executeCase();
        currentPlugin = plugins.size();
        postExecuteCase();
    }

    protected void postExecuteCase() {

    }

    protected void preExecuteCase() {

    }

    /**
     * invoke the save methods from Image class
     *
     * @param filename - destination file name
     */
    public void save(String filename, String suffix) {
        currentImages.save(filename, suffix);
    }

    /**
     * @return original image
     */
    public Images getOriginalImage() {
        return originalImages;
    }

    /**
     * @return result image after applying all plugins
     */
    public Images getResultImage() {
        return currentImages;
    }

    /**
     * @return - number of plugins to apply
     */
    public int getLengthOfTask() {
        return plugins.size();
    }

    public List<Plugin> getPlugins() {
        return plugins.subList(currentPlugin, plugins.size());
    }

    public Plugin getRecentPlugin() {
        return plugins.get(plugins.size() - 1);
    }

}
