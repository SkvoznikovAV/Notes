package com.example.notesapp.observe;

import com.example.notesapp.data.Note;

import java.util.ArrayList;
import java.util.List;

public class Publisher {
    private final List<Observer> observers;

    public Publisher(){
        observers = new ArrayList<>();
    }

    public void subscribe(Observer observer){
        observers.add(observer);
    }

    public void unsubscribe(Observer observer){
        observers.remove(observer);
    }

    public void notifySingle(Note note){
        if (note==null) return;

        for (Observer observer : observers){
            observer.updateNoteData(note);
            unsubscribe(observer);
        }
    }
}
