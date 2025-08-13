package org.example.common.models.Seasons;

import org.example.common.models.Weathers.Weather;

import java.util.Random;

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
        int randomIndex = new Random().nextInt(possibleWeathers.length);
        return possibleWeathers[randomIndex];
    }

    public Season getNextSeason() {
        return Season.values()[(this.index + 1) % 4];
    }

    public static Season getSeason(String seasonName) {
        for (Season season : values()) {
            if (season.name().equalsIgnoreCase(seasonName)) {
                return season;
            }
        }
        return null;
    }
}
