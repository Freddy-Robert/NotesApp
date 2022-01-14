package com.app.notes.ui;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.notes.R;
import com.app.notes.adapters.NoteAdapter;
import com.app.notes.model.Note;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    TextView textview_title, textview_priority;
    CheckBox check_box;
    NoteAdapter.OnItemClickListener onItemClickListener;
    NoteAdapter.OnCheckedNoteListener onCheckedNoteListener;

    public NoteViewHolder(@NonNull View itemView, NoteAdapter.OnItemClickListener onItemClickListener,
                          NoteAdapter.OnCheckedNoteListener onCheckedNoteListener) {
        super(itemView);

        this.textview_title = itemView.findViewById(R.id.textview_title);
        this.textview_priority = itemView.findViewById(R.id.textview_priority);
        this.check_box = itemView.findViewById(R.id.check_box);
        this.onItemClickListener = onItemClickListener;
        this.onCheckedNoteListener = onCheckedNoteListener;
    }

    public void bindTO(Note note){
        textview_title.setText(note.getName());
        textview_priority.setText(String.valueOf(note.getImportance()));
        check_box.setChecked(note.isCompleted());

        itemView.setOnClickListener(v -> onItemClickListener.onItemClick(note));

        check_box.setOnClickListener(v -> onCheckedNoteListener.onChekedNoteListener(note, note.isCompleted()));
    }

}
