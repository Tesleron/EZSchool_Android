package com.tesleron.ezschool.Adapter;

import static com.tesleron.ezschool.LoginActivity.currentUser;
import static com.tesleron.ezschool.MainActivity.typeOfUser;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DatabaseReference;
import com.tesleron.ezschool.Model.Lesson;
import com.tesleron.ezschool.Model.LessonStorage;
import com.tesleron.ezschool.Model.TypeOfUser;
import com.tesleron.ezschool.MyUtils.Constants;
import com.tesleron.ezschool.MyUtils.FireBaseOperations;
import com.tesleron.ezschool.MyUtils.MySignal;
import com.tesleron.ezschool.MyUtils.MyStringUtils;
import com.tesleron.ezschool.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Adapter_Class extends RecyclerView.Adapter<Adapter_Class.ClassViewHolder> {

    private Context context;
    private ArrayList<Lesson> aLessons;
    private DatabaseReference ref = FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_LESSON);




    public Adapter_Class(Context context) {
        this.context = context;
        aLessons = new ArrayList<>();
    }

    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_list, parent, false);
        ClassViewHolder myClassViewHolder = new ClassViewHolder(view);
        return myClassViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        Lesson aLesson = getItem(position);
        holder.list_LBL_name.setText(aLesson.getName());
        holder.list_LBL_duration.setText(MyStringUtils.getDurationFromation(aLesson.getStartTime()) + "-" + MyStringUtils.getDurationFromation(aLesson.getEndTime()));
        if (aLesson.getNotes().isEmpty())
        {
            holder.list_LBL_teachernotes.setText("Teacher's notes: N/A");
        }
        else
        {
            holder.list_LBL_teachernotes.setText("Teacher's notes: " + aLesson.getNotes().toString());
        }
    }

    private Lesson getItem(int position) {
        return aLessons.get(position);
    }

    @Override
    public int getItemCount() {
        return aLessons == null ? 0 : aLessons.size();
    }

    public void updateLessons(final ArrayList<Lesson> newLessons) {
        aLessons = newLessons;
        //raise flag, notfify for the currently opened adapter, if it is opened
        if (LessonStorage.getInstance().getCurrentOpenAdapter() != null)
        {
            LessonStorage.getInstance().getCurrentOpenAdapter().notifyDataSetChanged();
        }
        notifyDataSetChanged();
    }

    class ClassViewHolder extends RecyclerView.ViewHolder {

        private final MaterialTextView list_LBL_name;
        private final MaterialTextView list_LBL_duration;
        private final MaterialTextView list_LBL_teachernotes;
        private final ImageButton list_IMG_add;
        private final MaterialTextView list_LBL_addnote;
        private final ImageButton list_IMG_delete;
        private final MaterialTextView list_LBL_deletenote;
        private final ImageButton list_IMG_chat;

        private int pos;
        private DatabaseReference lessonReference = FireBaseOperations.getInstance().getDatabaseReference(Constants.KEY_LESSON);

        public ClassViewHolder(View itemView) {
            super(itemView);
            list_LBL_name = itemView.findViewById(R.id.list_LBL_name);
            list_LBL_duration = itemView.findViewById(R.id.list_LBL_duration);
            list_LBL_teachernotes = itemView.findViewById(R.id.list_LBL_teachernotes);
            list_IMG_add = itemView.findViewById(R.id.list_IMG_add);
            list_LBL_addnote = itemView.findViewById(R.id.list_LBL_addnote);
            list_IMG_delete = itemView.findViewById(R.id.list_IMG_delete);
            list_LBL_deletenote = itemView.findViewById(R.id.list_LBL_deletenote);
            list_IMG_chat = itemView.findViewById(R.id.list_IMG_chat);

            final boolean isStudent = typeOfUser == TypeOfUser.STUDENT ? true : false;
            if (isStudent) { // if is a student, remove options to edit things
                list_IMG_add.setVisibility(View.INVISIBLE);
                list_LBL_addnote.setVisibility(View.INVISIBLE);
                list_IMG_delete.setVisibility(View.INVISIBLE);
                list_LBL_deletenote.setVisibility(View.INVISIBLE);
            }

            list_IMG_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // pop up a screen asking which note to add
                    initAddNotePopup();
                }
            });

            list_IMG_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // pop up a screen asking which note to delete
                    initDeleteNotePopup();
                }
            });

            list_IMG_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // pop up a screen with the chatbox
                    initChatPopup();
                }
            });
        }

        private void initAddNotePopup() {

            // open the popup
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.popup_addnote);
            dialog.setTitle("Delete Note");
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();
            EditText pop_ET_note = dialog.findViewById(R.id.pop_ET_note);
            ImageButton pop_BTN_add = dialog.findViewById(R.id.pop_BTN_add);
            ImageButton pop_BTN_cancel = dialog.findViewById(R.id.pop_BTN_cancel);
            pop_BTN_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //update view
                    String note = pop_ET_note.getText().toString();
                    pos = getAdapterPosition();
                    Lesson clickedLesson = aLessons.get(pos);
                    clickedLesson.addNote(note);
                    dialog.dismiss();

                    MySignal.getInstance().toast("Successfully added note");

                    //update firebase
                    FireBaseOperations.getInstance()
                            .getDatabaseReference(Constants.KEY_LESSON)
                            .child(String.valueOf(pos))
                            .child("notes")
                            .setValue(clickedLesson.getNotes());
                }
            });

                pop_BTN_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

        private void initDeleteNotePopup() {
            pos = getAdapterPosition();
            Lesson clickedLesson = aLessons.get(pos);
            ArrayList<String> currentNotes = clickedLesson.getNotes(); // load from DB instead

            // open the popup
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.popup_deletenote);
            dialog.setTitle("Delete Note");
            ListView notesLv = (ListView) dialog.findViewById(R.id.pop_LST_notes);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.row_in_listview, currentNotes);
            notesLv.setAdapter(adapter);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();

            notesLv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String chosenNote = currentNotes.get(position);
                    currentNotes.remove(chosenNote);

                    MySignal.getInstance().toast("Successfully deleted note");

                    // update FB on whats happening
                    FireBaseOperations.getInstance()
                            .getDatabaseReference(Constants.KEY_LESSON)
                            .child(String.valueOf(pos))
                            .child("notes")
                            .setValue(clickedLesson.getNotes());

                    notesLv.requestLayout();
                }
            });

        }

        private void initChatPopup() {
            pos = getAdapterPosition();
            Lesson clickedLesson = aLessons.get(pos);
            ArrayList<String> currentMsgs = clickedLesson.getChatMsgs();
            // open the popup
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.popup_chatroom);
            dialog.setTitle("Chat Room");
            //ListView messagesLv = (ListView) dialog.findViewById(R.id.pop_CHT_msgs);
            clickedLesson.setLessonListView(dialog, R.id.pop_CHT_msgs);

            TextView pop_CHT_title = dialog.findViewById(R.id.pop_CHT_title);
            EditText pop_CHT_ET = dialog.findViewById(R.id.pop_CHT_ET);
            ImageButton pop_CHT_send = dialog.findViewById(R.id.pop_CHT_send);

            pop_CHT_title.setText(clickedLesson.getName() + " chat room!");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(dialog.getContext(), R.layout.row_in_listview, currentMsgs);
            LessonStorage.getInstance().setCurrentOpenAdapter(adapter);
            LessonStorage.getInstance().setCurrentIndexOnOpenedLesson(pos);
            //set this adapter to the global variable, and raise the flag

            //messagesLv.setAdapter(adapter);
            clickedLesson.getLessonListView().setAdapter(adapter);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();

            pop_CHT_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();

                    String message = "[" + dtf.format(now) + "] " + currentUser.getDisplayName() + ": " + pop_CHT_ET.getText().toString();
                    clickedLesson.addMessage(message);
                    LessonStorage.getInstance().setCurrentOpenAdapter(new ArrayAdapter<String>(dialog.getContext(), R.layout.row_in_listview, clickedLesson.getChatMsgs()));
                    clickedLesson.getLessonListView().setAdapter(LessonStorage.getInstance().getCurrentOpenAdapter());
                    LessonStorage.getInstance().getCurrentOpenAdapter().notifyDataSetChanged();

                    //currentMsgs.add(message);
                    pop_CHT_ET.getText().clear();


                    //update firebase
                    FireBaseOperations.getInstance()
                            .getDatabaseReference(Constants.KEY_LESSON)
                            .child(String.valueOf(pos))
                            .child(Constants.KEY_CHAT)
                            .setValue(clickedLesson.getChatMsgs());

                    //messagesLv.invalidateViews();
                    clickedLesson.getLessonListView().invalidateViews();
                }
            });

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    LessonStorage.getInstance().setCurrentOpenAdapter(null);
                }
            });
            //add dialog dismiss lsitener to make the current adapter nnull

        }
    }
}
