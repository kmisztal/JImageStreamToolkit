package pl.edu.misztal.JImageStreamToolkit.plugin;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedHashMap;


public class Attributes implements Cloneable {
    private final LinkedHashMap<String, Object> hashAttributes;

    /**
     * Constructor
     */
    public Attributes() {
        hashAttributes = new LinkedHashMap<>();
    }

    /**
     * Set an attribute.
     *
     * @param name  attribute name.
     * @param value attribute value.
     */
    public void set(String name, Object value) {
        hashAttributes.put(name, value);
    }

    /**
     * Set a list of parameters. Format: (String)name, (Object)value...
     *
     * @param params
     */
    public void set(Object... params) {
        for (int i = 0; i < params.length; i += 2) {
            hashAttributes.put((String) params[i], params[i + 1]);
        }
    }

    /**
     * Get an attribute by its name.
     *
     * @param name attribute's name.
     * @return the specified attribute as an Object.
     */
    public Object get(String name) {
        return hashAttributes.get(name);
    }

    public Object get(String name, Object defaultValue) {
        Object o = get(name);
        if (o != null) {
            return o;
        }
        return defaultValue;
    }

    /**
     * Returns all attributes' name and value as a String array.
     *
     * @return string array with all attributes' name and value.
     */
    public String[] toStringArray() {
        String attrs[] = new String[hashAttributes.size() * 2];
        String[] keys = hashAttributes.keySet().toArray(new String[0]);
        for (int x = 0; x < keys.length; x++) {
            attrs[(x * 2)] = keys[x];
            attrs[(x * 2) + 1] = "" + arrayString(hashAttributes.get(keys[x]));
        }
        return attrs;
    }

    public String arrayString(Object o) {
        if (o instanceof double[][]) {
            return Arrays.deepToString((double[][]) o);
        } else {
            return "" + o;
        }
    }

    /**
     * returns an array containing the attrbiute values
     *
     * @return
     */
    public Object[] getValues() {
        Object o[] = hashAttributes.entrySet().toArray(new Object[0]);
        return o;
    }

    /**
     * Clones a MarvinAttributes Object.
     *
     * @return
     */
    @Override
    public Attributes clone() {
        Attributes attrs = new Attributes();
        String[] keys = hashAttributes.keySet().toArray(new String[0]);
        for (String key : keys) {
            attrs.set(key, hashAttributes.get(key));
        }
        return attrs;
    }

    public boolean isEmpty() {
        return hashAttributes.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();

        for (String key : hashAttributes.keySet()) {
            ret.append(key).append(" : ");
            Object o = hashAttributes.get(key);
            if (o instanceof Number) {
                ret.append(o).append("\n");
            } else if (o instanceof Point) {
                final Point p = (Point) o;
                ret.append("[").append(p.getX()).append(";").append(p.getY()).append("]\n");
            } else if (o instanceof Color) {
                ret.append(o).append("\n");
            } else if (o instanceof int[]) {
                ret.append(Arrays.toString((int[]) o));
            } else if (o instanceof int[][]) {
                ret.append("2D array\n");
            } else {
                ret.append("").append(o).append("\n");
            }
        }

        return ret.toString();
    }

    public void update(Attributes attributes) {
        String[] keys = attributes.hashAttributes.keySet().toArray(new String[0]);
        for (String key : keys) {
            set(key, attributes.get(key));
        }
    }
}
