package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Notes notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initNotes();

        setContentView(R.layout.activity_main);

        int inContainer=R.id.maincontainer;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            inContainer=R.id.list_notes_container;
        }

        ListNotesFragment listNotesFragment = ListNotesFragment.newInstance(notes);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(inContainer, listNotesFragment)
                .commit();
    }

    private void initNotes() {
        notes = new Notes();
        notes.add(new Note("Заметка 1","Описание заметки 1","01.06.21 15:00"));
        notes.add(new Note("Заметка 2","Описание заметки 2","31.05.21 14:15"));
        notes.add(new Note("Заметка 3","Описание заметки 3","25.05.21 14:00"));
        notes.add(new Note("Заметка 4","Описание заметки 4","15.06.21 13:00"));
        notes.add(new Note("Заметка 5","Описание заметки 5","20.06.21 14:00"));
        notes.add(new Note("Заметка 6","Описание заметки 6","15.06.21 15:00"));
        notes.add(new Note("Заметка 7","Описание заметки 7","01.06.21 15:30"));
    }
}