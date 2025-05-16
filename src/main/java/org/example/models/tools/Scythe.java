package org.example.models.tools;

import org.example.models.*;
import org.example.models.enums.CellType;
import org.example.models.enums.Plants.Crop;
import org.example.models.enums.Plants.Fruit;
import org.example.models.enums.Plants.Plant;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.MineralType;
import org.example.models.enums.items.ToolType;

import java.util.Random;

public class Scythe extends Tool{

    public Scythe() {
        StackLevel level = ToolType.Scythe.getLevel();
        int energyUsage = 2;
        super(level , energyUsage , ToolType.Scythe.getName(), ToolType.Scythe);
    }


    @Override
    public Result use(Cell cell) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        player.consumeEnergy(getEnergyUsage());
        if (cell.getObject() instanceof Plant plant) {
            if (plant.isForaging())
                player.forageXp(10);

            Stacks fruit = plant.harvest();
            if (fruit == null) {
                return new Result(false, "The Plant is not Ready to be Harvested Yet");
            }
            player.getBackpack().addItems(fruit.getItem(), fruit.getStackLevel(), fruit.getQuantity());
            if (plant instanceof Crop crop) {
                if (crop.getType().getHarvestCycle() == -1) {
                    cell.setObject(null);
                }
            }
            player.farmXp(5);
            return new Result(true, "You Harvested " + fruit.getItem().getName());
        } else if (cell.getObject() instanceof MineralType mineralType) {
            if (mineralType == MineralType.Fiber) {
                cell.setObject(null);
                int randomInt = (new Random()).nextInt(2);
                if (randomInt == 1) {
                    player.getBackpack().addItems(MineralType.Fiber, StackLevel.Basic, 1);
                    player.forageXp(10);
                    return new Result(true, "Found 1 Fiber");
                } else {
                    player.forageXp(10);
                    return new Result(true, "Weed Was Cut");
                }
            } else {
                return new Result(false, "Can't Use On This Cell");
            }
        } else {
            return new Result(false, "Can't Use On This Cell");
        }
    }
}
