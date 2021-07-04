package com.example.notesapp.data;

import android.annotation.SuppressLint;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Note implements Serializable {
    private String id;
    private final String dateCreate;
    private final String name;
    private final String description;

    public Note(String name, String description,String dateCreate) {
        this.dateCreate = dateCreate;
        this.name = name;
        this.description = description;
    }

    @SuppressLint("SimpleDateFormat")
    public Note() {
        this.dateCreate = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        this.name = "";
        this.description = "";
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return dateCreate+"\n"+name+"\n"+description;
    }
}
