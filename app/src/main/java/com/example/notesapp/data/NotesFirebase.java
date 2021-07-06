package com.example.notesapp.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class NotesFirebase implements Serializable,NotesSource {
    private static final String NOTES_COLLECTION = "NOTES_COLLECTION";
    private static final String TAG = "[NotesFirebase]";

    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collection = store.collection(NOTES_COLLECTION);

    private ArrayList<Note> list = new ArrayList<>();

    @Override
    public NotesSource init(NotesSourceResponse notesSourceResponse) {
        collection.orderBy(NoteMapping.Fields.NAME, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            list = new ArrayList<>();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                Note note = NoteMapping.toNote(id, doc);
                                list.add(note);
                            }
                            notesSourceResponse.initialized(NotesFirebase.this);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with ", e);
                    }
                });

        return this;
    }

    @Override
    public Note getNote(int position) {
        return list.get(position);
    }

    @Override
    public int getSize() {
        if (list == null){
            return 0;
        }
        return list.size();
    }

    @Override
    public void deleteNote(int position) {
        collection.document(list.get(position).getId()).delete();
        list.remove(position);
    }

    @Override
    public void updateNoteData(int position, Note note) {
        String id = note.getId();
        collection.document(id).set(NoteMapping.toDocument(note));

        if (list.size()!=0) {
            list.set(position, note);
        }
    }

    @Override
    public void addNote(Note note) {
        collection.add(NoteMapping.toDocument(note))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>(){
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        note.setId(documentReference.getId());
                    }
                });
        list.add(note);
    }
}
