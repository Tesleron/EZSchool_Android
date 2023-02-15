package com.tesleron.ezschool.Model;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class LessonStorage {
    private static LessonStorage lessonStorage = null;
    private ArrayList<Lesson> lessons;

    private LessonStorage() {
        lessons = new ArrayList<>();
    }

    public static void init(){
        if (lessonStorage == null) {
            lessonStorage = new LessonStorage();
        }
    }

    public static LessonStorage getInstance(){return lessonStorage;}

    public ArrayList<Lesson> getClasses() {
        return lessons;
    }

}
