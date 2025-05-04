package org.example.models.Map;

import org.example.models.AnimalProperty.Barn;
import org.example.models.AnimalProperty.Coop;
import org.example.models.Cell;
import org.example.models.Position;
import org.example.models.enums.CellType;
import org.example.models.enums.items.BuildingType;

import java.util.*;

public class FarmMap extends Map {

    private Hut hut;
    private GreenHouse greenHouse;
    private ArrayList<Barn> barns;
    private ArrayList<Coop> coops;

    public FarmMap(int height, int width) {
        super(height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell(new Position(i, j));
            }
        }
    }


    public Hut getHut() {
        return hut;
    }

    public void setHut(Hut hut) {
        this.hut = hut;
    }

    public GreenHouse getGreenHouse() {
        return greenHouse;
    }

    public void setGreenHouse(GreenHouse greenHouse) {
        this.greenHouse = greenHouse;
    }

    public ArrayList<Barn> getBarns() {
        return barns;
    }

    public void addBarn(Barn barn) {
        this.barns.add(barn);
    }

    public ArrayList<Coop> getCoops() {
        return coops;
    }

    public boolean freeRectangle(int x, int y, int width, int height) {
        for (int i = x; i < x + height; i++) {
            for (int j = y; j < y + width; j++) {
                if (cells[i][j].getType() != CellType.Free) {
                    return false;
                }
            }
        }
        return true;
    }

    public void placeCoop(int x, int y, Coop coop) {
        this.coops.add(coop);
        for (int i = x; i < x + BuildingType.Coop.getHeight(); i++) {
            for (int j = y; j < y + BuildingType.Coop.getHeight(); j++) {
                cells[i][j].setType(CellType.Building);
                cells[i][j].setBuilding(coop);
            }
        }
    }

    public void placeBarn(int x, int y, Barn barn) {
        this.barns.add(barn);
        for (int i = x; i < x + BuildingType.Barn.getHeight(); i++) {
            for (int j = y; j < y + BuildingType.Barn.getHeight(); j++) {
                cells[i][j].setType(CellType.Building);
                cells[i][j].setBuilding(barn);
            }
        }
    }

}