package com.tesleron.ezschool.Model;

import static com.tesleron.ezschool.LoginActivity.currentUser;

import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tesleron.ezschool.MyUtils.Constants;
import com.tesleron.ezschool.MyUtils.FireBaseOperations;
import com.tesleron.ezschool.MyUtils.MySignal;

import java.util.ArrayList;
import java.util.Observable;

public class LessonStorage extends Observable {
    private static LessonStorage lessonStorage = null;
    private MutableLiveData<ArrayList<Lesson>> lessons;
    DatabaseReference lessonReference = FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_LESSON);
    private ArrayAdapter<String> currentOpenAdapter;
    private int currentIndexOnOpenedLesson;

    private LessonStorage() {
        lessons = new MutableLiveData<>();

        ArrayList<Lesson> theLessons = new ArrayList<>();
        lessonReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Lesson l = snapshot.getValue(Lesson.class);
                theLessons.add(l);
                lessons.setValue(theLessons);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }


            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lessonReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Lesson> lessons = new ArrayList<>();
                for (DataSnapshot snap:snapshot.getChildren()) {
                    Lesson l = snap.getValue(Lesson.class);
                    lessons.add(l);
                    Log.d("pttt", l.getNotes().toString());
                    Log.d("pttt", l.getChatMsgs().toString());
                }
                LessonStorage.getInstance().setLessons(lessons);
                if (currentOpenAdapter != null)
                {
                    currentOpenAdapter.clear();
                    currentOpenAdapter.addAll(lessons.get(currentIndexOnOpenedLesson).getChatMsgs());
                    currentOpenAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static void init(){
        if (lessonStorage == null) {
            lessonStorage = new LessonStorage();
        }
    }

    public static LessonStorage getInstance(){return lessonStorage;}

    public LiveData<ArrayList<Lesson>> getClasses() {
        return lessons;
    }

    public void setLessons (ArrayList<Lesson> newLessons) {
        lessons.setValue(newLessons);
    }

    public ArrayAdapter<String> getCurrentOpenAdapter() {
        return currentOpenAdapter;
    }

    public void setCurrentOpenAdapter(ArrayAdapter<String> currentOpenAdapter) {
        this.currentOpenAdapter = currentOpenAdapter;
    }

    public void setCurrentIndexOnOpenedLesson(int pos) {
        currentIndexOnOpenedLesson = pos;
    }
}
