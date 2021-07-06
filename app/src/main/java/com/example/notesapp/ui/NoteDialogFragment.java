package com.example.notesapp.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.notesapp.MainActivity;
import com.example.notesapp.R;
import com.example.notesapp.data.Note;
import com.example.notesapp.observe.Publisher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NoteDialogFragment extends DialogFragment {
    private TextView dateNoteView;
    private TextView titleNoteView;
    private TextView descriptionNoteView;
    private Note note;
    private Publisher publisher;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments()!=null) {
            note = (Note) getArguments().getSerializable(getResources().getString(R.string.key_note));
        }

        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity)context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    private Note getChangedNote(){
        if (note == null) return null;

        if (titleNoteView!=null & descriptionNoteView!=null & dateNoteView!=null) {
            Note changedNote = new Note(titleNoteView.getText().toString(),
                    descriptionNoteView.getText().toString(),
                    dateNoteView.getText().toString());
            changedNote.setId(note.getId());
            return changedNote;
        } else {
            return null;
        }
    }

    private void changeDateNote() {
        Calendar dateAndTime=Calendar.getInstance();

        String[] arrDate = dateNoteView.getText().toString().split("\\.");

        dateAndTime.set(Calendar.YEAR, Integer.parseInt(arrDate[2]));
        dateAndTime.set(Calendar.MONTH,Integer.parseInt(arrDate[1])-1);
        dateAndTime.set(Calendar.DAY_OF_MONTH,Integer.parseInt(arrDate[0]));

        DatePickerDialog.OnDateSetListener d = (view1, year, monthOfYear, dayOfMonth) -> {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            dateNoteView.setText(df.format(dateAndTime.getTime()));
        };

        new DatePickerDialog(getContext(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    @Override
    public void onDestroy() {
        if (publisher!=null) {
            publisher.notifySingle(getChangedNote());
        }
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_note, null);

        dateNoteView = view.findViewById(R.id.date_note);
        titleNoteView = view.findViewById(R.id.header_note);
        descriptionNoteView = view.findViewById(R.id.description_note);

        if (note != null) {
            dateNoteView.setText(note.getDateCreate());
            titleNoteView.setText(note.getName());
            descriptionNoteView.setText(note.getDescription());

            view.findViewById(R.id.date_note).setOnClickListener(v -> {
                changeDateNote();
            });

            view.findViewById(R.id.btn_note_ok).setOnClickListener(v -> {
                dismiss();
            });
        }

        setCancelable(false);
        return view;
    }


}
