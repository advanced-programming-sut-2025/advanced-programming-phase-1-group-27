package org.example.models.Map;

import org.example.models.Cell;
import org.example.models.Position;
import org.example.models.enums.CellType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Map {

    protected int height, width;
    protected Cell[][] cells;

    Map(int height, int width) {
        this.height = height;
        this.width = width;
        this.cells = new Cell[height][width];
        buildCellsGraph();
    }

    private void buildCellsGraph() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell(CellType.View, new Position(i, j));
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i > 0) cells[i][j].addAdjacentCell(cells[i - 1][j]);
                if (j > 0) cells[i][j].addAdjacentCell(cells[i][j - 1]);
                if (i < height - 1) cells[i][j].addAdjacentCell(cells[i + 1][j]);
                if (j < width - 1) cells[i][j].addAdjacentCell(cells[i][j + 1]);
            }
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public Cell[][] getCells() {
        return cells;
    }

    private HashSet<Cell> markedCells = new HashSet<>();
    private HashMap<Cell, Integer> distance = new HashMap<>();

    private void dfs(Cell cell) {
        markedCells.add(cell);
        for (Cell adj : cell.getAdjacentCells()) {
            if (adj.isPassable() && !markedCells.contains(adj)) {
                dfs(adj);
            }
        }
    }

    public boolean areConnected(Cell A, Cell B) {
        markedCells.clear();
        dfs(A);
        return markedCells.contains(B);
    }

    private void bfs(Cell cell) {
        Queue<Cell> queue = new LinkedList<>();
        markedCells.add(cell);
        queue.add(cell);
        while (!queue.isEmpty()) {
            Cell current = queue.remove();
            for (Cell adj : current.getAdjacentCells()) {
                if (adj.isPassable() && !markedCells.contains(adj)) {
                    markedCells.add(adj);
                    distance.put(adj, distance.get(current) + 1);
                    queue.add(adj);
                }
            }
        }
    }

    public int getDistance(Cell A, Cell B) {
        markedCells.clear();
        distance.clear();
        distance.put(A, 0);
        bfs(A);
        return (distance.containsKey(B)? distance.get(B): -1);
    }


    public String[] veiwMapString() {
        String[] mapView = new String[height];
        for (int i = 0; i < height; i++) {
            mapView[i] = "";
            for (int j = 0; j < width; j++) {
                mapView[i] += cells[i][j].toString();
            }
        }
        return mapView;
    }

    private static final String[] mapReadingManual = new String[] {
            "C -> Crop",
            "T -> Tree",
            "R -> Mineral",
            "H -> Hut",
            "G -> GreenHouse",
            "W -> Water",
            "D -> Door",
            "N -> NPC House",
            "S -> Store",
            "~ -> City Grounds"
    };

    public String[] getMapReadingManual() {
        return mapReadingManual;
    }
}
