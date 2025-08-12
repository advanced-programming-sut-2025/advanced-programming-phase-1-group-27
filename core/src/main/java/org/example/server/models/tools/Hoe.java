package org.example.server.models.tools;

import org.example.client.model.ClientApp;
import org.example.server.models.Cell;
import org.example.server.models.Player;
import org.example.server.models.Result;
import org.example.server.models.enums.CellType;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.items.ToolType;

public class Hoe extends Tool {

    public Hoe(ToolType toolType) {

        super(toolType.getLevel(), getEnergyUsageByType(toolType), toolType.getName(), toolType);
    }

    private static int getEnergyUsageByType(ToolType toolType) {

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
    public Result use(Cell cell) {
        Player player = ClientApp.getCurrentGame().getCurrentPlayer();
        player.consumeEnergy(this.getEnergyUsage());
        if (cell.getType() == CellType.Free) {
            cell.setType(CellType.Plowed);
            return new Result(true, "Plowed!");
        }
        return new Result(true, "Cannot Plow, Not a Free Cell!");
    }

}
