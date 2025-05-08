package org.example.models.tools;

import org.example.models.App;
import org.example.models.Cell;
import org.example.models.Result;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ToolType;

public class MilkPail extends Tool{

    private int price;

    public MilkPail() {
        StackLevel level = ToolType.MilkPail.getLevel();
        this.price = 1000;
        int energyUsage = 4;
        super(level, energyUsage , ToolType.MilkPail.getName());
    }

    @Override
    public Result use(Cell cell) {
        App.getCurrentGame().getCurrentPlayer().consumeEnergy(getEnergyUsage());
        //TODO rassa heyvun bezan
        return new Result(false, "");
    }
}
