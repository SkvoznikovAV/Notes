package com.example.notesapp.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.notesapp.MainActivity;
import com.example.notesapp.R;
import com.example.notesapp.data.Note;
import com.example.notesapp.data.Notes;
import com.example.notesapp.data.NotesFirebase;
import com.example.notesapp.data.NotesSource;
import com.example.notesapp.data.NotesSourceResponse;
import com.example.notesapp.observe.Observer;
import com.example.notesapp.observe.Publisher;

import java.io.Serializable;

public class ListNotesFragment extends Fragment implements Serializable {
    private NotesSource notes;
    private Publisher publisher;
    private ListNotesAdapter adapter;
    private RecyclerView recyclerView;
    private static final String NOTES="NOTES";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_notes, container, false);

        initRecyclerView(view);
        notes = new NotesFirebase().init(new NotesSourceResponse() {
            @Override
            public void initialized(NotesSource notes) {
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setDataSource(notes);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    private void initRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.list_notes_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new ListNotesAdapter();
        recyclerView.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(),
                LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        recyclerView.addItemDecoration(itemDecoration);

        adapter.setOnItemClickListener((view1, position) -> {
            showNote(position);
        });

        adapter.setOnItemLongClickListener((view1, position) -> {
            showNoteMenu(view1,position);
        });
    }

    private void showNoteMenu(View v,int position) {
        Activity activity = requireActivity();
        PopupMenu popupMenu = new PopupMenu(activity, v);
        activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.popup_item_del:
                    notes.deleteNote(position);
                    adapter.notifyItemRemoved(position);
                    return true;
                case R.id.popup_item_change:
                    showNote(position);
                    return true;
            }
            return true;
        });
        popupMenu.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Обработка выбора пункта меню приложения (активити)
        int id = item.getItemId();

        switch(id){
            case R.id.action_add:
                publisher.subscribe(new Observer() {
                    @Override
                    public void updateNoteData(Note note) {
                        notes.addNote(note);
                        adapter.notifyItemInserted(notes.getSize() - 1);
                        recyclerView.scrollToPosition(notes.getSize() - 1);
                    }
                });
                showNoteFragment(new Note());
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showNote(int position) {
        Note note = notes.getNote(position);

        publisher.subscribe(new Observer() {
            @Override
            public void updateNoteData(Note note) {
                notes.updateNoteData(position, note);
                adapter.notifyItemChanged(position);
            }
        });

        showNoteFragment(note);
    }

    private void showNoteFragment(Note note) {
        NoteFragment noteFragment = NoteFragment.newInstance(note);
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.maincontainer,noteFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public static ListNotesFragment newInstance() {
        ListNotesFragment fragment = new ListNotesFragment();
        return fragment;
    }

}