package com.tesleron.ezschool.Model;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class StudentUser extends UserDB {
    private static StudentUser studentUser = null;

    private StudentUser(){
    }

    private StudentUser(FirebaseUser currentUser){
        displayName = currentUser.getDisplayName();
    }

    public static void init(FirebaseUser currentUser){
        if (studentUser == null) {
            studentUser = new StudentUser(currentUser);
        }
    }

    public static StudentUser getInstance(){return studentUser;}
}
