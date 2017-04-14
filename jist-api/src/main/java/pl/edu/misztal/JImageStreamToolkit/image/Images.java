package pl.edu.misztal.JImageStreamToolkit.image;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Images extends ArrayList<Image> implements Cloneable {

    public boolean addAll(File[] c) {
        boolean ret = true;
        for (File f : c) {
            try {
                this.add(new Image(f));
            } catch (IOException e) {
                ret = false;
            }
        }
        return ret;
    }

    public boolean addAll(Image[] c) {
        return Collections.addAll(this, c);
    }

    public void fillWithCopy(Images images) {
        for (Image i : images) {
            add(i.clone());
        }
    }

    public String[] save(String filename, String suffix) {
        suffix = suffix.startsWith(".") ? suffix : "." + suffix;
        if (this.size() == 1) {
            this.get(0).save(filename + suffix);
            return new String[]{filename + suffix};
        } else {
            int it = 0;
            String[] ret = new String[this.size()];
            for (Image i : this) {
                ret[it] = filename + String.format("_%03d", it) + suffix;
                i.save(ret[it++]);
            }
            return ret;
        }
    }

    @Override
    public Images clone() {
        Images ret = new Images();
        for (Image i : this) {
            ret.add(i.clone());
        }
        return ret;
    }
}

