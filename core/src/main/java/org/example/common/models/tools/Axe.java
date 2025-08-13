package org.example.common.models.tools;

import org.example.client.model.ClientApp;
import org.example.common.models.Cell;
import org.example.common.models.Player;
import org.example.common.models.Result;
import org.example.common.models.AbilityType;
import org.example.common.models.CellType;
import org.example.common.models.Plants.Tree;
import org.example.common.models.StackLevel;
import org.example.common.models.items.MineralType;
import org.example.common.models.items.ToolType;

import java.util.Random;

public class Axe extends Tool {

    //If usage is failed -1
    //If foraging talent is max -1

    public Axe(ToolType toolType) {

        super(toolType.getLevel(), getEnergyUsageLevel(toolType), toolType.getName(), toolType);

    }

    private static int getEnergyUsageLevel(ToolType toolType) {

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
        if (ClientApp.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Foraging).getLevel() == 4) {
            energy--;
        }

        return Math.max(energy, 0);
    }

    @Override
    public Result use(Cell cell) {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        if (cell.getObject() instanceof Tree tree) {
            cell.setObject(null);
            cell.setType(CellType.Free);
            player.consumeEnergy(this.getEnergyUsage());
            player.getBackpack().addItems(MineralType.Wood, null, 5);
            int cnt = new Random().nextInt(2) + 1;

            return new Result(true, "You Cut The Tree! You Got 5 Wood and " +
                    cnt + " " + tree.getType().getSource() + "(s).");
        }
        return new Result(false, "Not A Tree Here!");
    }


}
