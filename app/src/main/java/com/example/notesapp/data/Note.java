package com.example.notesapp.data;

import java.io.Serializable;

public class Note implements Serializable {
    private final String dateCreate;
    private final String name;
    private final String description;

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
