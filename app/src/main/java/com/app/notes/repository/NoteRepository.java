package com.app.notes.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;

import com.app.notes.model.Note;
import com.app.notes.data.NoteDao;
import com.app.notes.data.NoteDatabase;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Repository module for handling data operations.
 */
public class NoteRepository {

    private NoteDatabase noteDatabase;
    private NoteDao noteDao;
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    public NoteRepository(Application application){
        noteDatabase = NoteDatabase.getINSTANCE(application);
        noteDao = noteDatabase.getNoteDao();
    }

    public DataSource.Factory<Integer, Note> getDataSourceFactory(){
        return noteDao.getDataSourceFactory();
    }

    public void insertNotes(Note... notes){
        executorService.execute(() -> {
            noteDao.insertAll(notes);
        });
    }

    public void deleteAllNotes(){
        executorService.execute(() -> {
            noteDao.deleteAll();
        });
    }

    public void updateNote(Note note){
        executorService.execute(() -> {
            noteDao.updateNote(note.getId(), note.getName(), note.getDescription(), note.getImportance(), note.isCompleted());
        });
    }

    public LiveData<Note> getNoteById(int id) {
        try {
            return executorService.submit(new Callable<LiveData<Note>>() {
                @Override
                public LiveData<Note> call() throws Exception {
                    return noteDao.getNoteById(id);
                }
            }).get();
        } catch (ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public void deleteOneNote(int id){
        executorService.execute(() -> {
            noteDao.deleteOneNote(id);
        });
    }

}
