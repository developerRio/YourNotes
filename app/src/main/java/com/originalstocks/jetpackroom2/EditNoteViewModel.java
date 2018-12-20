package com.originalstocks.jetpackroom2;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class EditNoteViewModel extends AndroidViewModel {

    private NoteDao noteDao;
    private NoteRoomDataBase roomDataBase;

    public EditNoteViewModel(@NonNull Application application) {
        super(application);

        roomDataBase = NoteRoomDataBase.getDatabase(application);
        noteDao = roomDataBase.noteDao();
    }

    public LiveData<Note> getNote(String noteId){
        return noteDao.getNote(noteId);
    }

    public LiveData<Note> getHeading(String noteIds){
        return noteDao.getHeading(noteIds);
    }
}
