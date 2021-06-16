package com.example.notesapp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {
    private Date dateCreate;
    private String name;
    private String description;

    public Note(String name, String description) {
        this.dateCreate = new Date();
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        SimpleDateFormat fmt = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        return fmt.format(dateCreate)+"\n"+name+"\n"+description;
    }
}
