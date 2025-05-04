package org.example.models.tools;

import org.example.models.Item;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ToolType;

public class TrashCan extends Tool{
    private int percentage;

    public TrashCan(ToolType toolType) {
        StackLevel level = toolType.getLevel();
        int energyUsage = 0;
        if(level == StackLevel.Basic){
            this.percentage = 0;
        }else if(level == StackLevel.Bronze){
            this.percentage = 15;
        }else if(level == StackLevel.Iron){
            this.percentage = 30;
        }else if(level == StackLevel.Gold){
            this.percentage = 45;
        }else if(level == StackLevel.Iridium){
            this.percentage = 60;
        }
        super(level , energyUsage , toolType.getName());
    }

    public void use() {
        //deleteItem;
    }

    public void deleteItem(Item item) {
        // TODO: add getValue() method to enums
        int refund = item.getPrice() * this.percentage / 100;
        // TODO: add money to user
    }
}
