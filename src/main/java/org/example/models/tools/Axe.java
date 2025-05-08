package models.tools;

import org.example.models.App;
import org.example.models.Cell;
import org.example.models.Player;
import org.example.models.Result;
import org.example.models.enums.AbilityType;
import org.example.models.enums.CellType;
import org.example.models.enums.Plants.Tree;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ToolType;
import org.example.models.tools.Tool;

public class Axe extends Tool {
    //If usage is failed -1
    //If foraging talent is max -1
    public Axe(ToolType toolType) {
        StackLevel level = toolType.getLevel();
        int energyUsage = 0;
        if (level == StackLevel.Basic) {
            energyUsage = 5;
        } else if (level == StackLevel.Bronze) {
            energyUsage = 4;
        } else if (level == StackLevel.Iron) {
            energyUsage = 3;
        } else if (level == StackLevel.Gold) {
            energyUsage = 2;
        } else if (level == StackLevel.Iridium) {
            energyUsage = 1;
        }
        super(level, energyUsage, toolType.getName());
    }

    @Override
    public int getEnergyUsage() {
        int energy = super.getEnergyUsage();
        if (App.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Foraging).getLevel() == 4) {
            energy--;
        }

        return Math.min(energy , 0);
    }

    @Override
    public Result use(Cell cell) {
        Player player = App.getCurrentGame().getCurrentPlayer();
        if (cell.getObject() instanceof Tree) {
            cell.setObject(null);
            cell.setType(CellType.Free);
            player.consumeEnergy(this.getEnergyUsage());
            //TODO add to inventory: 1 MineralType.Wood
            return new Result(true, "You Cut The Tree!");
        }
        return new Result(false, "Not A Tree Here!");
    }

}
