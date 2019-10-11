package br.com.zupandroid.notes.feature.createnote.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.zupandroid.notes.R;
import br.com.zupandroid.notes.feature.createnote.model.Repository;
import br.com.zupandroid.notes.feature.main.model.NoteEntity;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText editText;
    private NoteEntity noteEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            noteEntity = (NoteEntity) extras.getSerializable("NoteEntity");
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_24px);
        }
        editText = findViewById(R.id.createText);
        DatabaseReference referenciaFirebase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (noteEntity != null) {
            editText.setText(noteEntity.text);
            editText.setSelection(editText.length());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_create, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.createContact) {
            if (noteEntity == null) {
                Repository.createNote(editText.getText().toString());
            } else {
                noteEntity.text = editText.getText().toString();
                Repository.updateNote(noteEntity);
            }
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
