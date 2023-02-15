package com.tesleron.ezschool.Model;

import android.content.Intent;

public enum TypeOfUser {
    STUDENT, TEACHER;
    private static final String name = TypeOfUser.class.getName();
    public void attachTo(Intent intent) {
        intent.putExtra(name, ordinal());
    }
    public static TypeOfUser detachFrom(Intent intent) {
        if(!intent.hasExtra(name)) throw new IllegalStateException();
        return values()[intent.getIntExtra(name, -1)];
    }
}
