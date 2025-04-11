package models.tools;

import enums.Item;

public abstract class Tool implements Item {
    private final int energyUsage;
    private int level;
    private String name;

    public Tool(int level , int energyUsage , String name) {
        this.level = level;
        this.energyUsage = energyUsage;
        this.name = name;
    }

    public int getEnergyUsage(){
        return energyUsage;
    }

    public int getLevel() {
        return level;
    }

    public abstract void use();

    private void upgradeLevel(){
        this.level++;
    }
}
