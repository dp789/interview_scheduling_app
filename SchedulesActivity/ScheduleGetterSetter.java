package com.example.tanzeem.internity.SchedulesActivity;

public class ScheduleGetterSetter {

    private String Name;
    private String Description;
    private String Time;
    private String Score;

    public ScheduleGetterSetter() {
    }

    public ScheduleGetterSetter(String title, String description, String time, String score) {
        Name = title;
        Description = description;
        Time = time;
        Score = score;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String score) {
        Score = score;
    }

    public String getName() {
        return Name;
    }

    public void setName(String title) {
        Name = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
