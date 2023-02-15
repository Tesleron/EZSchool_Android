package com.tesleron.ezschool.Model;

import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.Observable;

public class ObserveableLessons implements Observer {
    private ArrayList<Lesson> lessons;

    public ObserveableLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;

    }

    @Override
    public void onChanged(Object o) {

    }
}
