package com.example.notesapp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Note implements Serializable {
    private String dateCreate;
    private String name;
    private String description;

    public Note(String name, String description,String dateCreate) {
        this.dateCreate = dateCreate;
        this.name = name;
        this.description = description;
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

    @Override
    public String toString() {
        return dateCreate+"\n"+name+"\n"+description;
    }
}
