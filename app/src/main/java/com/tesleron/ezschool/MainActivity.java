package com.tesleron.ezschool;



import static com.tesleron.ezschool.LoginActivity.currentUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tesleron.ezschool.Adapter.Adapter_Class;
import com.tesleron.ezschool.Model.Lesson;
import com.tesleron.ezschool.Model.StudentUser;
import com.tesleron.ezschool.Model.TeacherUser;
import com.tesleron.ezschool.MyUtils.Constants;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private MaterialTextView main_LBL_username;
    private RecyclerView main_LST_classes;
    private ArrayList<Lesson> aLessons;
    private Adapter_Class adapter_class;
    private int typeOfUser;


//    private TextView main_LBL_title;
//    private AppCompatEditText main_ET_text;
//    private MaterialButton main_BTN_update;
//    private MaterialButton main_BTN_saveGarage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent previousIntent = getIntent();
        typeOfUser = previousIntent.getExtras().getInt(Constants.KEY_TYPE);

        findViews();
        initViews();
        updateTitleOnScreen();

    }

    private void initViews() {
        switch (typeOfUser)
        {
            case 0:
                aLessons = StudentUser.getInstance().getClasses();
                break;
            case 1:
                aLessons = TeacherUser.getInstance().getClasses();
        }
        adapter_class = new Adapter_Class(this, aLessons, findViewById(R.id.activity_main), getSystemService(LAYOUT_INFLATER_SERVICE));
        main_LST_classes.setLayoutManager(new LinearLayoutManager(this));
        main_LST_classes.setAdapter(adapter_class);
//        main_BTN_update.setOnClickListener(v -> {
//            setTitle(main_ET_text.getText().toString());
//        });
//        main_BTN_saveGarage.setOnClickListener(v -> updateCarAsFWD("15-336-66"));
    }

    private void updateCarAsFWD(String licensePlate){
//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        DatabaseReference garageRef = db.getReference("garage");
//        garageRef.child("allCars").child(licensePlate).child("fourWheelDrive").setValue(true);
    }


    private void findViews() {
        main_LBL_username = findViewById(R.id.main_LBL_username);
        main_LST_classes = findViewById(R.id.main_LST_classes);


//        main_LBL_title = findViewById(R.id.main_LBL_title);
//        main_ET_text = findViewById(R.id.main_ET_text);
//        main_BTN_update = findViewById(R.id.main_BTN_update);
//        main_BTN_saveGarage = findViewById(R.id.main_BTN_saveGarage);
    }


    private void setTitle(String title) {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ezschool-9d756-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference titleRef = database.getReference("title");
        Log.d("setTitle", title);
        titleRef.setValue(title);
    }

    private void updateTitleOnScreen() {
        Intent previousIntent = getIntent();
        String name = previousIntent.getExtras().getString(Constants.KEY_NAME);
        main_LBL_username.setText(name);
//        FirebaseDatabase database = FirebaseDatabase.getInstance("https://ezschool-9d756-default-rtdb.europe-west1.firebasedatabase.app/");
//        DatabaseReference titleRef = database.getReference("title");
//        titleRef.addListenerForSingleValueEvent(new ValueEventListener() { // For one time data load.
//        //titleRef.addValueEventListener(new ValueEventListener() { // for all time data load.
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String value = dataSnapshot.getValue(String.class);
//                main_LBL_title.setText(value);
//                Log.d("Value Changed", "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("Canceled", "Failed to read value.", error.toException());
//            }
//        });
    }
}