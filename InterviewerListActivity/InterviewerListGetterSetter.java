package com.example.tanzeem.internity.InterviewerListActivity;

import android.util.Log;

public class InterviewerListGetterSetter {

    private String name;
    private String description;
    private int thumbnail;

    //Default Constructor
    public InterviewerListGetterSetter() { }

    //Parameterised Constructor
    public InterviewerListGetterSetter(String name, String description, int thumbnail) {
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    //Getter
    public String getName() { return name; }

    public String getDescription() { return description; }

    public int getThumbnail() { return thumbnail; }

    //Setter
    public void setName(String name) {  this.name = name; }

    public void setDescription(String description) { this.description = description; }

    public void setThumbnail(int thumbnail) { this.thumbnail = thumbnail; }
}
