package com.originalstocks.jetpackroom2;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    private String mNote;

    @NonNull
    private String heading;

    public Note(@NonNull String id, @NonNull String mNote, @NonNull String heading) {
        this.id = id;
        this.mNote = mNote;
        this.heading = heading;
    }

    @NonNull
    public String getHeading() {
        return heading;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getNote() {
        return this.mNote;
    }
}

