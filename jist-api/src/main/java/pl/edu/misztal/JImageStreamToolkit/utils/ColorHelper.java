package pl.edu.misztal.JImageStreamToolkit.utils;

public enum ColorHelper {

    RED(0) {
        @Override
        public int getColor(int rgb) {
            return (rgb >> 16) & 0xFF;
        }
    },
    GREEN(1) {
        @Override
        public int getColor(int rgb) {
            return (rgb >> 8) & 0xFF;
        }
    },
    BLUE(2) {
        @Override
        public int getColor(int rgb) {
            return (rgb) & 0xFF;
        }
    },
    ALPHA(3) {
        @Override
        public int getColor(int rgb) {
            return (rgb >> 24) & 0xff;
        }
    };
    private final int value;

    ColorHelper(int v) {
        value = v;
    }

    public static int getSize() {
        return 3;
    }

    public static int check(int val) {
        if (val < 0) {
            return 0;
        }
        if (val > 255) {
            return 255;
        }
        return val;
    }

    public static int getRGB(int r, int g, int b, int a) {
        return ((a & 0xFF) << 24) |
                ((r & 0xFF) << 16) |
                ((g & 0xFF) << 8) |
                ((b & 0xFF));
    }

    public static int getRGB(int r, int g, int b) {
        return getRGB(r, g, b, 255);
    }

    public abstract int getColor(int rgb);

    /**
     * Limits the color value between 0 and 255.
     *
     * @param color - color to check
     * @return int - the color value
     */
    public int limit8bitsColor(int color) {
        if (color > 255) {
            color = 255;
            return (color);
        }

        if (color < 0) {
            color = 0;
            return (color);
        }
        return color;
    }
}