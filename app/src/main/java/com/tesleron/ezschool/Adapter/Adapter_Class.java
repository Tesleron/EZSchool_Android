package com.tesleron.ezschool.Adapter;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import static com.tesleron.ezschool.LoginActivity.currentUser;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.tesleron.ezschool.Model.Lesson;
import com.tesleron.ezschool.MyUtils.Constants;
import com.tesleron.ezschool.MyUtils.FireBaseOperations;
import com.tesleron.ezschool.MyUtils.MyStringUtils;
import com.tesleron.ezschool.R;

import java.util.ArrayList;

public class Adapter_Class extends RecyclerView.Adapter<Adapter_Class.ClassViewHolder> {

    private Context context;
    private ArrayList<Lesson> aLessons;
    private View parentView;
    private Object systemService;

    public Adapter_Class(Context context, ArrayList<Lesson> aLessons, View activityView, Object systemService) {
        this.context = context;
        this.aLessons = aLessons;
        parentView = activityView;
        this.systemService = systemService;

    }

    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_list, parent, false);
        ClassViewHolder myClassViewHolder = new ClassViewHolder(view);
        return myClassViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        Lesson aLesson = aLessons.get(position);

        holder.list_LBL_name.setText(aLesson.getName());
        holder.list_LBL_duration.setText( MyStringUtils.getDurationFromation(aLesson.getStartTime(), aLesson.getEndTime()));
        holder.list_LBL_teachernotes.setText(aLesson.getNotes().toString());

//        if (song.isFavorite()) {
//            holder.itemView.setBackgroundColor(Color.RED);
//        } else {
//            holder.itemView.setBackgroundColor(Color.YELLOW);
//        }
    }

    @Override
    public int getItemCount() {
        return aLessons == null ? 0 : aLessons.size();
    }

    class ClassViewHolder extends RecyclerView.ViewHolder {

        private MaterialTextView list_LBL_name;
        private MaterialTextView list_LBL_duration;
        private MaterialTextView list_LBL_teachernotes;
        private ImageButton list_IMG_add;
        private ImageButton list_IMG_delete;
        private ImageButton list_IMG_chat;
//        private AppCompatImageView song_IMG_image;

        public ClassViewHolder(View itemView) {
            super(itemView);
            list_LBL_name = itemView.findViewById(R.id.list_LBL_name);
            list_LBL_duration = itemView.findViewById(R.id.list_LBL_duration);
            list_LBL_teachernotes = itemView.findViewById(R.id.list_LBL_teachernotes);
            list_IMG_add = itemView.findViewById(R.id.list_IMG_add);
            list_IMG_delete = itemView.findViewById(R.id.list_IMG_delete);
            list_IMG_chat = itemView.findViewById(R.id.list_IMG_chat);

            list_IMG_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // pop up a screen asking which note to add
                    Log.d("pttt", "clicked on addNote");
                    initAddNotePopup();
                }
            });

            list_IMG_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // pop up a screen asking which note to delete
                    Log.d("pttt", "clicked on delete");

                }
            });

            list_IMG_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // pop up a screen with the chatbox
                    Log.d("pttt", "clicked on chat");
                }
            });


//            song_LBL_name = itemView.findViewById(R.id.song_LBL_name);
//            song_IMG_image = itemView.findViewById(R.id.song_IMG_image);

        }

            private void initAddNotePopup() {

//                // inflate the layout of the popup window
                LayoutInflater inflater = (LayoutInflater) systemService;
                View popupView = inflater.inflate(R.layout.popup_addnote, null);
//
                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = 300;
                boolean focusable = true; // lets taps outside the popup also dismiss it

                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//
                // show the popup window
                popupWindow.showAtLocation(parentView, Gravity.CENTER, 0, 0);

                EditText pop_ET_note = popupView.findViewById(R.id.pop_ET_note);
                ImageButton pop_BTN_add = popupView.findViewById(R.id.pop_BTN_add);
                ImageButton pop_BTN_cancel = popupView.findViewById(R.id.pop_BTN_cancel);

                pop_BTN_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //update view
                        String note = pop_ET_note.getText().toString();
                        int pos = getAdapterPosition();
                        Lesson clickedLesson = aLessons.get(pos);
                        clickedLesson.addNote(note);


                        DatabaseReference ref = FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_LESSON).child(String.valueOf(pos)).child("notes");
                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String result = "";
                                for (DataSnapshot snap:snapshot.getChildren()) {
                                    result += snap.getValue(String.class) + ", " ;
                                }
//                                Log.d("pttt", snapshot.toString());
//                                ArrayList<String> notes = snapshot.getValue(ArrayList.class);
                                list_LBL_teachernotes.setText(result);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

//                        FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_TEACHER).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                list_LBL_teachernotes.setText(clickedLesson.getNotes().toString());
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });

                        //update firebase
                        FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_LESSON).child(String.valueOf(pos)).child("notes").setValue(clickedLesson.getNotes());
                        FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_TEACHER).child(currentUser.getUid()).child("Lessons").child(String.valueOf(pos)).child("notes").setValue(clickedLesson.getNotes());
                    }
                });

                pop_BTN_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
            }
    }
}
