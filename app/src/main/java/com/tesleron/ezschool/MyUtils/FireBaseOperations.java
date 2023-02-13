package com.tesleron.ezschool.MyUtils;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseOperations {

    //singleton
    private static FireBaseOperations fireBaseOperations = null;

    //firebase members

    private FirebaseDatabase database ;
    private DatabaseReference databaseReference;


    public FireBaseOperations(){
        database = FirebaseDatabase.getInstance("https://ezschool-9d756-default-rtdb.europe-west1.firebasedatabase.app/");

    }

    public static FireBaseOperations getInstance(){
        if(fireBaseOperations == null){
            fireBaseOperations = new FireBaseOperations();
        }
        return fireBaseOperations;
    }

    public DatabaseReference getDatabaseReference(String name) {
        databaseReference = database.getReference(name);
        return databaseReference;
    }
}
