package org.example.models.tools;

import org.example.models.App;
import org.example.models.Cell;
import org.example.models.Player;
import org.example.models.Result;
import org.example.models.enums.AbilityType;
import org.example.models.enums.CellType;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.MineralType;
import org.example.models.enums.items.ToolType;

public class Pickaxe extends Tool{
    //EnergyUsage : 5 - 4 - 3 - 2 - 1;
    //If usage is failed -1
    //If mining talent is max -1
    public Pickaxe(ToolType toolType) {
        StackLevel level = toolType.getLevel();
        int energyUsage = 0;
        if(level == StackLevel.Basic){
            energyUsage = 5;
        }else if(level == StackLevel.Bronze){
            energyUsage = 4;
        }else if(level == StackLevel.Iron){
            energyUsage = 3;
        }else if(level == StackLevel.Gold){
            energyUsage = 2;
        }else if(level == StackLevel.Iridium){
            energyUsage = 1;
        }
        super(level , energyUsage , toolType.getName());
    }

    @Override
    public int getEnergyUsage() {
        int energy = super.getEnergyUsage();
        if (App.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Mining).getLevel() == 4) {
            energy--;
        }
        return Math.min(energy , 0);
    }


    @Override
    public Result use(Cell cell) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        Object object = cell.getObject();
        if (cell.getType() == CellType.Plowed && object == null) {
            player.consumeEnergy(this.getEnergyUsage());
            cell.setType(CellType.Free);
            return new Result(true, "You UnPlowed The Cell!!");
        }
        else if (object instanceof MineralType) {
            if (MineralType.getMinerals().contains((MineralType) object)) {
                if (level.isBetterThan(((MineralType) object).getLevel())) {
                    player.consumeEnergy(this.getEnergyUsage());
                    cell.setType(CellType.Free);
                    cell.setObject(null);
                    //TODO add to Inventory: 1 object
                    player.mineXp(10);
                    return new Result(true, "You Extracted A " + ((MineralType) object).getName() +
                            " Mineral!!");

                } else {
                    player.consumeEnergy(Math.min(this.getEnergyUsage() - 1, 0));
                    return new Result(false, "Your Tool is not Strong Enough for This Mineral!");
                }
            } else if (MineralType.getJewels().contains((MineralType) object)) {
                if (level.isBetterThan(StackLevel.Bronze)) {
                    player.consumeEnergy(this.getEnergyUsage());
                    cell.setType(CellType.Free);
                    cell.setObject(null);
                    //TODO add to Inventory: 1 object
                    player.mineXp(10);
                    return new Result(true, "You Extracted A " + ((MineralType) object).getName() +
                            " Jewel!!");
                } else {
                    player.consumeEnergy(Math.min(this.getEnergyUsage() - 1, 0));
                    return new Result(false, "Your Tool is not Strong Enough for This Jewel!");
                }
            } else {
                player.consumeEnergy(this.getEnergyUsage());
                cell.setType(CellType.Free);
                cell.setObject(null);
                //TODO add to Inventory: 1 object
                player.mineXp(10);
                return new Result(true, "You Extracted A " + ((MineralType) object).getName());
            }
        } else if (cell.getType() == CellType.Occupied) {
            //TODO
            return new Result(false, "#$))!*)*#$)(%)()(#)@($)#@4");
        } else {
            player.consumeEnergy(Math.min(this.getEnergyUsage() - 1, 0));
            return new Result(false, "Cannot Use A Pickaxe Here !");
        }
    }
}
