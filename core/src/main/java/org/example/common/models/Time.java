package org.example.common.models;

import com.google.gson.internal.LinkedTreeMap;
import org.example.common.models.Seasons.Season;

import java.util.HashMap;

public class Time {
    private final String[] daysOfWeek = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday"};
    private Game game;
    // implement as instance
    private int daysPassed = 0;
    private int hour = 9, day = 1;
    private Season season = Season.Spring;

    public Time(Game game) {
        this.game = game;
    }

    public HashMap<String, Object> getInfo() {
        HashMap<String, Object> timeInfo = new HashMap<>();
        timeInfo.put("daysPassed", daysPassed);
        timeInfo.put("hour", hour);
        timeInfo.put("day", day);
        timeInfo.put("season", season.name());
        return timeInfo;
    }

    public void loadTime(LinkedTreeMap<String, Object> timeInfo) {
        setDaysPassed(((Number) timeInfo.get("daysPassed")).intValue());
        setHour(((Number) timeInfo.get("hour")).intValue());
        setDay(((Number) timeInfo.get("day")).intValue());
        setSeason(Season.getSeason((String) timeInfo.get("season")));
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getDate() {
        return this.day;
    }

    public String getDayOfWeek() {
        return this.daysOfWeek[this.day % 7];
    }

    public String getDateTime() {
        return new String(getDayOfWeek() + ", " + getSeason() + " " + getDate() + "\n" +
                getHour() + ":00\n");
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void passAnHour() {
        hour++;
        if (hour == 23) {
            passADay();
            hour = 9;
        }
        game.passAnHour();
    }

    private void passADay() {
        day++;
        daysPassed++;
        if (day == 28) {
            advanceSeason();
            day = 0;
        }
        game.newDay();
    }

    private void advanceTime(int hour) {
        while (hour-- > 0)
            passAnHour();
    }

    private void advanceSeason() {
        season = season.getNextSeason();
        game.newSeason();
    }

    public Season getSeason() {
        return this.season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    ///  Cheats :
    public void cheatAdvanceTime(int hour) {
        advanceTime(hour);
    }

    public void cheatAdvanceDate(int day) {
        advanceTime(day * 14);
    }

    public int getDaysPassed() {
        return daysPassed;
    }

    public void setDaysPassed(int daysPassed) {
        this.daysPassed = daysPassed;
    }
}