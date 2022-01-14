package com.app.notes.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;


import com.app.notes.R;
import com.app.notes.model.Note;
import com.app.notes.ui.NoteViewHolder;

public class NoteAdapter extends PagedListAdapter<Note, NoteViewHolder> {

    public static String ITEM_SELECTED;
    private OnItemClickListener onItemClickListener;
    private OnCheckedNoteListener onCheckedNoteListener;

    public NoteAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view, onItemClickListener, onCheckedNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = getItem(position);

        if (note != null) {
            holder.bindTO(note);
        }
    }

    public static DiffUtil.ItemCallback<Note> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Note>() {
                @Override
                public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public Note getNoteAt(int position){
        return getItem(position);
    }

    public interface OnItemClickListener{
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnCheckedNoteListener{
        void onChekedNoteListener(Note note, boolean isChecked);
    }

    public void setOnCheckedNoteListener(OnCheckedNoteListener onCheckedNoteListener){
        this.onCheckedNoteListener = onCheckedNoteListener;
    }
}
