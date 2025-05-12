package org.example.models.tools;

import org.example.models.*;
import org.example.models.AnimalProperty.Animal;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.AnimalType;
import org.example.models.enums.items.ToolType;

public class MilkPail extends Tool{

    private int price;

    public MilkPail() {
        StackLevel level = ToolType.MilkPail.getLevel();
        this.price = 1000;
        int energyUsage = 4;
        super(level, energyUsage , ToolType.MilkPail.getName(), ToolType.MilkPail);
    }

    @Override
    public Result use(Cell cell) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        player.consumeEnergy(getEnergyUsage());
        if (cell.getObject() instanceof Animal animal &&
                (animal.getType() == AnimalType.Cow || animal.getType() == AnimalType.Goat)) {
            Stacks product = animal.getProduct();
            if (product == null) {
                return new Result(false, "The Animal is Not Ready!");
            } else {
                player.getBackpack().addItems(product.getItem(), product.getStackLevel(), product.getQuantity());
                return new Result(true, "You Got " + product.getQuantity() + " of " + product.getItem().getName() + "!");
            }
        }
        else {
            return new Result(false, "There is No Goat/Cow in This Cell!");
        }
    }
}
