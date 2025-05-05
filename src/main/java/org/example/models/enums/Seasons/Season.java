package org.example.models.enums.Seasons;

import java.util.Random;

import org.example.models.enums.Weathers.Weather;

public enum Season {
    Spring(0, new Weather[]{Weather.Sunny, Weather.Rainy, Weather.Stormy}),
    Summer(1, Spring.possibleWeathers),
    Fall(2, Spring.possibleWeathers),
    Winter(3, new Weather[]{Weather.Sunny, Weather.Snowy});

    private final int index;
    private final Weather[] possibleWeathers;
    
    Season(int index, Weather[] possibleWeathers) {
        this.index = index;
        this.possibleWeathers = possibleWeathers;
    }

    public Weather pickARandomWeather() {
        int randomIndex = new Random(System.currentTimeMillis()).nextInt(possibleWeathers.length);
        return possibleWeathers[randomIndex];
    }

    public Season getNextSeason() {
        return Season.values()[(this.index + 1) % 4];
    }
}
