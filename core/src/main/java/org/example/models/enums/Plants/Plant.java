package org.example.models.enums.Plants;

import org.example.models.Cell;
import org.example.models.Stacks;

public abstract class Plant {
    protected boolean isGiant = false;
    protected PlantType type;
    protected int cnt = 0, currentStage = 0, tillNextHarvest;
    protected Cell cell;
    protected boolean wateredYesterday = true, wateredToday = false, isForaging = false, alwaysWatered = false;
    //...

    protected Plant(PlantType type) {
        this.type = type;
        tillNextHarvest = type.getTotalHarvestTime();
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void grow() {
        cnt++;
        tillNextHarvest = Integer.max(tillNextHarvest - 1, 0);
        if (currentStage < type.getStages().length - 1 && cnt >= type.getStages()[currentStage]) {
            currentStage++;
            cnt = 0;
        }
        wateredYesterday = wateredToday;
        wateredToday = false;
    }

    public int getTillNextHarvest() {
        return tillNextHarvest;
    }

    public void setTillNextHarvest(int tillNextHarvest) {
        this.tillNextHarvest = tillNextHarvest;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public Stacks harvest() {
        if (tillNextHarvest > 0) {
            return null;
        }
        tillNextHarvest = type.getHarvestCycle();
        return new Stacks(new Fruit(type.getFruit()), (isGiant ? 10 : 1));
    }

    public PlantType getType() {
        return type;
    }

    public void water() {
        wateredToday = true;
    }

    public boolean getWateredYesterday() {
        return wateredYesterday;
    }

    public void setWateredYesterday(boolean wateredYesterday) {
        this.wateredYesterday = wateredYesterday;
    }

    public boolean getWateredToday() {
        return wateredToday | alwaysWatered;
    }

    public void setWateredToday(boolean wateredToday) {
        this.wateredToday = wateredToday;
    }

    public void setAlwaysWatered(boolean alwaysWatered) {
        this.alwaysWatered = alwaysWatered;
    }

    public boolean isGiant() {
        return isGiant;
    }

    public void setGiant(boolean giant) {
        isGiant = giant;
    }

    public boolean isForaging() {
        return isForaging;
    }

    public void setForaging(boolean foraging) {
        isForaging = foraging;
    }
}
