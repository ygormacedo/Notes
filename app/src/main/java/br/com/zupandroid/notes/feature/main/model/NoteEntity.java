package br.com.zupandroid.notes.feature.main.model;

import java.io.Serializable;

public class NoteEntity implements Serializable {

    public String id;
    public String text;

    public NoteEntity(String id, String text) {
        this.id = id;
        this.text = text;
    }
}
