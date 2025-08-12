package org.example.server.models.tools;

import org.example.client.model.ClientApp;
import org.example.server.models.Cell;
import org.example.server.models.Player;
import org.example.server.models.Result;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.CellType;
import org.example.server.models.enums.Plants.Plant;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.items.ToolType;

public class WateringCan extends Tool {
    //Water: 40 - 55 - 70 - 85 - 100
    //EnergyUsage : 5 - 4 - 3 - 2 - 1;
    //If farming talent is max -1
    private int maxCapacity;
    private int waterCapacity;

    public WateringCan(ToolType toolType) {

        super(toolType.getLevel(), getEnergyUsageByLevel(toolType), toolType.getName(), toolType);
        setCapacity(toolType);

    }

    private static int getEnergyUsageByLevel(ToolType toolType) {

        StackLevel level = toolType.getLevel();
        int energyUsage = 0;
        if (level == StackLevel.Basic) {
            energyUsage = 5;
        } else if (level == StackLevel.Copper) {
            energyUsage = 4;
        } else if (level == StackLevel.Iron) {
            energyUsage = 3;
        } else if (level == StackLevel.Gold) {
            energyUsage = 2;
        } else if (level == StackLevel.Iridium) {
            energyUsage = 1;
        }

        return energyUsage;

    }

    @Override
    public int getEnergyUsage() {
        int energy = super.getEnergyUsage();
        if (ClientApp.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Farming).getLevel() == 4) {
            energy--;
        }
        return Math.max(energy, 0);
    }

    @Override
    public Result use(Cell cell) {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        if (cell.getType() == CellType.Water) {
            player.consumeEnergy(getEnergyUsage());
            this.waterCapacity = maxCapacity;
            return new Result(true, "Filled WateringCan!");
        } else if (cell.getObject() instanceof Plant plant) {
            if (plant.getWateredToday()) {
                return new Result(false, "Plant Was Watered Before!");
            } else {
                plant.water();
                player.consumeEnergy(getEnergyUsage());
                return new Result(true, "Plant Watered!");
            }
        } else {
            return new Result(false, "Invalid Cell!");
        }
    }

    private void setCapacity(ToolType toolType) {

        StackLevel level = toolType.getLevel();
        if (level == StackLevel.Basic) {
            this.waterCapacity = 40;
        } else if (level == StackLevel.Copper) {
            this.waterCapacity = 55;
        } else if (level == StackLevel.Iron) {
            this.waterCapacity = 70;
        } else if (level == StackLevel.Gold) {
            this.waterCapacity = 85;
        } else if (level == StackLevel.Iridium) {
            this.waterCapacity = 100;
        }

    }


}
