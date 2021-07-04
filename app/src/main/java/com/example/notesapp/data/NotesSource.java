package com.example.notesapp.data;

public interface NotesSource {
    NotesSource init(NotesSourceResponse notesSourceResponse);
    Note getNote(int position);
    int getSize();
    void deleteNote(int position);
    void updateNoteData(int position, Note note);
    void addNote(Note note);
}
