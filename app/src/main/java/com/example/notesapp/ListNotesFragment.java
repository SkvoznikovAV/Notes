package com.example.notesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListNotesFragment extends Fragment {

    private static final String KEY_LIST_NOTES = "KEY_LIST_NOTES";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_notes, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String param1 = getArguments().getString("PARAM1");
            Log.d("123", "onCreate: "+param1);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            Notes notes  = (Notes) getArguments().getSerializable(KEY_LIST_NOTES);

            ArrayList<Note> listNotes = notes.get();

            for (Note note: listNotes) {
                TextView tv = new TextView(getContext());
                tv.setText(note.getName());
                tv.setTextSize(30);

                tv.setOnClickListener(v -> {

                    Toast.makeText(getContext(),note.toString(),Toast.LENGTH_SHORT).show();
                });

                ((LinearLayout) view).addView(tv);

            }
        }
    }

    public static ListNotesFragment newInstance(Notes notes) {
        ListNotesFragment fragment = new ListNotesFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_LIST_NOTES,notes);
        fragment.setArguments(args);
        return fragment;
    }

}