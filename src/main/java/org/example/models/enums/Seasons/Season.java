package org.example.models.enums.Seasons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.example.models.enums.Weathers.Weather;

public enum Season {
    Spring(0, new Weather[]{Weather.Sunny, Weather.Rain, Weather.Storm}),
    Summer(1, Spring.possibleWeathers),
    Fall(2, Spring.possibleWeathers),
    Winter(3, new Weather[]{Weather.Sunny, Weather.Snow});

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
}
