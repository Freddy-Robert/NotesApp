package com.app.notes.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.app.notes.model.Note;
import com.app.notes.repository.NoteRepository;

/**
 * ViewModel of the app
 */
public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    public LiveData<PagedList<Note>> noteList;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        noteList = new LivePagedListBuilder<>(noteRepository.getDataSourceFactory(), 10).build();
    }

    public void insertNote(Note note) {
        noteRepository.insertNotes(note);
    }

    public void deleteAllNotes(){
        noteRepository.deleteAllNotes();
    }

    public void modifyNote(Note note){
        noteRepository.updateNote(note);
    }

    public LiveData<Note> getNoteById(int id){
        return noteRepository.getNoteById(id);
    }

    public void deleteOneNote(Note note){
        noteRepository.deleteOneNote(note.getId());
    }

}
