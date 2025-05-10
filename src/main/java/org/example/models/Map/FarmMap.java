package org.example.models.Map;

import org.example.models.AnimalProperty.AnimalEnclosure;
import org.example.models.AnimalProperty.Barn;
import org.example.models.AnimalProperty.Coop;
import org.example.models.Cell;
import org.example.models.Position;
import org.example.models.enums.CellType;
import org.example.models.enums.items.BuildingType;

import java.sql.Time;
import java.util.*;
import java.util.random.RandomGenerator;

public class FarmMap extends Map {

    private Hut hut;
    private GreenHouse greenHouse;
    private ArrayList<Barn> barns;
    private ArrayList<Coop> coops;

    public FarmMap(int height, int width) {
        super(height, width);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j].setType(CellType.Free);
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

    public boolean freeRectangle(int x, int y, int height, int width) {
        for (int i = x; i < x + height; i++) {
            for (int j = y; j < y + width; j++) {
                if (cells[i][j].getType() != CellType.Free) {
                    return false;
                }
            }
        }
        return true;
    }

    private void placeAnimalEnclosure(AnimalEnclosure animalEnclosure, int r, int c) {
        int height = animalEnclosure.getType().getHeight(),
                width = animalEnclosure.getType().getWidth();
        for (int i = r; i < r + height; i++) {
            cells[i][c].setType(CellType.Building);
            cells[i][c].setBuilding(animalEnclosure);
            cells[i][c + width - 1].setType(CellType.Building);
            cells[i][c + width - 1].setBuilding(animalEnclosure);
        }
        for (int j = c; j < c + width; j++) {
            cells[r][j].setType(CellType.Building);
            cells[r][j].setBuilding(animalEnclosure);
            cells[r + height - 1][j].setType(CellType.Building);
            cells[r + height - 1][j].setBuilding(animalEnclosure);
        }
        for (int i = r; i < r + height; i++) {
            for (int j = c; j < c + width; j++) {
                cells[i][j].setBuilding(animalEnclosure);
            }
        }
    }

    public void placeCoop(int i, int j, Coop coop) {
        this.coops.add(coop);
        placeAnimalEnclosure(coop, i, j);
    }

    public void placeBarn(int i, int j, Barn barn) {
        this.barns.add(barn);
        placeAnimalEnclosure(barn, i, j);
    }

    public void generateForaging() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (cells[i][j].getType() == CellType.Free) {
                    int randomInt =  (new Random(System.currentTimeMillis())).nextInt(1000);
                    if (randomInt / 10 == 15) {
                        if (randomInt == 151) cells[i][j].placeForagingMineral();
                        else cells[i][j].placeForagingCrop();
                    }
                }
            }
        }
    }

}