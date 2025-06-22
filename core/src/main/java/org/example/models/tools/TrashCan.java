package org.example.models.tools;

import org.example.models.App;
import org.example.models.Result;
import org.example.models.Stacks;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ToolType;

public class TrashCan extends Tool{
    private int percentage;

    public TrashCan(ToolType toolType) {

        super(toolType.getLevel() , 0 , toolType.getName(), toolType);

        if(level == StackLevel.Basic){
            this.percentage = 0;
        }else if(level == StackLevel.Copper){
            this.percentage = 15;
        }else if(level == StackLevel.Iron){
            this.percentage = 30;
        }else if(level == StackLevel.Gold){
            this.percentage = 45;
        }else if(level == StackLevel.Iridium){
            this.percentage = 60;
        }

    }

    public void use() {
        //deleteItem;
    }

    public Result deleteItem(Stacks stack) {
        int refund = stack.getTotalPrice() * this.percentage / 100;
        App.getCurrentGame().getCurrentPlayer().addMoney(refund);
        return new Result(true, "Item Sold");
    }
}
