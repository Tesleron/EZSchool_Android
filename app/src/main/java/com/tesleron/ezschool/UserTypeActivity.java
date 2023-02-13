package com.tesleron.ezschool;

import static com.tesleron.ezschool.LoginActivity.currentUser;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tesleron.ezschool.Model.Lesson;
import com.tesleron.ezschool.Model.StudentUser;
import com.tesleron.ezschool.Model.TeacherUser;
import com.tesleron.ezschool.MyUtils.Constants;
import com.tesleron.ezschool.MyUtils.FireBaseOperations;

import java.util.ArrayList;

public class UserTypeActivity extends AppCompatActivity {

    private DatabaseReference studentReference = FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_STUDENT);
    private DatabaseReference teacherReference = FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_TEACHER);
    private DatabaseReference lessonReference = FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_LESSON);

    private ExtendedFloatingActionButton type_BTN_pupil;
    private ExtendedFloatingActionButton type_BTN_teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        findViews();
        initViews();
    }

    private void initViews() {
        type_BTN_pupil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentUser.init(currentUser);
 //               StudentUser.getInstance().setClasses(DataManager.getClasses());
                studentReference.child(currentUser.getUid()).setValue(StudentUser.getInstance());

                DatabaseReference finalReference = studentReference.child(currentUser.getUid());
                lessonReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap:snapshot.getChildren()) {
                            Lesson l = snap.getValue(Lesson.class);
                            StudentUser.getInstance().getClasses().add(l);
                        }
                        finalReference.child("Lessons").setValue(StudentUser.getInstance().getClasses());
                        switchActivity(0);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        type_BTN_teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TeacherUser.init(currentUser);
 //               TeacherUser.getInstance().setClasses(DataManager.getClasses());
                teacherReference.child(currentUser.getUid()).setValue(TeacherUser.getInstance());

                DatabaseReference finalReference = teacherReference.child(currentUser.getUid());
                lessonReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snap:snapshot.getChildren()) {
                            Lesson l = snap.getValue(Lesson.class);
                            TeacherUser.getInstance().getClasses().add(l);
                        }
                        finalReference.child("Lessons").setValue(TeacherUser.getInstance().getClasses());
                        switchActivity(1);
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
    }

    private void findViews() {
        type_BTN_pupil = findViewById(R.id.type_BTN_pupil);
        type_BTN_teacher = findViewById(R.id.type_BTN_teacher);
    }

    private void switchActivity(int typeOfUser) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.KEY_NAME, currentUser.getDisplayName());
        intent.putExtra(Constants.KEY_TYPE, typeOfUser);
        startActivity(intent);
        finish();
    }


}