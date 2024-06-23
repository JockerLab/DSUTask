package test.task;

import java.util.*;

public class GroupSolver {
    private List<Integer> rank;
    private List<Integer> parent;

    private int getSet(int x) {
        if (parent.get(x) != x) {
            parent.set(x, getSet(parent.get(x)));
        }
        return parent.get(x);
    }

    private void unionSets(int x, int y) {
        x = getSet(x);
        y = getSet(y);
        if (x == y) {
            return;
        }
        if (rank.get(x) == rank.get(y)) {
            rank.set(x, rank.get(x) + 1);
        }
        if (rank.get(x) < rank.get(y)) {
            parent.set(x, y);
        } else {
            parent.set(y, x);
        }
    }

    private void initSets(Table table) {
        rank = new ArrayList<>();
        parent = new ArrayList<>();

        for (int row = 0; row < table.getRows(); row++) {
            parent.add(row);
            rank.add(0);
        }
    }

    private void solveForColumn(int column, Table table) {
        Map<String, Integer> firstPosition = new HashMap<>();

        for (int row = 0; row < table.getRows(); row++) {
            String value = table.get(row, column);

            if (value == null) {
                continue;
            }
            if (!firstPosition.containsKey(value)) {
                firstPosition.put(value, row);
            } else {
                int position = firstPosition.get(value);
                unionSets(position, row);
            }
        }
    }

    /**
     * For each column union into one group equal values by DSU algorithm
     * @param table
     * @return list of groups with ids of rows
     */
    public List<List<Integer>> solve(Table table) {
        initSets(table);

        for (int column = 0; column < table.getColumns(); column++) {
            solveForColumn(column, table);
        }

        List<List<Integer>> groupsCount = new ArrayList<>();
        Map<Integer, Integer> parentToGroupId = new HashMap<>();

        for (int i = 0; i < parent.size(); i++) {
            int groupId;

            if (parentToGroupId.containsKey(parent.get(i))) {
                groupId = parentToGroupId.get(parent.get(i));
            } else {
                groupId = groupsCount.size();
                parentToGroupId.put(parent.get(i), groupId);
                groupsCount.add(new ArrayList<>(List.of(0, groupId)));
            }

            groupsCount.get(groupId).set(0, groupsCount.get(groupId).get(0) + 1);
        }

        groupsCount.sort((a, b) -> -Integer.compare(a.get(0), b.get(0)));

        List<List<Integer>> groups = new ArrayList<>();
        Map<Integer, Integer> groupToSortedId = new HashMap<>();

        for (int i = 0; i < groupsCount.size(); i++) {
            groups.add(new ArrayList<>());
            groupToSortedId.put(groupsCount.get(i).get(1), i);
        }

        for (int row = 0; row < table.getRows(); row++) {
            int groupId = parentToGroupId.get(parent.get(row));
            int sortedId = groupToSortedId.get(groupId);
            groups.get(sortedId).add(row);
        }

        return groups;
    }
}
