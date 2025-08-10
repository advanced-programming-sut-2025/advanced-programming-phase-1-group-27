package org.example.server.models.enums.Plants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import org.example.client.controller.menus.OnlinePlayersMenuController;
import org.example.common.models.GameAssetManager;
import org.example.server.models.Cell;
import org.example.server.models.Stacks;

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
