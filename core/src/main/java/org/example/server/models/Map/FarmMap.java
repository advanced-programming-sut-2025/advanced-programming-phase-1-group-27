package org.example.server.models.Map;

import org.example.server.models.AnimalProperty.Animal;
import org.example.server.models.AnimalProperty.AnimalEnclosure;
import org.example.server.models.AnimalProperty.Barn;
import org.example.server.models.AnimalProperty.Coop;
import org.example.server.models.Cell;
import org.example.server.models.ShippingBin;
import org.example.server.models.enums.CellType;
import org.example.server.models.enums.Plants.Plant;

import java.util.ArrayList;
import java.util.Random;

public class FarmMap extends Map {

    private Hut hut;
    private GreenHouse greenHouse;
    private ArrayList<Barn> barns = new ArrayList<>();
    private ArrayList<Coop> coops = new ArrayList<>();
    private ArrayList<Animal> animals = new ArrayList<>();
    private ArrayList<ShippingBin> shippingBins = new ArrayList<>();
    private Cell passage = null;

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
                if (cells[i][j].getType() != CellType.Free || cells[i][j].getObject() != null) {
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
            for (int j = c; j < c + width; j++) {
                cells[i][j].setType(CellType.Building);
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

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public Cell getPassage() {
        return passage;
    }

    public void setPassage(Cell passage) {
        this.passage = passage;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void addShippingBin(ShippingBin shippingBin) {
        this.shippingBins.add(shippingBin);
    }

    public ArrayList<ShippingBin> getShippingBins() {
        return shippingBins;
    }

    public void generateForaging() {
        int foragingCount = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (cells[i][j].getObject() != null && cells[i][j].getObject() instanceof Plant plant &&
                        plant.isForaging())
                    foragingCount++;
            }
        }
        if (foragingCount > 110)
            return;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int randomInt = new Random().nextInt(200);
                Cell cell = cells[i][j];

                if (cell.getObject() != null)
                    continue;

                if (cell.getType() == CellType.Quarry) {
                    if (randomInt < 2)
                        cell.placeForagingMineral();
                } else if (cell.getBuilding() == null && cell.getType() == CellType.Plowed) {
                    if (randomInt < 2)
                        cell.placeForagingSeed();
                } else if (cell.getBuilding() == null && cell.getType() == CellType.Free) {
                    if (randomInt < 1)
                        cell.placeForagingCrop();
                    if (randomInt < 2)
                        cell.placeForagingTree();
                }
            }
        }
    }

}