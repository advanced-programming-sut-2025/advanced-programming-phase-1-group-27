package org.example.models.tools;

import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ToolType;

public class Hoe extends Tool{

    public Hoe(ToolType toolType) {
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

}
