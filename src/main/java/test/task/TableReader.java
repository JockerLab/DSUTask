package test.task;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class TableReader {
    private boolean isCorrectValues(List<String> values) {
        for (String value : values) {
            if (!value.matches("^\\d*(\\.\\d+)?$")) {
                return false;
            }
        }

        return true;
    }

    private List<String> parseStringColumns(String line) {
        return Stream.of(line.split(";"))
            .map(String::trim)
            .map(e ->
                (e.startsWith("\"") && e.endsWith("\""))
                    ? e.substring(1, e.length() - 1)
                    : e
            ).toList();
    }

    /**
     * Read file fileName and write unique and valid rows to {@link Table}.
     * Returns null if an error occurred while reading file.
     * @param fileName — file name
     * @return {@link Table}
     */
    public Table read(String fileName) {
        Table table = new Table();
        Set<String> existingRows = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> stringColumns = parseStringColumns(line);

                if (!isCorrectValues(stringColumns) || existingRows.contains(line)) {
                    continue;
                }

                table.addRow(stringColumns);
                existingRows.add(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File \"" + fileName + "\" not found");
            return null;
        } catch (IOException e) {
            System.err.println("An error occurred while reading file. " + e.getMessage());
            return null;
        }

        return table;
    }
}
