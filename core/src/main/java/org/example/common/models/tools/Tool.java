package org.example.common.models.tools;

import org.example.common.models.Cell;
import org.example.common.models.Result;
import org.example.common.models.StackLevel;
import org.example.common.models.items.ToolType;

public abstract class Tool {
    private final ToolType toolType;
    private final int energyUsage;
    protected StackLevel level;
    private String name;

    public Tool(StackLevel level, int energyUsage, String name, ToolType toolType) {
        this.level = level;
        this.energyUsage = energyUsage;
        this.name = name;
        this.toolType = toolType;
    }

    protected int getEnergyUsage() {
        return 2;
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
