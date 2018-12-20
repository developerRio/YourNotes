package com.originalstocks.jetpackroom2;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class EditActivity extends AppCompatActivity {

    private TextInputEditText updateEditText, updateHeadingEditText;
    private EditNoteViewModel editNoteViewModel;
    private Bundle bundle;
    private String noteId;
    private LiveData<Note> noteLiveData;
    private BottomAppBar mBottomAppBar;
    private FloatingActionButton fabButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        updateHeadingEditText = findViewById(R.id.editTextHeadingForEdit);
        updateEditText = findViewById(R.id.updateEditText);
        mBottomAppBar = findViewById(R.id.bottom_app_bar2);
        fabButton = findViewById(R.id.fab_button2);

        fabButton.post(new Runnable() {
            @Override
            public void run() {
                circularRevealEffect(fabButton);
            }
        });

        bundle = getIntent().getExtras();
        if (bundle != null){
            noteId = bundle.getString("note_id");
        }

        editNoteViewModel = ViewModelProviders.of(this).get(EditNoteViewModel.class);
        noteLiveData = editNoteViewModel.getNote(noteId);

        noteLiveData.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                updateEditText.setText(note.getNote());
                updateHeadingEditText.setText(note.getHeading());
            }
        });

        fabButton.setImageDrawable(getDrawable(R.drawable.ic_check_black_24dp));
        mBottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedNote = updateEditText.getText().toString();
                String updatedHeading = updateHeadingEditText.getText().toString();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("NOTE_ID", noteId);
                resultIntent.putExtra("UPDATED_HEADING", updatedHeading);
                resultIntent.putExtra("UPDATED_NOTE", updatedNote);
                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });

    }

    public void circularRevealEffect(View view) {
        int cx = view.getMeasuredWidth() / 2;
        int cy = view.getMeasuredHeight() / 2;

        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        Animator animator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        animator.setDuration(1000);
        animator.setInterpolator(new AccelerateInterpolator());
        view.setVisibility(View.VISIBLE);
        animator.start();
    }
}
