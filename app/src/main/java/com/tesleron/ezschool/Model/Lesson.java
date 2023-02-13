package com.tesleron.ezschool.Model;

import java.util.ArrayList;

public class Lesson {
    private String name;
    private int startTime;
    private int endTime;
    private ArrayList<String> notes;

    public Lesson(){
        notes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Lesson setName(String name) {
        this.name = name;
        return this;
    }

    public int getStartTime() {
        return startTime;
    }

    public Lesson setStartTime(int startTime) {
        this.startTime = startTime;
        return this;
    }

    public int getEndTime() {
        return endTime;
    }

    public Lesson setEndTime(int endTime) {
        this.endTime = endTime;
        return this;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public Lesson addNote(String note) {
        notes.add(note);
        return this;
    }


    public Lesson setNotes(ArrayList<String> notes) {
        this.notes = notes;
        return this;
    }
}
