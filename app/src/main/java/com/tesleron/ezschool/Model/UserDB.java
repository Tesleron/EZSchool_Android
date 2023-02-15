package com.tesleron.ezschool.Model;

import com.tesleron.ezschool.DataManager;

import java.util.ArrayList;

public class UserDB {
    protected String displayName;
//    protected ArrayList<Lesson> lessons;
//    private static UserDB userDB = null;


    public UserDB() {
       // lessons = DataManager.getClasses();
    }

//    protected UserDB(FirebaseUser currentUser) {
//        classes = new ArrayList<>();
//        this.displayName = currentUser.getDisplayName();
//
//    }

//    public static void init(FirebaseUser currentUser) {
//        if (userDB == null) {
//            userDB = new UserDB(currentUser);
//        }
//    }

 //   public static UserDB getInstance() {return userDB;}

    public void setUser(UserDB user) {
        this.displayName = user.displayName;
  //      this.lessons = user.lessons;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

//    public ArrayList<Lesson> getClasses() {
//        return lessons;
//    }

//    public void setClasses(ArrayList<Lesson> lessons) {
//        this.lessons = lessons;
//    }


}
