package org.example.server.models.AnimalProperty;

import org.example.server.models.Cell;
import org.example.server.models.enums.items.BuildingType;

public class Coop extends AnimalEnclosure {
    private Cell topLeftCell;

    public Coop(BuildingType coopType, Cell topLeftCell) {
        super(coopType, topLeftCell);
    }

    public BuildingType getType() {
        return type;
    }
}
