package com.tesleron.ezschool;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tesleron.ezschool.Model.Lesson;
import com.tesleron.ezschool.Model.LessonStorage;
import com.tesleron.ezschool.Model.StudentUser;
import com.tesleron.ezschool.Model.TeacherUser;
import com.tesleron.ezschool.Model.TypeOfUser;
import com.tesleron.ezschool.Model.UserDB;
import com.tesleron.ezschool.MyUtils.Constants;
import com.tesleron.ezschool.MyUtils.FireBaseOperations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public static FirebaseUser currentUser = null;
    private DatabaseReference studentReference = FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_STUDENT);
    private DatabaseReference teacherReference = FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_TEACHER);
    private DatabaseReference lessonReference = FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_LESSON);
    private boolean isStudent;
    private boolean isTeacher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        LessonStorage.init();
    }


    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) { //after reaching here, means the login session has ended, need to open the new activity window
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        Log.d("pttt","reached here after login session");
    }


    private void login(FirebaseUser currentUser) {
        if (currentUser == null) {//not found in DB

            // Choose authentication providers
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build());

            // Create and launch sign-in intent
            Intent signInIntent = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build();
            signInLauncher.launch(signInIntent);
        } else { // found in DB, initializing
            checkAlreadyExists();
        }
    }

    private void switchActivity(TypeOfUser type) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(Constants.KEY_NAME, currentUser.getDisplayName());
        type.attachTo(intent);
        startActivity(intent);
        finish();
    }

    private void loadStudentUserFromDB(DatabaseReference reference) {
        StudentUser.init(currentUser);
        reference = reference.child(currentUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentUser.getInstance().setUser(snapshot.getValue(UserDB.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        switchActivity(TypeOfUser.STUDENT);
    }

    private void loadTeacherUserFromDB(DatabaseReference reference) {
        TeacherUser.init(currentUser);
        reference = reference.child(currentUser.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TeacherUser.getInstance().setUser(snapshot.getValue(UserDB.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        switchActivity(TypeOfUser.TEACHER);
    }

    /**
     * Checks whether the user is listed in the DB, or creates a new user entirely
     */
    private void checkAlreadyExists() {
        Query studentQuery = studentReference; // need to see which reference is this
        Query teacherQuery = teacherReference;
        isStudent = false;
        isTeacher = false;

        studentQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap:snapshot.getChildren()) { // search existence in student section
                    if (currentUser.getUid().equals(snap.getKey())) {
                        loadStudentUserFromDB(studentReference);
                        isStudent = true;
                        isTeacher = false;
                        break;

                    }
                }

                if (!isStudent && !isTeacher) { //is a completely new user
                    createNewUserDB();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        teacherQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap:snapshot.getChildren()) { // search existence in teacher section
                    if (currentUser.getUid().equals(snap.getKey())) {
                        loadTeacherUserFromDB(teacherReference);
                        isTeacher = true;
                        isStudent = false;
                        break;

                    }
                }

                if (!isStudent && !isTeacher) { //is a completely new user
                    createNewUserDB();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void createNewUserDB() {
        Intent intent = new Intent(this, UserTypeActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        login(currentUser);

    }
}