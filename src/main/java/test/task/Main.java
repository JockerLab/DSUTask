package test.task;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        if (args.length < 1 || args[0] == null) {
            System.err.println("Invalid file name in arguments");
            return;
        }

        try {
            String fileName = args[0];
            TableReader reader = new TableReader();
            Table table = reader.read(fileName);

            if (table == null) {
                return;
            }

            GroupSolver solver = new GroupSolver();
            List<List<Integer>> groups = solver.solve(table);

            TableWriter writer = new TableWriter();
            writer.write(groups, table, "output.txt");
        } catch (OutOfMemoryError e) {
            System.err.println("Out of memory");
        }

        long endTime = System.nanoTime();
        System.out.println("Execution time: " + (endTime - startTime) / 1_000_000 + "ms");
    }
}
