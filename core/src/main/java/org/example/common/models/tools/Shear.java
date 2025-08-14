package org.example.common.models.tools;

import org.example.client.model.ClientApp;
import org.example.common.models.AnimalProperty.Animal;
import org.example.common.models.Cell;
import org.example.common.models.Player;
import org.example.common.models.Result;
import org.example.common.models.Stacks;
import org.example.common.models.items.AnimalType;
import org.example.common.models.items.ToolType;

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
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
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
