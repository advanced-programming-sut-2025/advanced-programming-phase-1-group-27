package org.example.models.AnimalProperty;

import org.example.models.Building;
import org.example.models.Cell;
import org.example.models.enums.items.BuildingType;

import java.util.ArrayList;

public abstract class AnimalEnclosure extends Building {
    protected final BuildingType type;
    protected ArrayList<Animal> animals = new ArrayList<>();

    protected AnimalEnclosure(BuildingType type, Cell topLeftCell) {
        super(topLeftCell, type.getHeight(), type.getWidth());
        this.type = type;
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

}
