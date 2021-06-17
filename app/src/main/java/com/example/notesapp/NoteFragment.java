package com.example.notesapp;

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

import java.util.ArrayList;

public class NoteFragment extends Fragment {
    private static final String KEY_NOTE = "KEY_NOTE";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            Note note = (Note) getArguments().getSerializable(KEY_NOTE);

            ((TextView)view.findViewById(R.id.date_time_note)).setText(note.getDateCreate());
            ((TextView)view.findViewById(R.id.header_note)).setText(note.getName());
            ((TextView)view.findViewById(R.id.description_note)).setText(note.getDescription());
        }
    }

    public static NoteFragment newInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_NOTE,note);
        fragment.setArguments(args);
        return fragment;
    }
}
