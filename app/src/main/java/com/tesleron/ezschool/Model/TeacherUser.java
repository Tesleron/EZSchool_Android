package com.tesleron.ezschool.Model;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class TeacherUser extends UserDB {
    private static TeacherUser teacherUser = null;

    private TeacherUser(){
//        lessons = new ArrayList<>();
    }

    private TeacherUser(FirebaseUser currentUser){
    //    lessons = new ArrayList<>();
        displayName = currentUser.getDisplayName();
    }

    public static void init(FirebaseUser currentUser){
        if (teacherUser == null) {
            teacherUser = new TeacherUser(currentUser);
        }
    }

    public static TeacherUser getInstance(){return teacherUser;}

}
