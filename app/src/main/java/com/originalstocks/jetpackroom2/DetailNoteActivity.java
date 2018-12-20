package com.originalstocks.jetpackroom2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetailNoteActivity extends AppCompatActivity {

    private TextView headingText, NotesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_note);

        headingText = findViewById(R.id.textViewHeadingDetailed);
        NotesText = findViewById(R.id.textViewNoteDetailed);

        Intent intent = getIntent();
        String mHeading = intent.getStringExtra("detailedHeading");
        String mNotes = intent.getStringExtra("detailedNote");

        headingText.setText(mHeading);
        NotesText.setText(mNotes);



    }
}
