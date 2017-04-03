package pl.edu.misztal.JImageStreamToolkit.plugin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.edu.misztal.JImageStreamToolkit.image.Image;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public abstract class Plugin {
    protected final Logger logger = LogManager.getLogger(this.getClass());
    private Attributes attributes = new Attributes();

    /**
     * Executes the algorithm.
     *
     * @param imgIn  input image.
     * @param imgOut output image
     */
    protected abstract void process(
            Image imgIn,
            Image imgOut
    );

    /**
     * Executes the algorithm.
     *
     * @param imgIn  input image.
     * @param imgOut output image
     */
    public void apply(
            Image imgIn,
            Image imgOut
    ) {
        Instant startTime = Instant.now();
        logger.info(this.getClass() + " starting filter");
        process(imgIn, imgOut);
        logger.info("filter {} has ended, time {} ms", this.getClass(), ChronoUnit.MILLIS.between(startTime, Instant.now()));
    }

    /**
     * Executes the algorithm.
     *
     * @param imgInAndOut input and output image.
     */
    protected final void process(
            Image imgInAndOut
    ) {
        process(imgInAndOut, imgInAndOut);
    }

    /**
     * Executes the algorithm.
     *
     * @param imgInAndOut input and output image.
     */
    public final void apply(
            Image imgInAndOut
    ) {
        Instant startTime = Instant.now();
        logger.info(this.getClass() + " starting filter");
        process(imgInAndOut, imgInAndOut);
        logger.info("filter {} has ended, time {} ms", this.getClass(), ChronoUnit.MILLIS.between(startTime, Instant.now()));
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        attributes.set(key, value);
    }

    public String getName() {
        String[] name = this.getClass().getName().split("\\.");
        return name[name.length - 1];
    }

    public String getInfo() {
        String ret = "<html><body><strong>Plugin:<br/></strong> " + getName() + "<br/>";
        if (!attributes.isEmpty()) {
            ret += "<strong>Attributes:</strong><br/>";
            ret += attributes.toString();
        }
        return ret;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

}
