package org.example.server.models.enums.Weathers;

import org.example.server.models.App;
import org.example.server.models.Cell;
import org.example.server.models.Map.FarmMap;
import org.example.server.models.Player;
import org.example.server.models.enums.Plants.Plant;

import java.util.Random;

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



    public double getToolEnergyModifier() {
        return toolEnergyModifier;
    }

    public double getFishingModifier() {
        return fishingModifier;
    }



    public static Weather getWeather(String weatherName) {
        for (Weather weather : Weather.values()) {
            if (weather.name().equalsIgnoreCase(weatherName)) {
                return weather;
            }
        }
        return null;
    }
}
