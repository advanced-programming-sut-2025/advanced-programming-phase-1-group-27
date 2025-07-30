package org.example.server.models.Map;

import com.google.gson.internal.LinkedTreeMap;
import org.example.client.Main;
import org.example.client.view.OutsideView;
import org.example.server.models.AnimalProperty.Animal;
import org.example.server.models.AnimalProperty.AnimalEnclosure;
import org.example.server.models.AnimalProperty.Barn;
import org.example.server.models.AnimalProperty.Coop;
import org.example.server.models.Cell;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Position;
import org.example.server.models.ShippingBin;
import org.example.server.models.enums.CellType;
import org.example.server.models.enums.Plants.*;

import java.util.ArrayList;
import java.util.HashMap;
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

    public ArrayList<HashMap<String, Object>> getForagingInfo() {
        ArrayList<HashMap<String, Object>> info = new ArrayList<>();
        for (int i = 1; i < height - 1; i++)
            for (int j = 1; j < width - 1; j++)
                if (cells[i][j].getObject() instanceof Plant plant) {
                    int finalI = i;
                    int finalJ = j;
                    info.add(new HashMap<String, Object>() {{
                        put("type", plant.getType().toString());
                        put("x", finalI);
                        put("y", finalJ);
                    }});
                }
        return info;
    }

    public void addForaging(ArrayList<LinkedTreeMap<String, Object>> info) {
        for (LinkedTreeMap<String, Object> foraging : info) {
            int x = ((Number) foraging.get("x")).intValue();
            int y = ((Number) foraging.get("y")).intValue();
            Cell cell = cells[x][y];
            String typeName = (String) foraging.get("type");
            CropType cropType = CropType.getItem(typeName);
            if (cropType != null) {
                Crop crop = new Crop(cropType);
                crop.setTillNextHarvest(0);
                crop.setForaging(true);
                cell.plant(crop);
            }
            TreeType treeType = TreeType.getItem(typeName);
            if (treeType != null) {
                Tree tree = new Tree(treeType);
                tree.setTillNextHarvest(0);
                tree.setForaging(true);
                cell.plant(tree);
            }
        }
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

    public void print(float tileSize) {
        super.print(tileSize);
        Position position = greenHouse.getTopLeftCell().getPosition();
        int x = OutsideView.getGraphicalPosition(position).getX() - 20,
                y = OutsideView.getGraphicalPosition(position).getY() - 30;
        Main.getBatch().draw(GameAssetManager.getGameAssetManager().getGreenHouseTexture(),
                x, y - 240, 320, 280);

        position = hut.getTopLeftCell().getPosition();
        x = OutsideView.getGraphicalPosition(position).getX() - 20;
        y = OutsideView.getGraphicalPosition(position).getY() - 30;
        Main.getBatch().draw(GameAssetManager.getGameAssetManager().getHutTexture(),
                x, y - 125, 160, 160);
    }

    public ArrayList<Plant> getAllPlants() {
        ArrayList<Plant> plants = new ArrayList<>();
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                Cell cell = cells[i][j];
                if (cell.getObject() != null && !(cell.getBuilding() instanceof GreenHouse)) {
                    if (cell.getObject() instanceof Plant plant) {
                        plants.add(plant);
                    }
                }
            }
        return plants;
    }

}