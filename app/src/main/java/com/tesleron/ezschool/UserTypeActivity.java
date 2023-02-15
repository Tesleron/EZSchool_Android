package com.tesleron.ezschool;

import static com.tesleron.ezschool.LoginActivity.currentUser;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tesleron.ezschool.Model.Lesson;
import com.tesleron.ezschool.Model.LessonStorage;
import com.tesleron.ezschool.Model.StudentUser;
import com.tesleron.ezschool.Model.TeacherUser;
import com.tesleron.ezschool.Model.TypeOfUser;
import com.tesleron.ezschool.MyUtils.Constants;
import com.tesleron.ezschool.MyUtils.FireBaseOperations;

import java.util.ArrayList;

public class UserTypeActivity extends AppCompatActivity {

    private DatabaseReference studentReference = FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_STUDENT);
    private DatabaseReference teacherReference = FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_TEACHER);
    private DatabaseReference lessonReference = FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_LESSON);

    private ExtendedFloatingActionButton type_BTN_pupil;
    private ExtendedFloatingActionButton type_BTN_teacher;

    private LottieAnimationView lottieAnimationViewPupil;
    private LottieAnimationView lottieAnimationViewTeacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_type);

        findViews();
        initViews();

        lottieAnimationViewPupil.resumeAnimation();
        lottieAnimationViewTeacher.resumeAnimation();
    }

    private void initViews() {
        type_BTN_pupil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudentUser.init(currentUser);
 //               StudentUser.getInstance().setClasses(DataManager.getClasses());
                studentReference.child(currentUser.getUid()).setValue(StudentUser.getInstance());

                DatabaseReference finalReference = studentReference.child(currentUser.getUid());
                lessonReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //LessonStorage.getInstance().getClasses().clear();
                        for (DataSnapshot snap:snapshot.getChildren()) {
                            Lesson l = snap.getValue(Lesson.class);
                            LessonStorage.getInstance().getClasses().add(l);
                           // StudentUser.getInstance().getClasses().add(l);
                        }
                       // finalReference.child(Constants.KEY_MY_LESSONS).setValue(StudentUser.getInstance().getClasses());
                        switchActivity(TypeOfUser.STUDENT);
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
                lessonReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //LessonStorage.getInstance().getClasses().clear();
                        for (DataSnapshot snap:snapshot.getChildren()) {
                            Lesson l = snap.getValue(Lesson.class);
                            LessonStorage.getInstance().getClasses().add(l);
                            //TeacherUser.getInstance().getClasses().add(l);
                        }
                        //finalReference.child(Constants.KEY_MY_LESSONS).setValue(TeacherUser.getInstance().getClasses());
                        switchActivity(TypeOfUser.TEACHER);
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

        lottieAnimationViewPupil = findViewById(R.id.animation_pupil);
        lottieAnimationViewTeacher = findViewById(R.id.animation_teacher);
    }

    private void switchActivity(TypeOfUser type) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.KEY_NAME, currentUser.getDisplayName());
        type.attachTo(intent);
        startActivity(intent);
        finish();
    }


}