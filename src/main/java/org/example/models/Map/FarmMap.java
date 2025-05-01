package org.example.models.Map;

import org.example.models.Cell;

import java.util.*;

public class FarmMap {
    private int height, width;
    private Cell[][] cells;
    private Hut hut;
    private GreenHouse greenHouse;


    FarmMap(int height, int width) {
        this.height = height;
        this.width = width;
        this.cells = new Cell[height][width];
        buildCellsGraph();
    }

    private void buildCellsGraph() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell();
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

    private HashSet<Cell> markedCells;

    private void dfs(Cell cell) {
        markedCells.add(cell);
        for (Cell adj : cell.getAdjacentCells()) {
            if (adj.isPassable() && !markedCells.contains(adj)) {
                dfs(adj);
            }
        }
    }

    public boolean areConnected(Cell A, Cell B) {
        markedCells = new HashSet<>();
        dfs(A);
        return markedCells.contains(B);
    }

    private HashMap<Cell, Integer> distance;

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
        markedCells = new HashSet<>();
        distance = new HashMap<>();
        distance.put(A, 0);
        bfs(A);
        return (distance.containsKey(B)? distance.get(B): 0);
    }

    public String[] veiwMapString() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(cells[i][j].toString());
            }
            System.out.println("\n");
        }
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public Cell[][] getCells() {
        return cells;
    }

    public Hut getHut() {
        return hut;
    }

    public void setHut(Hut hut) {
        this.hut = hut;
    }

    public GreenHouse getGreenHouse() {
        return greenHouse;
    }

    public void setGreenHouse(GreenHouse greenHouse) {
        this.greenHouse = greenHouse;
    }
}