package org.example.models.tools;

import org.example.models.*;
import org.example.models.enums.Plants.Crop;
import org.example.models.enums.Plants.CropType;
import org.example.models.enums.Plants.FruitType;
import org.example.models.enums.Plants.Plant;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.MineralType;
import org.example.models.enums.items.ToolType;

import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Scythe extends Tool {

    public Scythe() {

        super(ToolType.Scythe.getLevel(), 2, ToolType.Scythe.getName(), ToolType.Scythe);

    }


    @Override
    public Result use(Cell cell) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        player.consumeEnergy(getEnergyUsage());
        if (cell.getObject() instanceof Plant plant) {
            if (plant.isForaging())
                player.forageXp(10);
            if (plant.getType() == CropType.Grass) {
                cell.setObject(null);
                int randomInt = (new Random()).nextInt(2);
                if (randomInt == 1) {
                    player.getBackpack().addItems(FruitType.Fiber, StackLevel.Basic, 1);
                    player.forageXp(10);
                    return new Result(true, "Weed Was Cut, Found 1 Fiber");
                } else {
                    player.forageXp(10);
                    return new Result(true, "Weed Was Cut");
                }
            }
            Stacks fruit = plant.harvest();
            if (fruit == null) {
                return new Result(false, "The Plant is not Ready to be Harvested Yet");
            }
            player.getBackpack().addItems(fruit.getItem(), fruit.getStackLevel(), fruit.getQuantity());
            if (plant instanceof Crop crop) {
                if (crop.getType().getHarvestCycle() == -1) {
                    cell.setObject(null);
                    int x = cell.getPosition().getX(), y = cell.getPosition().getY();
                    for (int i = max(0, x - 1); i < min(cell.getMap().getHeight(), x + 2); i++)
                        for (int j = max(0, y - 1); j < min(cell.getMap().getWidth(), y + 2); j++)
                            if (cell.getMap().getCell(i, j).getObject() == crop)
                                cell.getMap().getCell(i, j).setObject(null);
                }
            }
            player.farmXp(5);
            return new Result(true, "You Harvested " + fruit.getItem().getName());
        } else if (cell.getObject() instanceof MineralType mineralType) {
            return new Result(false, "Can't Use On This Cell");
        } else {
            return new Result(false, "Can't Use On This Cell");
        }
    }
}
