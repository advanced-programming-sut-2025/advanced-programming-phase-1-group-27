package org.example.models.enums.Plants;

public interface PlantType {
    public int[] getStages();
    public int getHarvestCycle();
    public FruitType getFruit();
    public int getTotalHarvestTime();
}
