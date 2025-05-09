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
        int[] I = {0, 1, 1, 1, 0, -1, -1, -1},
                J = {1, 1, 0, -1, -1, -1, 0, 1};
        //directions : {R, DR, D, DL, L, UL, U, UR}
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell(CellType.View, new Position(i, j), this);
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int k = 0; k < 8; k++) {
                    int newI = i + I[k], newJ = j + J[k];
                    if (newI >= 0 && newI < height && newJ >= 0 && newJ < width) {
                        cells[i][j].addAdjacentCell(cells[newI][newJ]);
                    } else {
                        cells[i][j].addAdjacentCell(null);
                    }
                }
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


    private static final String mapReadingManual = new String(
            "Map Manual :\n" +
                    "Y -> You !!\n" +
                    "C -> Crop\n" +
            "T -> Tree\n" +
            "R -> Mineral\n" +
            "H -> Hut\n" +
            "G -> GreenHouse\n" +
            "W -> Water\n" +
            "D -> Door\n" +
            "N -> NPC House\n" +
            "S -> Store\n" +
            "~ -> City Grounds\n"
    );

    public String getMapReadingManual() {
        return mapReadingManual;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
