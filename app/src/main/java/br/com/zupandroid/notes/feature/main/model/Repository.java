package br.com.zupandroid.notes.feature.main.model;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    private Repository() {
    }

    public static void deleteNote(String id) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(uid).child("notes").child(id).removeValue();
    }

    public static LiveData<List<NoteEntity>> getNotes() {
        final MutableLiveData<List<NoteEntity>> output = new MutableLiveData<>();

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child(uid).child("notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<NoteEntity> listText = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    listText.add(
                            new NoteEntity(
                                    child.getKey(),
                                    (String) child.getValue()
                            )
                    );
                }

                output.postValue(listText);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return output;
    }
}
