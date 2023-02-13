package com.tesleron.ezschool;

import com.tesleron.ezschool.Model.Lesson;

import java.util.ArrayList;

public class DataManager {
    public static ArrayList<Lesson> getClasses() {
        ArrayList<Lesson> aLessons = new ArrayList<>();
        aLessons.add(new Lesson()
                .setName("History")
                .setStartTime(8)
                .setEndTime(10)
                .addNote("Bring history book")
        );

        aLessons.add(new Lesson()
                .setName("Algebra")
                .setStartTime(10)
                .setEndTime(11)
                .addNote("")
        );

        aLessons.add(new Lesson()
                .setName("Literature")
                .setStartTime(11)
                .setEndTime(12)
                .addNote("Bring book")
        );

        aLessons.add(new Lesson()
                .setName("Computer Science")
                .setStartTime(12)
                .setEndTime(14)
                .addNote("Bring computers")
        );

        aLessons.add(new Lesson()
                .setName("Culture")
                .setStartTime(14)
                .setEndTime(15)
                .addNote("Starting project today :)")
        );

        aLessons.add(new Lesson()
                .setName("Language")
                .setStartTime(15)
                .setEndTime(16)
                .addNote("Bring tongue book")
        );

        aLessons.add(new Lesson()
                .setName("Sports")
                .setStartTime(16)
                .setEndTime(18)
                .addNote("Bring towels")
        );


        return aLessons;
    }







}
