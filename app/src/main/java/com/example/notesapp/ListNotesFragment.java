package com.example.notesapp;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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

                tv.setOnLongClickListener(v -> {
                    Activity activity = requireActivity();
                    PopupMenu popupMenu = new PopupMenu(activity, v);
                    activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());

                    popupMenu.setOnMenuItemClickListener(item -> {
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.popup_item_del:
                                Toast.makeText(getContext(), "Удаление заметки", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.popup_item_arh:
                                Toast.makeText(getContext(), "Перемещение в архив", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return true;
                    });
                    popupMenu.show();
                    return false;
                });

                tv.setOnClickListener(v -> {
                    showNote(note);
                });

                ((LinearLayout) view).addView(tv);
            }
        }
    }

    private void showNote(Note note) {
        NoteFragment noteFragment = NoteFragment.newInstance(note);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();

        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT) {
            fragmentTransaction.replace(R.id.maincontainer,noteFragment);
            fragmentTransaction.addToBackStack(null);
        }else {
            fragmentTransaction.replace(R.id.note_container,noteFragment);
        }

        fragmentTransaction.commit();
    }

    public static ListNotesFragment newInstance(Notes notes) {
        ListNotesFragment fragment = new ListNotesFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_LIST_NOTES,notes);
        fragment.setArguments(args);
        return fragment;
    }

}