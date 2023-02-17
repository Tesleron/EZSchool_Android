package com.tesleron.ezschool.Model;

import android.app.Dialog;
import android.widget.ListView;

import java.util.ArrayList;

public class Lesson {
    private String name;
    private int startTime;
    private int endTime;
    private ArrayList<String> notes;
    private ArrayList<String> chatMsgs;
    private ListView lessonListView;


    public Lesson(){
        notes = new ArrayList<>();
        chatMsgs = new ArrayList<>();
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

    public Lesson setNotes(ArrayList<String> notes) {
        this.notes = notes;
        return this;
    }

    public ArrayList<String> getChatMsgs() {
        return chatMsgs;
    }

    public Lesson setChatMsgs(ArrayList<String> chatMsgs) {
        this.chatMsgs = chatMsgs;
        return this;
    }

    public Lesson addNote(String note) {
        notes.add(note);
        return this;
    }

    public Lesson addMessage(String msg) {
        chatMsgs.add(msg);
        return this;
    }

    public ListView getLessonListView() {
        return lessonListView;
    }

    public void setLessonListView(Dialog dialog, int id) {
        lessonListView = dialog.findViewById(id);
    }
}
