package org.example.models.tools;

import org.example.models.Item;
import org.example.models.Result;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ToolType;

public class Shear extends Tool {
    private int price;

    public Shear() {
        StackLevel level = ToolType.Shear.getLevel();
        this.price = 1000;
        int energyUsage = 4;
        super(level, energyUsage , ToolType.Shear.getName());
    }

    public Result use() {
        //TODO rassa heyvun
        return new Result(false, "(!*@*&!&#$*!@#");
    }

}
