package models.Seasons;

import java.util.ArrayList;

import models.Weathers.Weather;

public enum Season {
    Spring({Weather.Sunny, Weather.Rain, Weather.Storm}),
    Summer(Spring.possibleWeathers),
    Fall(Spring.possibleWeathers),
    Winter({Weather.Sunny, Weather.Snow});

    private final ArrayList<Weather> possibleWeathers;
    
    Season(ArrayList<Weather> possibleWeathers) {
        this.possibleWeathers = possibleWeathers;
    }

    
    public void changeWeather() {
        //change the weather for the next day
    }
}
