package org.example.server.models.tools;

import org.example.client.model.ClientApp;
import org.example.server.models.*;
import org.example.server.models.enums.AbilityType;
import org.example.server.models.enums.CellType;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.items.MineralType;
import org.example.server.models.enums.items.ToolType;
import org.example.server.models.enums.items.products.CraftingProduct;

public class Pickaxe extends Tool {
    //EnergyUsage : 5 - 4 - 3 - 2 - 1;
    //If usage is failed -1
    //If mining talent is max -1
    public Pickaxe(ToolType toolType) {

        super(toolType.getLevel(), getEnergyUsageByLevel(toolType), toolType.getName(), toolType);

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
        if (ClientApp.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Mining).getLevel() == 4) {
            energy--;
        }
        return Math.min(energy, 0);
    }

    @Override
    public Result use(Cell cell) {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        Object object = cell.getObject();
        if (cell.getType() == CellType.Plowed && object == null) {
            player.consumeEnergy(this.getEnergyUsage());
            cell.setType(CellType.Free);
            return new Result(true, "You UnPlowed The Cell!!");
        } else if (object instanceof MineralType mineralType) {
            if (MineralType.getMinerals().contains(mineralType)) {
                if (level.isBetterThan((mineralType).getLevel())) {
                    player.consumeEnergy(this.getEnergyUsage());
                    cell.setObject(null);
                    player.getBackpack().addItems(mineralType, null, 5);
                    if (player.getAbility(AbilityType.Mining).getLevel() >= 2)
                        player.getBackpack().addItems(mineralType, null, 5);


                    player.mineXp(10);
                    return new Result(true, "You Extracted A " + (mineralType).getName() +
                            " Mineral!!");

                } else {
                    player.consumeEnergy(Math.min(this.getEnergyUsage() - 1, 0));
                    return new Result(false, "Your Tool is not Strong Enough for This Mineral!");
                }
            } else if (MineralType.getJewels().contains(mineralType)) {
                if (level.isBetterThan(StackLevel.Copper)) {
                    player.consumeEnergy(this.getEnergyUsage());
                    cell.setObject(null);
                    player.getBackpack().addItems(mineralType, null, 5);
                    if (player.getAbility(AbilityType.Mining).getLevel() >= 2)
                        player.getBackpack().addItems(mineralType, null, 5);

                    player.mineXp(10);
                    return new Result(true, "You Extracted A " + mineralType.getName() +
                            " Jewel!!");
                } else {
                    player.consumeEnergy(Math.min(this.getEnergyUsage() - 1, 0));
                    return new Result(false, "Your Tool is not Strong Enough for This Jewel!");
                }
            } else {
                player.consumeEnergy(this.getEnergyUsage());
                cell.setObject(null);
                player.getBackpack().addItems(mineralType, null, 5);
                if (player.getAbility(AbilityType.Mining).getLevel() >= 2)
                    player.getBackpack().addItems(mineralType, null, 5);

                player.mineXp(10);
                return new Result(true, "You Extracted A " + mineralType.getName());
            }
        } else if (cell.getObject() instanceof Artisan artisan) {
            cell.setObject(null);
            player.getBackpack().addItems(CraftingProduct.getItem(artisan.getType().toString()), null, 1);
            return new Result(false, "You Removed And Claimed The Artisan!");
        } else {
            player.consumeEnergy(Math.min(this.getEnergyUsage() - 1, 0));
            return new Result(false, "Cannot Use A Pickaxe Here !");
        }
    }

}
