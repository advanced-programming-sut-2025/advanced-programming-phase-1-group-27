package org.example.models.tools;

import org.example.models.App;
import org.example.models.Item;
import org.example.models.enums.AbilityType;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ToolType;

public class WateringCan extends Tool{
    //Water: 40 - 55 - 70 - 85 - 100
    //EnergyUsage : 5 - 4 - 3 - 2 - 1;
    //If farming talent is max -1
    private int waterCapacity;
    public WateringCan(ToolType toolType) {
        StackLevel level = toolType.getLevel();
        int energyUsage = 0;
        if(level == StackLevel.Basic){
            energyUsage = 5;
            this.waterCapacity = 40;
        }else if(level == StackLevel.Bronze){
            energyUsage = 4;
            this.waterCapacity = 55;
        }else if(level == StackLevel.Iron){
            energyUsage = 3;
            this.waterCapacity = 70;
        }else if(level == StackLevel.Gold){
            energyUsage = 2;
            this.waterCapacity = 85;
        }else if(level == StackLevel.Iridium){
            energyUsage = 1;
            this.waterCapacity = 100;
        }
        super(level , energyUsage , toolType.getName());
    }

    public int getEnergy() {
        int energy = this.getEnergyUsage();
        if (App.getCurrentGame().getCurrentPlayer().getAbility(AbilityType.Farming).getLevel() == 4) {
            energy--;
        }
        return Math.min(energy , 0);
    }
}
