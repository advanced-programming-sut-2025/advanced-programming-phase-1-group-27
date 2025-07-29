package org.example.server.models.Map;

import com.badlogic.gdx.graphics.Texture;
import org.example.client.Main;
import org.example.server.models.App;
import org.example.server.models.Cell;
import org.example.server.models.Position;
import org.example.server.models.enums.CellType;
import org.example.server.models.enums.Plants.Crop;
import org.example.server.models.enums.Plants.CropType;
import org.example.server.models.enums.Plants.Tree;
import org.example.server.models.enums.Plants.TreeType;
import org.example.server.models.enums.items.MineralType;

import java.util.*;

public class Map {

    private static final String mapReadingManual = new String(
            """
                    Map Manual :
                    [1, 2, 3, 4] -> Players (By Id)\s
                    \u001B[48;5;179m  \u001B[0m -> Empty farm grounds
                    \u001B[48;2;19;138;57m  \u001B[0m -> Crops
                    \u001B[48;2;1;59;19m  \u001B[0m -> Trees
                    \u001B[48;5;240m  \u001B[0m -> Minerals
                    \u001B[48;5;236m  \u001B[0m -> Quarry
                    \u001B[48;2;59;33;1m  \u001B[0m -> Huts, NPCHouses and Stores
                    \u001B[48;2;66;54;32m  \u001B[0m -> GreenHouse, Barns and Coops
                    \u001B[48;2;64;120;168m  \u001B[0m -> Water
                    \u001B[48;2;138;106;67m  \u001B[0m -> Doors
                    \u001B[38;2;59;33;1m\u001B[48;5;179m__\u001B[0m -> Plowed cells
                    \u001B[48;2;89;89;89m  \u001B[0m -> NPC valley grounds
                    \033[43m  \u001B[0m -> Passages between maps
                    \u001B[34m\u001B[48;2;89;89;89mA \u001B[0m -> Npcs (by the first of their names)
                    \u001B[48;2;201;129;129m  \u001B[0m -> Animals
                    """
    );
    protected int height, width;
    protected Cell[][] cells;
    protected Cell[][] par;
    private HashSet<Cell> markedCells = new HashSet<>();
    private HashMap<Cell, Integer> distance = new HashMap<>();

    Map(int height, int width) {
        this.height = height;
        this.width = width;
        this.cells = new Cell[height][width];
        this.par = new Cell[height][width];
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

    public Cell getCell(int i, int j) {
        if (i >= 0 && i < height && j >= 0 && j < width)
            return cells[i][j];
        else
            return null;
    }

    public Cell[][] getCells() {
        return cells;
    }

    private void dfs(Cell cell) {
        markedCells.add(cell);
        for (Cell adj : cell.getAdjacentCells()) {
            if (adj != null && adj.isPassable() && !markedCells.contains(adj)) {
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
        par = new Cell[height][width];
        Queue<Cell> queue = new LinkedList<>();
        markedCells.add(cell);
        queue.add(cell);
        distance.put(cell, 0);
        while (!queue.isEmpty()) {
            Cell current = queue.remove();
            for (Cell adj : current.getAdjacentCells()) {
                if (adj != null && adj.isPassable() && !markedCells.contains(adj)) {
                    markedCells.add(adj);
                    distance.put(adj, distance.get(current) + 1);
                    queue.add(adj);
                    par[adj.getPosition().getX()][adj.getPosition().getY()] = current;
                }
            }
        }
    }

    public int getPathEnergy(Cell A, Cell B) {
        int energy = 0;
        ArrayList<Cell> path = getPath(A, B);
        for (int i = 1; i < path.size(); i++) {
            Cell previous = path.get(i - 1), next = path.get(i);

            if (previous.getPosition().getX() == next.getPosition().getX() ||
                    previous.getPosition().getY() == next.getPosition().getY()) {
                energy++;
            } else {
                energy += 10;
            }
        }
        return energy / 20;
    }

    public Cell getPlaceInPath(Cell A, Cell B, int energy) {
        int neededEnergy = 0;
        ArrayList<Cell> path = getPath(A, B);
        for (int i = 1; i < path.size(); i++) {
            Cell previous = path.get(i - 1), next = path.get(i);
            if (energy <= neededEnergy / 20)
                return previous;

            if (previous.getPosition().getX() == next.getPosition().getX() ||
                    previous.getPosition().getY() == next.getPosition().getY()) {
                neededEnergy++;
            } else {
                neededEnergy += 10;
            }
        }
        return B;
    }

    public ArrayList<Cell> getPath(Cell A, Cell B) {
        markedCells.clear();
        distance.clear();
        distance.put(B, 0);
        bfs(B);
        Cell temp = A;
        ArrayList<Cell> path = new ArrayList<>();
        while (temp != B) {
            path.add(temp);
            temp = par[temp.getPosition().getX()][temp.getPosition().getY()];
        }
        path.add(B);
        return path;
    }

    public String getMapReadingManual() {
        return mapReadingManual;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void print(float tileSize) {
        for (int i = cells.length - 1; i >= 0; i--) {
            for (int j = 0; j < cells[i].length; j++) {
                float y = (cells.length - 1 - i) * tileSize;
                float x = j * tileSize;
                Texture texture = cells[i][j].getTexture();

                if (texture == null) continue;
                Main.getBatch().draw(texture, x, y, tileSize, tileSize);

                if (cells[i][j].getObject() instanceof Crop crop) {
                    texture = ((CropType) crop.getType()).getTexture();
                    if (texture != null)
                        Main.getBatch().draw(texture, x + 4, y + 4, 32, 32);
                }
                if (cells[i][j].getObject() instanceof MineralType mineral) {
                    texture = mineral.getTexture();
                    if (texture != null)
                        Main.getBatch().draw(texture, x + 4, y + 4, 32, 32);
                }
                if (cells[i][j].getObject() instanceof Tree tree) {
                    ArrayList<Texture>  textures = ((TreeType) tree.getType()).getTextures();
                    if (textures != null)
                        Main.getBatch().draw(textures.get(2), x, y, 36, 60);
                }
            }
        }
    }


}

class Node implements Comparable<Node> {
    private final Vertex vertex;
    private int distance;

    public Node(Vertex vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    public Vertex getVertex() {
        return vertex;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Node o) {
        if (distance != o.distance)
            return distance - o.distance;
        else
            return vertex.compareTo(o.vertex);
    }
}

class Vertex implements Comparable<Vertex> {
    private final int direction;
    private final Cell cell;

    public Vertex(Cell cell, int direction) {
        this.direction = direction;
        this.cell = cell;
    }


    public Cell getCell() {
        return cell;
    }

    public int getDirection() {
        return direction;
    }

    @Override
    public int compareTo(Vertex o) {
        int i = cell.getPosition().getX(), j = cell.getPosition().getY();
        int i2 = o.getCell().getPosition().getX(), j2 = o.getCell().getPosition().getY();
        if (i != i2)
            return i - i2;
        else if (j != j2)
            return j - j2;
        else
            return direction - o.direction;
    }
}
