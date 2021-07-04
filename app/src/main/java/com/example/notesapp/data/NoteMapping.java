package com.example.notesapp.data;

import java.util.HashMap;
import java.util.Map;

public class NoteMapping {

    public static class Fields{
        public final static String NAME = "name";
        public final static String DESCRIPTION = "desription";
        public final static String DATE = "date";
    }

    public static Map<String, Object> toDocument(Note note){
        Map<String, Object> doc = new HashMap<>();
        doc.put(Fields.NAME, note.getName());
        doc.put(Fields.DESCRIPTION, note.getDescription());
        doc.put(Fields.DATE, note.getDateCreate());

        return doc;
    }

    public static Note toNote(String id, Map<String, Object> doc){
        Note note = new Note((String)doc.get(Fields.NAME),
                (String)doc.get(Fields.DESCRIPTION),
                (String)doc.get(Fields.DATE));
        note.setId(id);
        return note;
    }
}
