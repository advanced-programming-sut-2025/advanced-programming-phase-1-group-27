package org.example.common.models.AnimalProperty;

import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.Building;
import org.example.common.models.Cell;
import org.example.common.models.ItemManager;
import org.example.common.models.items.BuildingType;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AnimalEnclosure extends Building {
    protected final BuildingType type;
    protected ArrayList<Animal> animals = new ArrayList<>();

    protected AnimalEnclosure(BuildingType type, Cell topLeftCell) {
        super(topLeftCell, type.getHeight(), type.getWidth());
        this.type = type;
    }

    public static AnimalEnclosure handleInfo(Cell topLeftCell, LinkedTreeMap<String, Object> info) {
        AnimalEnclosure animalEnclosure = ItemManager.getEnclosure(topLeftCell, (String) info.get("type"));
        ArrayList<LinkedTreeMap<String, Object>> animalsInfo = (ArrayList<LinkedTreeMap<String, Object>>) info.get("animals");
        for (LinkedTreeMap<String, Object> animalInfo : animalsInfo) {
            animalEnclosure.animals.add(new Animal(animalEnclosure, animalInfo));
        }
        return animalEnclosure;
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("position", topLeftCell.getPosition().getInfo());
        info.put("type", type.name());
        info.put("animals", getAnimalsInfo());
        return info;
    }

    private ArrayList getAnimalsInfo() {
        ArrayList result = new ArrayList();
        for (Animal animal : animals) {
            result.add(animal.getInfo());
        }
        return result;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal);
    }

    public abstract BuildingType getType();

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

}
