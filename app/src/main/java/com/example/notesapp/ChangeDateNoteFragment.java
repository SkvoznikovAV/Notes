package com.example.notesapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

public class ChangeDateNoteFragment extends Fragment {

    private final static String DATE = "DATE";

    private String date;

    public static ChangeDateNoteFragment newInstance(String date) {
        ChangeDateNoteFragment fragment = new ChangeDateNoteFragment();
        Bundle args = new Bundle();
        args.putString(DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            date = getArguments().getString(DATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_change_date_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatePicker datePicker = view.findViewById(R.id.datePicker);
        String[] arrDate = date.split("\\.");
        datePicker.init(Integer.parseInt(arrDate[2]),Integer.parseInt(arrDate[1])-1,Integer.parseInt(arrDate[0]),null);

        view.findViewById(R.id.btn_ok).setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });
    }
}