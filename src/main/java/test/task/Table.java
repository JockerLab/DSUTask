package test.task;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private int rows, columns;
    private final List<List<String>> table;

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public List<List<String>> getTable() {
        return table;
    }

    public String get(int row, int column) {
        if (row < table.size() && column < table.get(row).size()) {
            String value = table.get(row).get(column);
            if (value.isEmpty()) {
                return null;
            }
            return value;
        }

        return null;
    }

    public String getFormattedRow(int row) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < table.get(row).size(); i++) {
            if (i > 0) {
                str.append(";");
            }
            str.append("\"").append(table.get(row).get(i)).append("\"");
        }

        return str.toString();
    }

    public void addRow(List<String> stringColumns) {
        table.add(new ArrayList<>());
        table.getLast().addAll(stringColumns);
        columns = Math.max(columns, stringColumns.size());
        rows++;
    }

    Table() {
        this.table = new ArrayList<>();
        this.rows = 0;
        this.columns = 0;
    }
}
