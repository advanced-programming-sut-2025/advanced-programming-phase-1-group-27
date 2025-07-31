package org.example.common.models;

import org.example.server.models.enums.Seasons.Season;

public class Time {
    private Game game;
    private final String[] daysOfWeek = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday"};
    // implement as instance
    private int daysPassed = 0;
    private int hour = 9, day = 1;
    private Season season = Season.Spring;

    public Time(Game game) {
        this.game = game;
    }

    public int getHour() {
        return this.hour;
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
}