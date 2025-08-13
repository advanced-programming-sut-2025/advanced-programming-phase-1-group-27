package org.example.common.models.AnimalProperty;

import org.example.common.models.Cell;
import org.example.common.models.items.BuildingType;

public class Barn extends AnimalEnclosure {
    private Cell topLeftCell;

    public Barn(BuildingType barnType, Cell topLeftCell) {
        super(barnType, topLeftCell);
    }

    public void setTopLeftCell(Cell topLeftCell) {
        this.topLeftCell = topLeftCell;
    }

    public BuildingType getType() {
        return type;
    }
}
