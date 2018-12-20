package com.originalstocks.jetpackroom2;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    public static final String NOTE_ADDED = "NOTE_ADDED";
    private TextInputEditText inputEditText, headingEditText;
    private BottomAppBar mBottomAppBar;
    private FloatingActionButton fabButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        headingEditText = findViewById(R.id.editTextHeading);
        inputEditText = findViewById(R.id.editText);
        fabButton = findViewById(R.id.add_note_button);
        mBottomAppBar = findViewById(R.id.bottom_app_bar);

        mBottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        fabButton.setImageDrawable(getDrawable(R.drawable.ic_check_black_24dp));

        fabButton.post(new Runnable() {
            @Override
            public void run() {
                circularRevealEffect(fabButton);
            }
        });

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();

                if (TextUtils.isEmpty(inputEditText.getText())) {
                    setResult(RESULT_CANCELED, resultIntent);
                } else {
                    String noteHeading = headingEditText.getText().toString();
                    String note = inputEditText.getText().toString();
                    resultIntent.putExtra(NOTE_ADDED, note);
                    resultIntent.putExtra("heading", noteHeading);
                    setResult(RESULT_OK, resultIntent);
                }
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
