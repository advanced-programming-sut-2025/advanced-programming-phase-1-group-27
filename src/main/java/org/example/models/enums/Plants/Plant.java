package org.example.models.enums.Plants;

import org.example.models.Cell;
import org.example.models.enums.CellType;

public abstract class Plant {
    protected boolean isGiant = false;
    protected PlantType type;
    protected int cnt = 0, currentStage = 0, tillNextHarvest;
    protected Cell cell;
    protected boolean wateredYesterday = true, wateredToday = false;
    //...

    protected Plant(PlantType type) {
        this.type = type;
        tillNextHarvest = type.getTotalHarvestTime();
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Cell getCell() {
        return cell;
    }

    public void grow() {
        cnt++;
        tillNextHarvest = Integer.max(tillNextHarvest - 1, 0);
        if (cnt >= type.getStages()[currentStage] && currentStage < type.getStages().length - 1) {
            currentStage++;
            cnt = 0;
        }
        wateredYesterday = wateredToday;
        wateredToday = false;
    }

    public Fruit harvest() {
        if (tillNextHarvest > 0) {
            return null;
        }
        tillNextHarvest = type.getHarvestCycle();
        return new Fruit(type.getFruit());
    }

    public PlantType getType() {
        return type;
    }

    public void water() {
        wateredToday = true;
    }

    public void setWateredYesterday(boolean wateredYesterday) {
        this.wateredYesterday = wateredYesterday;
    }

    public void setWateredToday(boolean wateredToday) {
        this.wateredToday = wateredToday;
    }

    public boolean getWateredYesterday() {
        return wateredYesterday;
    }

    public boolean getWateredToday() {
        return wateredToday;
    }

    public boolean isGiant() {
        return isGiant;
    }

    public void setGiant(boolean giant) {
        isGiant = giant;
    }
}
