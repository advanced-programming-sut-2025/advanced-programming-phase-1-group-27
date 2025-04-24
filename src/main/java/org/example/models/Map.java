package org.example.models;

import org.example.models.enums.CellType;

public class Map {
    private String[] mapView = new String[80];
    private int height, width;
    private CellType[][] cells = new CellType[80][70];

    Map (int height, int width) {
        this.height = height;
        this.width = width;
        this.generate();
    }

    public void generate() {

    }

    public void plant(int x, int y, Crop crop) {

    }

    public boolean areConnected(Place A, Place B) {
    
    }

    public void changeType(int x, int y, CellType cellType) {
        
    }

    public String veiwMapString() {
        
    }
}