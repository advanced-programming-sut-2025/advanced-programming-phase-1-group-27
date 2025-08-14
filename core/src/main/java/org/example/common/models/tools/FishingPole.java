package org.example.common.models.tools;

import org.example.client.Main;
import org.example.client.controller.ResultController;
import org.example.client.model.ClientApp;
import org.example.client.view.MiniGame.PreMiniGameMenuView;
import org.example.common.models.*;
import org.example.common.models.items.ToolType;

public class FishingPole extends Tool {

    private int price;

    //Required fishing talent : 0 , 0 , 2 , 4
    //If fishing talent is max -1
    public FishingPole(ToolType toolType) {

        super(toolType.getLevel(), getEnergyUsageByType(toolType), toolType.getName(), toolType);
        setPriceByType(toolType);
    }

    private static int getEnergyUsageByType(ToolType toolType) {

        StackLevel level = toolType.getLevel();
        int energyUsage = 0;
        if (level == StackLevel.Training) {
            energyUsage = 8;
        } else if (level == StackLevel.Bamboo) {
            energyUsage = 8;
        } else if (level == StackLevel.Fiberglass) {
            energyUsage = 6;
        } else if (level == StackLevel.Iridium) {
            energyUsage = 4;
        }

        return energyUsage;

    }

    public int getPrice() {
        return price;
    }

    @Override
    public int getEnergyUsage() {
        int energy = super.getEnergyUsage();
        if (ClientApp.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() == 4) {
            energy--;
        }
        return Math.min(energy, 0);
    }

    public boolean enoughAbility() {
        if (this.getLevel() == StackLevel.Basic) {
            return ClientApp.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() >= 0;
        } else if (this.getLevel() == StackLevel.Large) {
            return ClientApp.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() >= 0;
        } else if (this.getLevel() == StackLevel.Fiberglass) {
            return ClientApp.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() >= 2;
        } else if (this.getLevel() == StackLevel.Iridium) {
            return ClientApp.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Fishing).getLevel() >= 4;
        }
        return false;
    }

    private void setPriceByType(ToolType toolType) {


        StackLevel level = toolType.getLevel();

        if (level == StackLevel.Training) {
            this.price = 25;
        } else if (level == StackLevel.Bamboo) {
            this.price = 500;
        } else if (level == StackLevel.Fiberglass) {
            this.price = 1800;
        } else if (level == StackLevel.Iridium) {
            this.price = 7500;
        }


    }

    @Override
    public Result use(Cell cell) {
        if (cell.getType() == CellType.Water) {

            if (ClientApp.getCurrentGame().getCurrentPlayer().getEnergy() < ToolType.getFishPoleEnergy(this.getToolType())) {
                ResultController.addError("Fishing failed! You don't have enough energy!");
                ClientApp.getCurrentGame().getCurrentPlayer().setEnergy(0);
            } else {
                Main.getMain().getScreen().dispose();
                Main.getMain().setScreen(new PreMiniGameMenuView(this.getToolType()));
            }

        }
        return new Result(false, "YOOO");
    }
}
