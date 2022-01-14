package com.app.notes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.app.notes.R;
import com.app.notes.adapters.NoteAdapter;
import com.app.notes.model.Note;
import com.app.notes.viewmodel.NoteViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class EditNoteActivity extends AppCompatActivity {

    NoteViewModel noteViewModel;
    TextInputEditText text_input_title, text_input_description;
    NumberPicker numberPicker;
    public int ID_NOTE_SELECTED = 0;

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

        //Retrieve the ID of the Note selected
        ID_NOTE_SELECTED = getIntent().getIntExtra(NoteAdapter.ITEM_SELECTED, 0);

        //ViewModel observe ID of note selected and populate fields in UI
        noteViewModel.getNoteById(ID_NOTE_SELECTED).observe(this, this::setNoteData);

    }

    private void setNoteData(Note note) {
        text_input_title.setText(note.getName());
        text_input_description.setText(note.getDescription());
        numberPicker.setValue(note.getImportance());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_note_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //If save icon on menu is selected save data from the fields
        if (item.getItemId() == R.id.option_menu_save){

            Note note = getNoteData();

            if(note == null){
                return false;
            }

            noteViewModel.modifyNote(note);

            Toast.makeText(EditNoteActivity.this, getString(R.string.toast_note_modified_successfully), Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private Note getNoteData() {

        //Retrieve data from fields in UI when save option is selected
        String noteTitle = text_input_title.getText().toString();
        String noteDescription = text_input_description.getText().toString();
        int valuePriority = numberPicker.getValue();

        if(noteTitle.isEmpty() | noteDescription.isEmpty()){
            Toast.makeText(EditNoteActivity.this, getString(R.string.toast_must_fill_all_fields), Toast.LENGTH_SHORT).show();
            return null;
        }

        return new Note(ID_NOTE_SELECTED, noteTitle, noteDescription, valuePriority, false);
    }

}