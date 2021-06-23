package com.example.notesapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class NoteFragment extends Fragment {
    private static final String KEY_NOTE = "KEY_NOTE";

    private Note note;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            note = (Note) getArguments().getSerializable(KEY_NOTE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (note != null) {
            ((TextView)view.findViewById(R.id.date_note)).setText(note.getDateCreate());
            ((TextView)view.findViewById(R.id.header_note)).setText(note.getName());
            ((TextView)view.findViewById(R.id.description_note)).setText(note.getDescription());

            view.findViewById(R.id.btn_change_date_note).setOnClickListener(v -> {
                setButtonChangeDateListener();
            });
        }
    }

    private void setButtonChangeDateListener() {
        ChangeDateNoteFragment changeDateNoteFragment = ChangeDateNoteFragment.newInstance(note.getDateCreate());
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();

        int inContainer=R.id.maincontainer;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            inContainer=R.id.note_container;
        }

        fragmentTransaction.replace(inContainer,changeDateNoteFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static NoteFragment newInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_NOTE,note);
        fragment.setArguments(args);
        return fragment;
    }
}
