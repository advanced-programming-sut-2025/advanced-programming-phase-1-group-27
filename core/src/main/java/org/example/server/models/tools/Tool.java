package org.example.server.models.tools;

import org.example.server.models.App;
import org.example.server.models.Cell;
import org.example.server.models.Result;
import org.example.server.models.enums.StackLevel;
import org.example.server.models.enums.items.ToolType;

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
        return (int) ((double) energyUsage * App.getCurrentGame().getCurrentWeather().getToolEnergyModifier());
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
