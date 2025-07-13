package org.example.server.models.tools;

import org.example.server.models.*;
import org.example.server.models.AnimalProperty.Animal;
import org.example.server.models.enums.items.AnimalType;
import org.example.server.models.enums.items.ToolType;

public class Shear extends Tool {
    private final int price;

    public Shear() {
//        StackLevel level = ToolType.Shear.getLevel();
        super(ToolType.Shear.getLevel(), 4, ToolType.Shear.getName(), ToolType.Shear);
        this.price = 1000;
//        int energyUsage = 4;
    }

    @Override
    public Result use(Cell cell) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        player.consumeEnergy(getEnergyUsage());
        if (cell.getObject() instanceof Animal animal && animal.getType() == AnimalType.Sheep) {
            Stacks product = animal.getProduct();
            if (product == null) {
                return new Result(false, "The Sheep is Not Ready!");
            } else {
                player.getBackpack().addItems(product.getItem(), product.getStackLevel(), product.getQuantity());
                return new Result(true, "You Got " + product.getQuantity() + " of " + product.getItem().getName() + "!");
            }
        } else {
            return new Result(false, "There is no Sheep in this Cell!");
        }
    }
}
