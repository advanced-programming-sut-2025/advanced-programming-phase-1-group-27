package org.example.models.AnimalProperty;

import org.example.models.Building;
import org.example.models.Cell;
import org.example.models.enums.items.BuildingType;

public class Barn extends AnimalEnclosure {
    private final BuildingType barnType;
    private Cell topLeftCell;

    public Barn(BuildingType barnType) {
        this.barnType = barnType;
    }

    public void setTopLeftCell(Cell topLeftCell) {
        this.topLeftCell = topLeftCell;
    }
}
