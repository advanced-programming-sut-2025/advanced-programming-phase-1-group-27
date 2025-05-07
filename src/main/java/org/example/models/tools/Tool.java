package org.example.models.tools;

import org.example.models.App;
import org.example.models.enums.StackLevel;

public abstract class Tool{
    private final int energyUsage;
    protected StackLevel level;
    private String name;

    public Tool(StackLevel level , int energyUsage , String name) {
        this.level = level;
        this.energyUsage = energyUsage;
        this.name = name;
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
}
