package org.example.models.enums.Weathers;
public enum Weather {
    Sunny(1),
    Rain(1.5),
    Storm(1),
    Snow(2);

    private final double toolEnergyModifier;

    Weather(double toolEnergyModifier) {
        this.toolEnergyModifier = toolEnergyModifier;
    }

    public double getToolEnergyModifier() {
        return toolEnergyModifier;
    }

    public void pre() {
        // Apply the weathers effect
    }
}
