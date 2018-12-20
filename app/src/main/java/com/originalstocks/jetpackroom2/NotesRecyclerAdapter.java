package com.originalstocks.jetpackroom2;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.NotesViewHolder> {

    public interface OnDeleteClickListener{
        void OnDeleteClickListener(Note myNote);
    }
    private Context context;
    private List<Note> mNotes;
    private OnDeleteClickListener deleteClickListener;
    // constructor

    public NotesRecyclerAdapter(Context context, OnDeleteClickListener listener) {
        this.context = context;
        this.deleteClickListener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_list, parent, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NotesViewHolder holder, final int position) {

        if (mNotes != null) {
            Note note = mNotes.get(position);
            holder.setData(note.getNote(), position);
            holder.setHeading(note.getHeading(), position);
            holder.setListeners();
        } else {
            // Covers the case when not being ready yet
            holder.notes_TextView.setText("Please add new note by touching add button at bottom right corner...");
        }

        holder.viewForeground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = mNotes.get(position);
                String mTittle = note.getHeading();
                String mNote = note.getNote();
                Toast.makeText(context, "Heading is : " + mTittle, Toast.LENGTH_SHORT).show();

                int currentPosition = holder.getAdapterPosition();
                final Intent detailedIntent = new Intent(context, DetailNoteActivity.class);
                for (int i = 0; i <= currentPosition; i++) {
                    detailedIntent.putExtra("detailedHeading", mTittle);
                    detailedIntent.putExtra("detailedNote", mNote);
                }
                context.startActivity(detailedIntent);

            }
        });

        // todo : when user long presses the card it shows a dialog to edit or delete the note...
        // todo : or on long press user selects the note/card to delete

    }

    @Override
    public int getItemCount() {
        if (mNotes != null) {
            return mNotes.size();
        } else {
            return 0;
        }

    }

    public void setmNotes(List<Note> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    public class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView notes_TextView, notes_heading;
        int mPosition;
        ImageView editButton, deleteButton;
        RelativeLayout viewForeground;


        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            notes_heading = itemView.findViewById(R.id.note_heading_TextView);
            notes_TextView = itemView.findViewById(R.id.notesTextView);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }

        public void setData(String note, int position) {
            notes_TextView.setText(note);
            mPosition = position;
        }

        public void setListeners() {
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent editIntent = new Intent(context, EditActivity.class);
                    editIntent.putExtra("note_id", mNotes.get(mPosition).getId());
                    ((Activity)context).startActivityForResult(editIntent, MainActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteClickListener != null){
                        deleteClickListener.OnDeleteClickListener(mNotes.get(mPosition));
                    }
                }
            });
        }

        public void setHeading(String heading, int position) {
            notes_heading.setText(heading);
            mPosition = position;
        }
    }
}
