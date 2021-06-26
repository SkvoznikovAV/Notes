package com.example.notesapp;

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
}
