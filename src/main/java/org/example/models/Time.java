package org.example.models;

import org.example.models.enums.Seasons.Season;

public class Time {
    //implement as instance
    private int hour = 9, day = 1;
    private Season season = Season.Spring;
    private final String[] daysOfWeek = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday"};

    public int getTime() {
        return this.hour;
    }

    public int getDate() {
        return this.day;
    }

    public String getDayOfWeek() {
        return this.daysOfWeek[this.day % 7];
    }

    public String showDateTime() {
        return new String(getDayOfWeek() + ", " + getSeason() + " " + getDate() + "\n" +
                getTime() + ":00\n");
    }

    public void passAnHour() {
        hour++;
        if (hour == 24) {
            advanceDate(1);
            hour = 9;
        }
    }

    private void advanceTime(int hour) {
        this.hour += hour;
        advanceDate(this.hour / 24);
        this.hour %= 24;
    }
    
    private void advanceDate(int day) {
        this.day += day;
        while (this.day >= 28) {
            advanceSeason();
            this.day -= 28;
        }
    }

    private void advanceSeason() {
        season = season.getNextSeason();
    }
    public Season getSeason() {
        return this.season;
    }

    ///  Cheats :
    public void cheatAdvanceTime(int hour) {
        advanceTime(hour);
    }

    public void cheatAdvanceDate(int day) {
        advanceDate(day);
    }

}