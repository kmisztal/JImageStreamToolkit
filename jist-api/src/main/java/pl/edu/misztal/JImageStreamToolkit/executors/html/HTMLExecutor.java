package pl.edu.misztal.JImageStreamToolkit.executors.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import pl.edu.misztal.JImageStreamToolkit.executors.StepHandlerExecutor;
import pl.edu.misztal.JImageStreamToolkit.executors.utils.Pair;
import pl.edu.misztal.JImageStreamToolkit.image.Image;
import pl.edu.misztal.JImageStreamToolkit.image.Images;
import pl.edu.misztal.JImageStreamToolkit.plugin.Attributes;
import pl.edu.misztal.JImageStreamToolkit.plugin.Plugin;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.stream.Stream;

public class HTMLExecutor extends StepHandlerExecutor {


    private String lastSavePath;

    public HTMLExecutor(File... files) throws IOException {
        super(files);
    }

    public HTMLExecutor(Image... images) {
        super(images);
    }

    public void save(String destPath) throws IOException {
        this.lastSavePath = destPath + File.separator + "results.html";
        Path dest_ = new File(destPath).toPath();
        if (Files.notExists(dest_)) {
            Files.createDirectory(dest_);
        }

        ClassLoader classLoader = getClass().getClassLoader();

        //prepare html file
        InputStream file = classLoader.getResourceAsStream("example.html");

        Document doc = Jsoup.parse(file, "UTF-8", "https://github.com/kmisztal/JImageStreamToolkit/");

        Path images = new File(destPath + File.separator + "images").toPath();
        if (Files.notExists(images)) {
            Files.createDirectory(images);
        }
        int it = 1;
        System.out.println("Saving images");
        for (Pair<Images, Plugin> p : imageList) {
            System.out.println(it + " / " + imageList.size());
            String res = "images" + File.separator + String.format("%03d", it);

            String[] names = p.getKey().save(destPath + File.separator + res, "png");
            if (names.length > 1) {
                throw new RuntimeException("Saving to much files");
            } else {
                Element ec = doc.select("#tabs-name").first();
                String html = "<li><a href=\"#" + it + "\" data-toggle=\"tab\">" + p.getValue().getName() + "</a></li>";
                Element e = Jsoup.parse(html).getElementsByTag("li").first();
                if (it == 1) {
                    e.addClass("active");
                }
                ec.appendChild(e);

                ec = doc.select("#tabs-content").first();
                html = "<div class=\"tab-pane\" id=\"" + it + "\">" +
                        "<img src=\"" + names[0].substring(destPath.length() + 1) + "\" class=\"img-thumbnail\" alt=\"Responsive image\">" +
                        "<hr>";
                if (!p.getValue().getAttributes().isEmpty()) {
                    html +=
                            "<div class=\"well\">" + html(p.getValue().getAttributes()) + "</div>";
                }
                html += "</div>";
                e = Jsoup.parse(html).getElementsByTag("div").first();
                if (it == 1) {
                    e.addClass("active");
                }
                ec.appendChild(e);
            }
            ++it;
        }

        //save html file
        try (BufferedWriter htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destPath + File.separator + "results.html"), "UTF-8"))) {
            htmlWriter.write(doc.toString());
            htmlWriter.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        file.close();

        String v = classLoader.getResource("assets").getFile();
        String fromPathName = //v;
                System.getProperty("os.name").contains("indow") && !v.startsWith("/") ?
                        v.substring(5) :
                        v;

        Path from = new File(fromPathName).toPath();
        Path to = new File(destPath + File.separator + "assets").toPath();
        try (final Stream<Path> sources = Files.walk(from)) {
            sources.forEach(src -> {
                final Path dest = to.resolve(from.relativize(src).toString());
                try {
                    if (Files.isDirectory(src)) {
                        if (Files.notExists(dest)) {
                            Files.createDirectories(dest);
                        }
                    } else {
                        Files.copy(classLoader.getResourceAsStream("assets"
                                        + File.separator + from.relativize(src).toString()),
                                dest, StandardCopyOption.REPLACE_EXISTING);
                    }
                } catch (IOException et) {
                    throw new RuntimeException("Failed to unzip file.", et);
                }
            });
        } catch (IOException e1) {
            //TODO: it is really bad :(
            String[] srcs = {
                    "/assets/css/tabs.css",
                    "/assets/css/bootstrap.min.css",
                    "/assets/css/ie10-viewport-bug-workaround.css",
                    "/assets/css/starter-template.css",
                    "/assets/img/favicon.ico",
                    "/assets/js/jquery.min.js",
                    "/assets/js/bootstrap.min.js",
                    "/assets/js/ie10-viewport-bug-workaround.js",
                    "/assets/js/ie-emulation-modes-warning.js",
            };
            for (String src : srcs) {
                final Path dest = Paths.get(to.toString(), src.substring(7));
                File parent = dest.toFile().getParentFile();
                if (!parent.exists() && !parent.mkdirs()) {
                    throw new IllegalStateException("Couldn't create dir: " + parent);
                }
                Files.copy(classLoader.getResourceAsStream(src.substring(1)),
                        dest, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    private String html(Attributes attributes) {
        StringBuilder sb = new StringBuilder();
        sb.append("<dl class=\"dl-horizontal\">");
        for (String key : attributes.keySet()) {
            sb.append("<dt>");
            sb.append(key).append("");
            sb.append("</dt>");
            sb.append("<dd>");
            Object o = attributes.get(key);
            if (o instanceof Number) {
                sb.append(o).append("\n");
            } else if (o instanceof Point) {
                final Point p = (Point) o;
                sb.append("[").append(p.getX()).append(";").append(p.getY()).append("]\n");
            } else if (o instanceof Color) {
                sb.append(o).append("\n");
            } else if (o instanceof int[]) {
                sb.append(Arrays.toString((int[]) o));
            } else if (o instanceof int[][]) {
                sb.append("2D array\n");
            } else {
                sb.append("").append(o).append("\n");
            }
            sb.append("</dd>");
        }
        sb.append("</dl>");
        return sb.toString();
    }


    public void open() {
        if (this.lastSavePath != null && new File(this.lastSavePath).exists()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                System.out.println("The file would be open");
                desktop.open(new File(this.lastSavePath));
            } catch (IOException ignored) {

            }
        }
    }
}