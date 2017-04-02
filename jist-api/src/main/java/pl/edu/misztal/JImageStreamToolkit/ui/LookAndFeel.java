package pl.edu.misztal.JImageStreamToolkit.ui;

import javax.swing.*;

public class LookAndFeel {

    /**
     * ustawia look and feel systemu dla aplikacji okienkowej
     */
    static void doIt() {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // handle exception
        }
    }
}