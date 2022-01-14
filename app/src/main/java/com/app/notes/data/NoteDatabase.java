package com.app.notes.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.app.notes.R;
import com.app.notes.model.Note;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The Room database for this app
 */
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase noteDatabaseInstance;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //Create database instance
    public static synchronized NoteDatabase getINSTANCE(Application application){
        if(noteDatabaseInstance == null){
            noteDatabaseInstance = Room.databaseBuilder(application.getApplicationContext(),
                     NoteDatabase.class, "database_person")
                    .addCallback(sRoomDatabaseCallback)
                    .build();
        }
        return noteDatabaseInstance;
    }

    public abstract NoteDao getNoteDao();


    //Callback for pre-populate database when created
    private final static RoomDatabase.Callback sRoomDatabaseCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {

                //Prepopulate the database with instruccions detecting the System's language
                if(Locale.getDefault().getLanguage().equals("en")){
                    Note note1 = new Note("The app is in Spanish and English", "Write your description here", 4, true);
                    Note note2 = new Note("Swipe to left or right to delete", "If you need to delete a task swipe to left or right", 5, false);
                    Note note3 = new Note("Touch here to edit your task", "In this space you can write all that you need remember", 6, false);
                    noteDatabaseInstance.getNoteDao().insertAll(note1, note2, note3);
                } else{
                    Note note1 = new Note("La aplicación esta en dos idiomas", "Aqui escribes tus apuntes", 1, false);
                    Note note2 = new Note("Desliza hacia los lados para borrar", "Si deseas borrar una tarea solo desliza hacia la derecha o izquierda", 2, true);
                    Note note3 = new Note("Toca aquí para editar tu tarea", "En este espacio puedes escribir lo que necesites", 3, false);
                    noteDatabaseInstance.getNoteDao().insertAll(note1, note2, note3);
                }

            });

        }
    };

}
