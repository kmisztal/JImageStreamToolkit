package pl.edu.misztal.JImageStreamToolkit.executors.utils;

import org.apache.commons.lang.StringUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

public class TimeExecution {

    Instant startEvent;
    Instant stopEvent;
    Instant startJob;
    Instant stopJob;

    String currentName;

    List<Pair<String, Long>> timelist = new LinkedList<>();

    public void startEvent() {
        startEvent = Instant.now();
    }

    public void stopEvent() {
        stopEvent = Instant.now();
    }

    public void startJob(String name) {
        currentName = name;
        startJob = Instant.now();
    }

    public void endJob(boolean print) {
        stopJob = Instant.now();
        final long v = ChronoUnit.MILLIS.between(startJob, stopJob);
        timelist.add(new Pair<>(currentName, v));
        if (print)
            System.out.println(currentName + " : " + v);
    }

    public String printEventExecutionTime() {
        final long v = ChronoUnit.MILLIS.between(startEvent, stopEvent);
        timelist.add(new Pair<>("Total time", v));

        TableBuilder tb = new TableBuilder();
        tb.addRow("Plugin", "ms");
        for (Pair<String, Long> entry : timelist) {
            tb.addRow(entry.getKey(), entry.getValue().toString());
        }
        return tb.toString();
    }


    public class TableBuilder {
        List<String[]> rows = new LinkedList<String[]>();

        void addRow(String... cols) {
            rows.add(cols);
        }

        int length(final CharSequence cs) {
            return cs == null ? 0 : cs.length();
        }

        private int[] colWidths() {
            int cols = -1;

            for (String[] row : rows)
                cols = Math.max(cols, row.length);

            int[] widths = new int[cols];

            for (String[] row : rows) {
                for (int colNum = 0; colNum < row.length; colNum++) {
                    widths[colNum] =
                            Math.max(
                                    widths[colNum],
                                    length(row[colNum]));
                }
            }
            return widths;
        }

        @Override
        public String toString() {
            StringBuilder buf = new StringBuilder();

            int[] colWidths = colWidths();

            for (String[] row : rows) {
                for (int colNum = 0; colNum < row.length; colNum++) {
                    buf.append(
                            StringUtils.rightPad(
                                    StringUtils.defaultString(
                                            row[colNum]), colWidths[colNum]));
                    buf.append(' ');
                }

                buf.append('\n');
            }

            return buf.toString();
        }

    }

}