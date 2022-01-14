package com.app.notes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.app.notes.R;
import com.app.notes.adapters.NoteAdapter;
import com.app.notes.model.Note;
import com.app.notes.viewmodel.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;

public class ListNotesActivity extends AppCompatActivity{

    NoteViewModel noteViewModel;
    NoteAdapter noteAdapter;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    public static String SETTINGS_SAVED = "BackgroundColor";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set preferences in the color of background UI
        setUIPreferences();

        //Initializing views
        recyclerView = findViewById(R.id.recycler_view);
        floatingActionButton = findViewById(R.id.floating_action_button);
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteAdapter = new NoteAdapter();

        //Listeners for interacting with the UI
        setUIListeners();

        setRecyclerViewSettings();

        //Observing ViewModel and submit List of Notes
        noteViewModel.noteList.observe(this, notes -> noteAdapter.submitList(notes));
    }

    private void setUIPreferences() {
        //Retrieving preferences and setting root layout color
        String backgroundColor = PreferenceManager.getDefaultSharedPreferences(this).
                getString(SETTINGS_SAVED, String.valueOf(Color.WHITE));
        findViewById(R.id.constraint_layout_id).setBackgroundColor(Integer.parseInt(backgroundColor));
    }

    private void setRecyclerViewSettings() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(noteAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.deleteOneNote(noteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);
    }


    private void setUIListeners() {
        noteAdapter.setOnItemClickListener(note -> {
            Intent intent = new Intent(ListNotesActivity.this, EditNoteActivity.class);
            intent.putExtra(NoteAdapter.ITEM_SELECTED, note.getId());
            startActivity(intent);
        });

        noteAdapter.setOnCheckedNoteListener((note, isChecked) -> {
            Note modifiedNote = new Note(note.getId(),note.getName(), note.getDescription(), note.getImportance(), !isChecked);
            noteViewModel.modifyNote(modifiedNote);
        });

        floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(ListNotesActivity.this, AddNoteActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_list_notes_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.option_menu_delete_all_notes){
            showDialogAskingDeleteAllNotes();
        } else if(item.getItemId() == R.id.option_change_color_background){
            launchColorPickerDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showDialogAskingDeleteAllNotes() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.title_dialog_delete_allnotes)
                .setMessage(R.string.message_dialog_delete_allnotes)
                .setPositiveButton(getString(R.string.text_positive_button), (dialog, which) -> {
                    // If option yes is selected delete all notes
                    noteViewModel.deleteAllNotes();
                })
                // A null listener allows the button to dismiss the dialog without any action
                .setNegativeButton(R.string.text_negative_button, null)
                .setIcon(R.drawable.warning_icon)
                .show();
    }

    private void launchColorPickerDialog() {

        //Launch a Dialog to select the background color of the activity
        new ColorPickerDialog.Builder(this)
                .setTitle(getString(R.string.title_picker_dialog))
                .setPositiveButton(R.string.text_positive_button, //In case YES changing background and saving with shared preferences
                        (ColorEnvelopeListener) (envelope, fromUser) -> {
                            findViewById(R.id.constraint_layout_id).setBackgroundColor(envelope.getColor());
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ListNotesActivity.this);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(SETTINGS_SAVED, String.valueOf(envelope.getColor()));
                            editor.apply();
                        })
                .setNegativeButton(getString(R.string.text_negative_button), (dialogInterface, i) -> dialogInterface.dismiss())
                .setBottomSpace(12) // Set a bottom space between the last slidebar and buttons.
                .show();
    }

}