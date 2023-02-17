package com.tesleron.ezschool.Model;
public class UserDB {
    protected String displayName;

    public UserDB() {

    }

    public void setUser(UserDB user) {
        this.displayName = user.displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
