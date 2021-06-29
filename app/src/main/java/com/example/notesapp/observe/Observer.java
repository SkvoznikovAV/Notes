package com.example.notesapp.observe;

import com.example.notesapp.data.Note;

public interface Observer {
    void updateNoteData(Note note);
}
