package br.com.zupandroid.notes.feature.createnote.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.zupandroid.notes.feature.main.model.NoteEntity;

public class Repository {

    private Repository() {
    }

    public static void updateNote(NoteEntity noteEntity){
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(uid).child("notes").child(noteEntity.id).setValue(noteEntity.text);
    }

    public static void createNote(final String noteToBeSaved) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return;

        String uid = user.getUid();
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child(uid).child("notes");

        ref.push().setValue(noteToBeSaved);
    }
}
