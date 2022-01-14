package com.app.notes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.app.notes.R;
import com.app.notes.model.Note;
import com.app.notes.viewmodel.NoteViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class AddNoteActivity extends AppCompatActivity {

    NoteViewModel noteViewModel;
    TextInputEditText text_input_title, text_input_description;
    NumberPicker numberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //Initializing ViewModel
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        //Initializing Views
        text_input_title = findViewById(R.id.text_input_title);
        text_input_description = findViewById(R.id.text_input_description);
        numberPicker = findViewById(R.id.number_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_note_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //When save image is selected retrieve data and insert Note
        if (item.getItemId() == R.id.option_menu_save){

            Note note = getNoteData();

            if(note != null){
                insertNote(note);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private Note getNoteData() {

        //Retrieve data from fields in UI
        String noteTitle = text_input_title.getText().toString();
        String noteDescription = text_input_description.getText().toString();
        int valuePriority = numberPicker.getValue();

        if(noteTitle.isEmpty() | noteDescription.isEmpty()){
            Toast.makeText(AddNoteActivity.this, getString(R.string.toast_must_fill_all_fields), Toast.LENGTH_SHORT).show();
            return null;
        }

        return new Note(noteTitle, noteDescription, valuePriority, false);
    }


    private void insertNote(Note note){

        //Insert Note through ViewModel
        noteViewModel.insertNote(note);
        Toast.makeText(AddNoteActivity.this, getString(R.string.toast_note_created_successfully), Toast.LENGTH_SHORT).show();
        finish();

    }

}