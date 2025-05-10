package org.example.models.enums.Plants;

import org.example.models.enums.Seasons.Season;

public interface PlantType {
    public int[] getStages();
    public int getHarvestCycle();
    public FruitType getFruit();
    public int getTotalHarvestTime();
    public PlantSourceType getSource();
    public boolean getOneTime();
    public Season[] getSeasons();

    default String getName() {
        return this.toString().replaceAll("([A-Z])", " $1").trim();
    }
}
