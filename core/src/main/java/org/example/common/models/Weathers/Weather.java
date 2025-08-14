package org.example.common.models.Weathers;

public enum Weather {
    Sunny(1, 1.5),
    Rainy(1.5, 1.2),
    Stormy(1, 0.5),
    Snowy(2, 1);

    private final double toolEnergyModifier;
    private final double fishingModifier;


    Weather(double toolEnergyModifier, double fishingModifier) {
        this.toolEnergyModifier = toolEnergyModifier;
        this.fishingModifier = fishingModifier;
    }

    public static Weather getWeather(String weatherName) {
        for (Weather weather : Weather.values()) {
            if (weather.name().equalsIgnoreCase(weatherName)) {
                return weather;
            }
        }
        return null;
    }

    public double getToolEnergyModifier() {
        return toolEnergyModifier;
    }

    public double getFishingModifier() {
        return fishingModifier;
    }
}
