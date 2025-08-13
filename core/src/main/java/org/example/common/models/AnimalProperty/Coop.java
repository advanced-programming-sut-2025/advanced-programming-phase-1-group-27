package org.example.common.models.AnimalProperty;

import org.example.common.models.Cell;
import org.example.common.models.items.BuildingType;

public class Coop extends AnimalEnclosure {
    private Cell topLeftCell;

    public Coop(BuildingType coopType, Cell topLeftCell) {
        super(coopType, topLeftCell);
    }

    public BuildingType getType() {
        return type;
    }
}
