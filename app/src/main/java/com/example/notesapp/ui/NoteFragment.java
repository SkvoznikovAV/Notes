package com.example.notesapp.ui;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.notesapp.MainActivity;
import com.example.notesapp.R;
import com.example.notesapp.data.Note;
import com.example.notesapp.observe.Publisher;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NoteFragment extends Fragment {
    private static final String KEY_NOTE = "KEY_NOTE";

    private Note note;
    private Publisher publisher;
    private TextView dateNoteView;
    private TextView titleNoteView;
    private TextView descriptionNoteView;


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
        if (titleNoteView!=null & descriptionNoteView!=null & dateNoteView!=null) {
            return new Note(titleNoteView.getText().toString(), descriptionNoteView.getText().toString(), dateNoteView.getText().toString());
        } else {
            return null;
        }
    }

    private Spanned getUnderLineText(String txt) {
        String htmlTaggedString  = "<u>"+txt+"</u>";
        Spanned textSpan = android.text.Html.fromHtml(htmlTaggedString);
        return textSpan;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dateNoteView = view.findViewById(R.id.date_note);
        titleNoteView = view.findViewById(R.id.header_note);
        descriptionNoteView = view.findViewById(R.id.description_note);

        if (note != null) {
            dateNoteView.setText(getUnderLineText(note.getDateCreate()));
            titleNoteView.setText(note.getName());
            descriptionNoteView.setText(note.getDescription());

            view.findViewById(R.id.date_note).setOnClickListener(v -> {
                changeDateNote();
            });

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                view.findViewById(R.id.btn_note_ok).setOnClickListener(v -> {
                    requireActivity().onBackPressed();
                });
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(getChangedNote());
    }

    private void changeDateNote() {
        Calendar dateAndTime=Calendar.getInstance();

        String[] arrDate = note.getDateCreate().split("\\.");

        dateAndTime.set(Calendar.YEAR, Integer.parseInt(arrDate[2]));
        dateAndTime.set(Calendar.MONTH,Integer.parseInt(arrDate[1])-1);
        dateAndTime.set(Calendar.DAY_OF_MONTH,Integer.parseInt(arrDate[0]));

        DatePickerDialog.OnDateSetListener d = (view1, year, monthOfYear, dayOfMonth) -> {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            dateNoteView.setText(getUnderLineText(df.format(dateAndTime.getTime())));
        };

        new DatePickerDialog(getContext(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public static NoteFragment newInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_NOTE,note);
        fragment.setArguments(args);
        return fragment;
    }
}
