package org.example.models.tools;

import org.example.models.Cell;
import org.example.models.Result;
import org.example.models.enums.StackLevel;
import org.example.models.enums.items.ToolType;

public abstract class Tool{
    private final ToolType toolType;
    private final int energyUsage;
    protected StackLevel level;
    private String name;

    public Tool(StackLevel level , int energyUsage , String name, ToolType toolType) {
        this.level = level;
        this.energyUsage = energyUsage;
        this.name = name;
        this.toolType = toolType;
    }

    protected int getEnergyUsage(){
        return energyUsage;
    }

    public StackLevel getLevel() {
        return level;
    }

    public String getName() {
        return name;
    }

    public Result use(Cell cell) {
        return null;
    }

    public ToolType getToolType() {
        return toolType;
    }
}
