package test.task;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TableWriter {
    public void write(List<List<Integer>> groups, Table table, String fileName) {
        int groupsWithMoreOneRow = 0;

        for (int group = 0; group < groups.size(); group++) {
            if (groups.get(group).size() > 1) {
                groupsWithMoreOneRow++;
            }
        }

        try (PrintWriter writer = new PrintWriter(fileName, StandardCharsets.UTF_8)) {
            writer.println("Групп с больше чем одной строкой: " + groupsWithMoreOneRow);
            writer.println();

            for (int group = 0; group < groups.size(); group++) {
                writer.println("Группа " + (group + 1));
                for (int rowId : groups.get(group)) {
                    String row = table.getFormattedRow(rowId);
                    writer.println(row);
                }
                writer.println();
            }
        } catch (IOException e) {
            System.err.println("An error occurred while writing to file. " + e.getMessage());
        }
    }
}
