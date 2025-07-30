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

    private static void applyRain() {
        for (Player player : App.getCurrentGame().getPlayers()) {
            FarmMap map = player.getFarmMap();
            Cell[][] cells = map.getCells();
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[i].length; j++) {
                    if (cells[i][j].getObject() instanceof Plant && cells[i][j].getBuilding() == null) {
                        Plant plant = (Plant) cells[i][j].getObject();
                        plant.water();
                    }
                }
            }
        }
    }

    private static void applyThor() {
        for (Player player : App.getCurrentGame().getPlayers()) {
            FarmMap map = player.getFarmMap();
            Cell[][] cells = map.getCells();
            for (int i = 0; i < 3; i++) {
                int x = (new Random()).nextInt(cells.length);
                int y = (new Random()).nextInt(cells[0].length);
                cells[x][y].thor();
            }
        }
    }

    public double getToolEnergyModifier() {
        return toolEnergyModifier;
    }

    public double getFishingModifier() {
        return fishingModifier;
    }

    public void applyWeatherEffect() {
        if (this == Stormy) applyThor();
        if (this == Stormy || this == Rainy) applyRain();

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
