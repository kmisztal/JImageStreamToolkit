package pl.edu.misztal.JImageStreamToolkit.executors.gui;

import pl.edu.misztal.JImageStreamToolkit.executors.StepHandlerExecutor;
import pl.edu.misztal.JImageStreamToolkit.executors.utils.Pair;
import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;
import pl.edu.misztal.JImageStreamToolkit.ui.EscapeClose;
import pl.edu.misztal.JImageStreamToolkit.ui.LookAndFeel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public final class GUIExecutor extends StepHandlerExecutor {
    FilenameFilter fileNameFilter;
    DefaultListModel model;
    private JFrame mainFrame;
    private JList imageList;
    private JEditorPane info;
    private ResizableImagePanel imageView;
    private JPanel gui;


    public GUIExecutor(File... files) throws IOException {
        super(files);
        initGUI();
    }

    public GUIExecutor(Image... img) {
        super(img);
        initGUI();
    }

    private void initGUI() {
        mainFrame = new JFrame("GUI Step Handler Executor");
        LookAndFeel.doIt();
        EscapeClose.doIt(mainFrame);

        gui = new JPanel(new GridLayout());

        imageView = new ResizableImagePanel();

        info = new JEditorPane();
        info.setContentType("text/html");
        info.setEditable(false);

        model = new DefaultListModel();
        imageList = new JList(model);
        imageList.setCellRenderer(new IconCellRenderer());
        ListSelectionListener listener = (ListSelectionEvent lse) -> {
            if (imageList.getSelectedValue() instanceof Pair) {
                Pair<Image, Plugin> o = (Pair<Image, Plugin>) imageList.getSelectedValue();
                imageView.setImage(o.getKey().getBufferedImage());
                info.setText(o.getValue().getInfo().replaceAll("\n", "<br/>"));
            }
        };


        imageList.addListSelectionListener(listener);

        fileNameFilter = (File file, String name1) -> true;

        JScrollPane guiSP = new JScrollPane(
                info,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        gui.add(new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new JSplitPane(
                        JSplitPane.VERTICAL_SPLIT,
                        guiSP,
                        new JScrollPane(
                                imageList,
                                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
                ),
                new JScrollPane(imageView)));
        guiSP.setMaximumSize(new Dimension(Short.MAX_VALUE, 150));
        guiSP.setMinimumSize(new Dimension(10, 150));

        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.add(getGui());
        mainFrame.setLocationByPlatform(true);
        mainFrame.pack();
        mainFrame.setSize(800, 600);
        mainFrame.setVisible(true);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            GUIExecutor imageList1 = new GUIExecutor();
//            imageList1.setVisible(true);
//        });
//    }

    private Container getGui() {
        return gui;
    }

    public void addImage(Image img, Plugin plugin) {
        model.addElement(new Pair<>(img, plugin));
        imageList.setSelectedIndex(imageList.getModel().getSize() - 1);
    }


    class IconCellRenderer extends DefaultListCellRenderer {

        private static final long serialVersionUID = 1L;

        private int size;
        private BufferedImage icon;

        IconCellRenderer() {
            this(100);
        }

        IconCellRenderer(int size) {
            this.size = size;
            icon = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        }

        @Override
        public Component getListCellRendererComponent(
                JList list,
                Object value,
                int index,
                boolean isSelected,
                boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (c instanceof JLabel && value instanceof Pair) {
                Pair<Image, Plugin> pair = (Pair) value;
                Image i = pair.getKey();
                Plugin plugin = pair.getValue();

                JLabel l = (JLabel) c;
                l.setText(plugin.getName());
                l.setIcon(new ImageIcon(icon));

                final String html
                        = "<html><body>"
                        + "<p>Look Ma, no hands!";
                l.setToolTipText(html);

                Graphics2D g = icon.createGraphics();
                g.setColor(Color.white);
                g.setBackground(Color.white);
                g.clearRect(0, 0, size, size);

                int w = i.getWidth(), h = i.getHeight(), begx = 0, begy = 0;
                if (w > h) {
                    h = (int) (1.0 * h / w * size);
                    w = size;
                    begy = (size - h) / 2;
                } else {
                    w = (int) (1.0 * w / h * size);
                    h = size;
                    begx = (size - w) / 2;
                }

                g.drawImage(i.getBufferedImage(), begx, begy, w, h, this);

                g.dispose();
            }
            return c;
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(size, size);
        }

    }
}

