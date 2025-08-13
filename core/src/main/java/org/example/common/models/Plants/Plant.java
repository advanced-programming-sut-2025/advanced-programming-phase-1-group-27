package org.example.common.models.Plants;

import com.badlogic.gdx.graphics.Texture;
import com.google.gson.internal.LinkedTreeMap;
import org.example.client.model.GameAssetManager;
import org.example.common.models.ItemManager;
import org.example.common.models.Cell;
import org.example.common.models.Stacks;

import java.util.HashMap;

public abstract class Plant {
    protected boolean isGiant = false;
    protected PlantType type;
    protected int cnt = 0, currentStage = 0, tillNextHarvest;
    protected Cell cell;
    protected boolean wateredYesterday = true, wateredToday = false, isForaging = false, alwaysWatered = false;
    protected boolean fertilized = false;
    //...

    protected Plant(PlantType type) {
        this.type = type;
        tillNextHarvest = type.getTotalHarvestTime();
    }

    public static Plant handleInfo(LinkedTreeMap<String, Object> info) {
        Plant plant = ItemManager.getPlant((String) info.get("type"));
        assert plant != null;
        plant.isGiant = (boolean) info.get("isGiant");
        plant.cnt = ((Number) info.get("cnt")).intValue();
        plant.currentStage = ((Number) info.get("currentStage")).intValue();
        plant.tillNextHarvest = ((Number) info.get("tillNextHarvest")).intValue();
        plant.wateredYesterday = ((boolean) info.get("wateredYesterday"));
        plant.wateredToday = (boolean) info.get("wateredToday");
        plant.isForaging = ((boolean) info.get("isForaging"));
        plant.alwaysWatered = ((boolean) info.get("alwaysWatered"));
        plant.fertilized = ((boolean) info.get("fertilized"));
        return plant;
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> info = new HashMap<>();
        info.put("isGiant", isGiant);
        info.put("type", type.toString());
        info.put("cnt", cnt);
        info.put("currentStage", currentStage);
        info.put("tillNextHarvest", tillNextHarvest);
        info.put("wateredYesterday", wateredYesterday);
        info.put("wateredToday", wateredToday);
        info.put("isForaging", isForaging);
        info.put("alwaysWatered", alwaysWatered);
        info.put("fertilized", fertilized);
        return info;
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
        if (currentStage < type.getStages().length  && cnt >= type.getStages()[currentStage]) {
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

    public void maxCurrentStage() {
        currentStage = type.getStages().length;
    }

    public Stacks harvest() {
        if (tillNextHarvest > 0) {
            return null;
        }
        tillNextHarvest = type.getHarvestCycle();
        return new Stacks(type.getFruit(), (isGiant ? 10 : 1));
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

    public boolean isAlwaysWatered() {
        return alwaysWatered;
    }

    public void setFertilized(boolean fertilized) {
        this.fertilized = fertilized;
    }

    public boolean isFertilized() {
        return fertilized;
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

    public Texture getTexture() {
        return GameAssetManager.getGameAssetManager().getPlantTexture(type, currentStage);
    }
}
