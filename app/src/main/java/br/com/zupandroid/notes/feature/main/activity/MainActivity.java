package br.com.zupandroid.notes.feature.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.zupandroid.notes.R;
import br.com.zupandroid.notes.feature.createnote.activity.CreateNoteActivity;
import br.com.zupandroid.notes.feature.main.adapter.NotesAdapter;
import br.com.zupandroid.notes.feature.main.model.NoteEntity;
import br.com.zupandroid.notes.feature.main.model.Repository;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCenter.start(getApplication(), "77e75d30-2b5b-4a27-8417-73fc4c7efe2c",
                Analytics.class, Crashes.class);


        // SE O USUARIO NAO ESTA LOGADO
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            List<AuthUI.IdpConfig> providers = Collections.singletonList(
                    new AuthUI.IdpConfig.EmailBuilder().build());

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        } else {
            // JA LOGOU ANTES
            montarTela();
        }
    }

    private void montarTela() {
        setRecyclerView();
        setClickUser();
    }

    private void setRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_item);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final NotesAdapter adapter = new NotesAdapter(new ArrayList<NoteEntity>());
        recyclerView.setAdapter(adapter);

        Repository.getNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                if (noteEntities == null) return;

                adapter.setContent(noteEntities);
            }
        });
    }

    private void setClickUser() {
        FloatingActionButton clickUser = findViewById(R.id.createUser);
        clickUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                montarTela();
            } else {
                List<AuthUI.IdpConfig> providers = Collections.singletonList(
                        new AuthUI.IdpConfig.EmailBuilder().build());

                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(providers)
                                .build(),
                        RC_SIGN_IN);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
