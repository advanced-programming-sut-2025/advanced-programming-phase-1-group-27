package org.example.models.AnimalProperty;

import org.example.models.Cell;
import org.example.models.Map.Map;
import org.example.models.enums.items.BuildingType;

public class Coop extends AnimalEnclosure {
    private Cell topLeftCell;

    public Coop(BuildingType coopType, Cell topLeftCell) {
        super(coopType, topLeftCell);
    }

    public BuildingType getType() {
        return type;
    }
}
