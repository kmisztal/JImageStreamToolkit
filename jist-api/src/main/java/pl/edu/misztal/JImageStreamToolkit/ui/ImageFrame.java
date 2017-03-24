package pl.edu.misztal.JImageStreamToolkit.ui;

import pl.edu.misztal.JImageStreamToolkit.image.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FilenameFilter;

public final class ImageFrame extends JDialog {

    String core_title;
    FilenameFilter fileNameFilter;
    ResizableImagePanel imageView;

    public ImageFrame(String title) {
        super((Window) null);
        setModal(true);
        LookAndFeel.doIt();
        EscapeClose.doIt(this);

        imageView = new ResizableImagePanel();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        add(new JScrollPane(imageView));
        setLocationByPlatform(true);
        pack();
        setSize(800, 600);
        setTitle(title == null ? "Step Handler Executor" : title);

        imageView.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent me) {
                ImageFrame.this.setTitle(ImageFrame.this.core_title + " ("
                        + v((me.getX() - imageView.getxPos()) / imageView.getScale(), imageView.getOriginalImage().getWidth())
                        + ";"
                        + v((me.getY() - imageView.getyPos()) / imageView.getScale(), imageView.getOriginalImage().getHeight()) + ")");
            }
        });

        // make the cursor a crosshair shape
        imageView.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));

        fileNameFilter = (File file, String namme1) -> true;
    }

    public ImageFrame(Image image) {
        this();
        imageView.setImage(image.getBufferedImage());
    }

    public ImageFrame(String title, Image image) {
        this(title);
        imageView.setImage(image.getBufferedImage());
    }

    private ImageFrame() {
        this("");
    }

    public static void display(Image img) {
        ImageFrame i = new ImageFrame(img);
        i.display();
    }

    public static void displayCopy(Image img) {
        display(img.clone());
    }

    private String v(double val, int max) {
        if (val < 0) {
            return "0";
        } else if (val > max) {
            return "" + max;
        } else {
            return "" + (int) val;
        }
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title); //To change body of generated methods, choose Tools | Templates.
        if (core_title == null) {
            core_title = title;
        }
    }

    public void display() {
        this.setVisible(true);
    }

    public void display(boolean modal) {
        this.setModal(modal);
        this.setVisible(true);
    }

}
