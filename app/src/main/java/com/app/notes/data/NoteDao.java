package com.app.notes.data;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.app.notes.model.Note;

/**
 * The Data Access Object for Note Class
 */
@Dao
public interface NoteDao {

    @Query("SELECT * FROM Note ORDER BY importance ASC")
    DataSource.Factory<Integer, Note> getDataSourceFactory();

    @Insert
    void insertAll(Note... notes);

    @Query("DELETE FROM Note")
    void deleteAll();

    @Query("UPDATE Note SET name = :name, description = :description, importance = :importance, isCompleted = :isCompleted WHERE id = :id" )
    void updateNote(int id, String name, String description, int importance, boolean isCompleted);

    @Query("SELECT * FROM Note WHERE id = :id")
    LiveData<Note> getNoteById(int id);

    @Query("DELETE FROM Note WHERE id = :id")
    void deleteOneNote(int id);

}
