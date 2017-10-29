package ch.hearc.swissbox.notepad;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

import ch.hearc.swissbox.R;

public class NotePadActivity extends AppCompatActivity {

    private List<Note> notes;

    public List<Note> getNotes() {
        return notes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notes = new ArrayList<>();

        for (int i = 0; i < 40; i++) {
            notes.add(new Note("note" + i, "description note " + i));
        }

        NoteListFragment fragment = new NoteListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("notes", new NotesContainer(notes));
        fragment.setArguments(bundle);
        getFragmentManager().
                beginTransaction()
                .add(R.id.myfragment, fragment)
                .commit();

        setContentView(R.layout.activity_note_pad);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                AddNoteFragment addNoteFragment = new AddNoteFragment();
                getFragmentManager().
                        beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.myfragment, addNoteFragment)
                        .addToBackStack("")
                        .commit();
            }
        });


    }
}
