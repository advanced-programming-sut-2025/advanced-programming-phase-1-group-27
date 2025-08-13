package org.example.common.models;

public enum CellType {
    Building,
    Free,
    Plowed,
    Water,
    Door,
    View,
    MapLink,
    Quarry;

    public static CellType getCellType(String type) {
        for (CellType cellType : values()) {
            if (cellType.toString().equalsIgnoreCase(type))
                return cellType;
        }
        return null;
    }
}
