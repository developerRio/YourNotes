package com.originalstocks.jetpackroom2;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class MainActivity extends AppCompatActivity implements NotesRecyclerAdapter.OnDeleteClickListener {

    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private BottomAppBar mBottomAppBar;
    private FloatingActionButton fabButton;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabButton = findViewById(R.id.fab_button);
        recyclerView = findViewById(R.id.recycler_notes);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        mBottomAppBar = findViewById(R.id.bottom_app_bar);
        coordinatorLayout = findViewById(R.id.coordinator_layout);

        mBottomAppBar.replaceMenu(R.menu.main_menu);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(addIntent, NEW_NOTE_ACTIVITY_REQUEST_CODE);

            }
        });
        final NotesRecyclerAdapter notesRecyclerAdapter = new NotesRecyclerAdapter(this, this);

        noteViewModel.getmAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                notesRecyclerAdapter.setmNotes(notes);
            }
        });

        recyclerView.setHasFixedSize(true);

        // Staggered Layout
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        //recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(notesRecyclerAdapter);

        // hiding fab button on scrolling
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fabButton.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fabButton.isShown())
                    fabButton.hide();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        mBottomAppBar.setNavigationIcon(null);
        // add bounce animation to fab
        fabButton.post(new Runnable() {
            @Override
            public void run() {
                circularRevealEffect(fabButton);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE) {
            // Add new Note
            final String note_id = UUID.randomUUID().toString();
            Note note = new Note(note_id, data.getStringExtra(AddActivity.NOTE_ADDED), data.getStringExtra("heading"));
            noteViewModel.insert(note);
            Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show();

        } else if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            // Update Note
            Note note = new Note(data.getStringExtra("NOTE_ID"), data.getStringExtra("UPDATED_NOTE"), data.getStringExtra("UPDATED_HEADING"));
            noteViewModel.updateNote(note);
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void OnDeleteClickListener(Note myNote) {
        // Delete ops
        noteViewModel.deleteNote(myNote);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                // show dialog & delete all notes if yes
        }

        return true;
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
