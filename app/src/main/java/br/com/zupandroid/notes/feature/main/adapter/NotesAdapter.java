package br.com.zupandroid.notes.feature.main.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.zupandroid.notes.R;
import br.com.zupandroid.notes.feature.createnote.activity.CreateNoteActivity;
import br.com.zupandroid.notes.feature.main.model.NoteEntity;
import br.com.zupandroid.notes.feature.main.model.Repository;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    private List<NoteEntity> content;

    public NotesAdapter(List<NoteEntity> content) {
        this.content = content;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(position);
    }

    public void setContent(List<NoteEntity> content) {
        this.content = content;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textView;
        private AppCompatImageButton imgButton;
        private AppCompatImageButton editButton;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_note_text);
            imgButton = itemView.findViewById(R.id.item_note_delete);
            editButton = itemView.findViewById(R.id.editButtonText);

        }

        void bind(final int position) {
            textView.setText(content.get(position).text);
            imgButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Repository.deleteNote(content.get(position).id);

                }
            });
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), CreateNoteActivity.class);
                    intent.putExtra("NoteEntity", content.get(position));
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}