package com.example.notesapp.data;

import com.example.notesapp.data.Note;

import java.io.Serializable;
import java.util.ArrayList;

public class Notes implements Serializable {
    private ArrayList<Note> list;

    public Notes() {
        this.list = new ArrayList<>();
    }

    public void add(Note note){
        list.add(note);
    }

    public ArrayList<Note> get(){
        return list;
    }

    public Note getNote(int position){
        return list.get(position);
    }

    public int getSize() {
        return list.size();
    }

    public void init() {
        this.add(new Note("Заметка 1","Описание заметки 1","01.06.2020"));
        this.add(new Note("Заметка 2","Описание заметки 2","31.05.2018"));
        this.add(new Note("Заметка 3","Описание заметки 3","25.05.2017"));
        this.add(new Note("Заметка 4","Описание заметки 4","17.04.2021"));
        this.add(new Note("Заметка 5","Описание заметки 5","20.06.2021"));
        this.add(new Note("Заметка 6","Описание заметки 6","15.06.2016"));
        this.add(new Note("Заметка 7","Описание заметки 7","10.06.2021"));
        this.add(new Note("Заметка 8","Описание заметки 8","15.06.2018"));
        this.add(new Note("Заметка 9","Описание заметки 9","28.02.2020"));
        this.add(new Note("Заметка 10","Описание заметки 10","20.04.2015"));
        this.add(new Note("Заметка 11","Описание заметки 11","05.12.2019"));
        this.add(new Note("Заметка 12","Описание заметки 12","07.08.2020"));
    }

    public void updateNoteData(int position, Note note) {
        list.set(position, note);
    }

    public void deleteNote(int position){
        list.remove(position);
    }

    public void addNote(Note note) {
        list.add(note);
    }
}
