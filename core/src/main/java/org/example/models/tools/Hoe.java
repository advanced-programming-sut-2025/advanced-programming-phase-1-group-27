package org.example.models.tools;

import org.example.models.App;
import org.example.models.Cell;
import org.example.models.Player;
import org.example.models.Result;
import org.example.models.enums.CellType;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ToolType;

public class Hoe extends Tool{

    public Hoe(ToolType toolType) {
        StackLevel level = toolType.getLevel();
        int energyUsage = 0;
        if(level == StackLevel.Basic){
            energyUsage = 5;
        }else if(level == StackLevel.Copper){
            energyUsage = 4;
        }else if(level == StackLevel.Iron){
            energyUsage = 3;
        }else if(level == StackLevel.Gold){
            energyUsage = 2;
        }else if(level == StackLevel.Iridium){
            energyUsage = 1;
        }
        super(level , energyUsage , toolType.getName(), toolType);
    }

    @Override
    public Result use(Cell cell) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        player.consumeEnergy(this.getEnergyUsage());
        if (cell.getType() == CellType.Free) {
            cell.setType(CellType.Plowed);
            return new Result(true, "Plowed!");
        }
        return new Result(true, "Cannot Plow, Not a Free Cell!");
    }

}
