package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Notes notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initNotes();

        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            ListNotesFragment listNotesFragment = ListNotesFragment.newInstance(notes);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.listNotes, listNotesFragment);
            transaction.commit();
        }
    }

    private void initNotes() {
        notes = new Notes();
        notes.add(new Note("Заметка 1","Описание заметки 1"));
        notes.add(new Note("Заметка 2","Описание заметки 2"));
        notes.add(new Note("Заметка 3","Описание заметки 3"));
        notes.add(new Note("Заметка 4","Описание заметки 4"));
        notes.add(new Note("Заметка 5","Описание заметки 5"));
        notes.add(new Note("Заметка 6","Описание заметки 6"));
        notes.add(new Note("Заметка 7","Описание заметки 7"));
    }
}