package com.tesleron.ezschool;



import static com.tesleron.ezschool.LoginActivity.currentUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tesleron.ezschool.Adapter.Adapter_Class;
import com.tesleron.ezschool.Model.Lesson;
import com.tesleron.ezschool.Model.LessonStorage;
import com.tesleron.ezschool.Model.StudentUser;
import com.tesleron.ezschool.Model.TeacherUser;
import com.tesleron.ezschool.Model.TypeOfUser;
import com.tesleron.ezschool.MyUtils.Constants;

import java.util.ArrayList;
import java.util.Observable;


public class MainActivity extends AppCompatActivity {
    private MaterialTextView main_LBL_username;
    private RecyclerView main_LST_classes;
//    private ArrayList<Lesson> aLessons;
    private Adapter_Class adapter_class;
    private ImageButton main_BTN_signout;
//    public static int typeOfUser; // 0 -> student, 1 -> teacher

    public static TypeOfUser typeOfUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent previousIntent = getIntent();
        typeOfUser = TypeOfUser.detachFrom(previousIntent);

        findViews();
        initViews();
        updateTitleOnScreen();

    }

    private void initViews() {
//        switch (typeOfUser)
//        {
//            case STUDENT:
//                //aLessons = StudentUser.getInstance().getClasses();
//                aLessons = LessonStorage.getInstance().getClasses();
//                break;
//            case TEACHER:
//                aLessons = LessonStorage.getInstance().getClasses();
////                aLessons = TeacherUser.getInstance().getClasses();
//        }
        adapter_class = new Adapter_Class(this/*, LessonStorage.getInstance().getClasses(), findViewById(R.id.activity_main), getSystemService(LAYOUT_INFLATER_SERVICE)*/);
        main_LST_classes.setLayoutManager(new LinearLayoutManager(this));
        main_LST_classes.setAdapter(adapter_class);

        main_BTN_signout.setOnClickListener(v -> signOut());
    }
    private void findViews() {
        main_LBL_username = findViewById(R.id.main_LBL_username);
        main_LST_classes = findViewById(R.id.main_LST_classes);
        main_BTN_signout = findViewById(R.id.main_BTN_signout);
    }

    private void updateTitleOnScreen() {
        Intent previousIntent = getIntent();
        String name = previousIntent.getExtras().getString(Constants.KEY_NAME);
        main_LBL_username.setText(name);
    }

//    Observer<ArrayList<Lesson>> observer = new Observer<ArrayList<Lesson>>() {
//
//
//        @Override
//        public void onChanged(ArrayList<Lesson> newLessons) {
//            adapter_class.updateLessons(newLessons);
//        }
//    };

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        setEmptyDataUserDB();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void setEmptyDataUserDB() {
        switch (typeOfUser) {
            case STUDENT: // student
                StudentUser.getInstance().setDisplayName(null);
                //StudentUser.getInstance().setClasses(new ArrayList<>());
                break;
            case TEACHER: // teacher
                TeacherUser.getInstance().setDisplayName(null);
                //TeacherUser.getInstance().setClasses(new ArrayList<>());
        }
    }


}