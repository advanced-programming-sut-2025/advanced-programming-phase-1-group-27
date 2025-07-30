package org.example.server.models.Map;

import org.example.server.models.Cell;
import org.example.server.models.enums.CellType;

import java.util.Random;

public class FarmMapBuilder {
    private FarmMap finalProduct;
    private int n, m;

    public void setDimensions(int height, int width) {
        finalProduct = new FarmMap(height, width);
        n = height;
        m = width;
    }

    public void createHut() {
        Hut hut = new Hut(finalProduct.getCell(7, m - 4), finalProduct.getCell(4, m - 6));
        for (int i = 4; i < 8; i++) {
            for (int j = m - 6; j < m - 2; j++) {
                finalProduct.getCell(i, j).setType(CellType.Building);
                finalProduct.getCell(i, j).setBuilding(hut);
            }
        }
        for (int j = m - 6; j < m - 2; j++) {
            finalProduct.getCell(8, j).setType(CellType.View);
        }
        finalProduct.getCell(7, m - 4).setType(CellType.Door);
        finalProduct.setHut(hut);
    }

    public void createBigLake(int x, int y) {
        for (int i = x; i < x + 8; i++)
            for (int j = y; j < y + 5; j++)
                finalProduct.getCell(i, j).setType(CellType.Water);
        for (int i = x; i < x + 8; i++) {
            finalProduct.getCell(i, y - 1).setType(CellType.Water);
            finalProduct.getCell(i, y + 5).setType(CellType.Water);
        }
        for (int j = y; j < y + 5; j++) {
            finalProduct.getCell(x - 1, j).setType(CellType.Water);
            finalProduct.getCell(x + 8, j).setType(CellType.Water);
        }
    }

    public void createSmallLake(int x, int y) {
        for (int i = x; i < x + 3; i++)
            for (int j = y; j < y + 2; j++)
                finalProduct.getCell(i, j).setType(CellType.Water);
        for (int i = x; i < x + 3; i++) {
            finalProduct.getCell(i, y - 1).setType(CellType.Water);
            finalProduct.getCell(i, y + 2).setType(CellType.Water);
        }
        for (int j = y; j < y + 2; j++) {
            finalProduct.getCell(x - 1, j).setType(CellType.Water);
            finalProduct.getCell(x + 3, j).setType(CellType.Water);
        }
    }

    public void createStoneQuarry() {
        for (int i = 3; i < 8; i++) {
            for (int j = 5; j < 10; j++) {
                finalProduct.getCell(i, j).setType(CellType.Quarry);

                if (new Random().nextInt(4) == 0) {
                    finalProduct.getCell(i, j).placeForagingMineral();
                }
            }
        }
    }

    public void createGreenHouse() {
        GreenHouse greenHouse = new GreenHouse(finalProduct.getCell(10, 24), finalProduct.getCell(4, 20));
        for (int i = 4; i < 11; i++) {
            for (int j = 20; j < 28; j++) {
                if (i == 4 || i == 10 || j == 20 || j == 27)
                    finalProduct.getCell(i, j).setType(CellType.Building);
                finalProduct.getCell(i, j).setBuilding(greenHouse);
            }
        }

        finalProduct.setGreenHouse(greenHouse);
    }

    public void generateForagingItems() {
        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < m - 1; j++) {
                Cell cell = finalProduct.getCell(i, j);
                int randomInt = (new Random()).nextInt(500);
                if (cell.getType() == CellType.Free && cell.getBuilding() == null) {
                    if (randomInt < 2) {
                        cell.placeForagingCrop();
                    } else if (randomInt < 4) {
                        cell.placeForagingTree();
                    }
                }
            }
        }
    }

    public void setPassageToNpcValley(int x, int y, int x2, int y2, NPCMap npcMap) {
        finalProduct.setPassage(finalProduct.getCell(x, y));
        finalProduct.getCell(x, y).setType(CellType.MapLink);
        finalProduct.getCell(x, y).setObject(npcMap.getCell(x2, y2));
    }

    public FarmMap getFinalProduct() {
        return finalProduct;
    }
}
