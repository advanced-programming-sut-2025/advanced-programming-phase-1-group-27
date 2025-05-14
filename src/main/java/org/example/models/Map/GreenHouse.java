package org.example.models.Map;

import org.example.models.Cell;
import org.example.models.Building;
import org.example.models.enums.CellType;

public class GreenHouse extends Building {
    private Cell door;
    private boolean isRepaired = false;

    public GreenHouse(Cell door, Cell topLeftCell) {
        this.door = door;
        super(topLeftCell, 7, 8);
    }

    public boolean isRepaired() {
        return isRepaired;
    }

    public void repair() {
        this.isRepaired = true;
        int x = topLeftCell.getPosition().getX(), y = topLeftCell.getPosition().getY();

        for (int i = x + 1; i < x + height - 1; i++) {
            for (int j = y + 1; j < y + width - 1; j++) {
                topLeftCell.getMap().getCell(i, j).setType(CellType.Free);
            }
        }
        topLeftCell.getMap().getCell(x + height - 1, y + width - 4).setType(CellType.View);
        topLeftCell.getMap().getCell(x + 1, y + width - 4).setType(CellType.Water);
    }
}
