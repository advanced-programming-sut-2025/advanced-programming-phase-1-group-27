package org.example.models.enums.Plants;

public class Crop {
    private CropType type;
    private int cnt = 0, currentStage = 0, tillNextHarvest;
    //...

    public Crop(CropType type) {
        this.type = type;
        tillNextHarvest = type.getTotalHarvestTime();
    }

    public void grow() {
        cnt++;
        tillNextHarvest = Integer.max(tillNextHarvest - 1, 0);
        if (cnt >= type.getStages()[currentStage] && currentStage < type.getStages().length - 1) {
            currentStage++;
            cnt = 0;
        }
    }

    public Fruit harvest() {
        if (tillNextHarvest > 0) {
            return null;
        }
        tillNextHarvest = type.getRegrowthTime();
        return new Fruit(type.getFruit());
    }

    public CropType getType() {
        return type;
    }
}
