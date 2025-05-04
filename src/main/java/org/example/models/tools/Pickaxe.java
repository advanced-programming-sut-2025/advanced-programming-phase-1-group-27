package org.example.models.tools;

import org.example.models.App;
import org.example.models.enums.AbilityType;
import org.example.models.enums.StackLevel;
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

    public int getEnergy() {
        int energy = this.getEnergyUsage();
        if (App.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Mining).getLevel() == 4) {
            energy--;
        }
        return Math.min(energy , 0);
    }
}
