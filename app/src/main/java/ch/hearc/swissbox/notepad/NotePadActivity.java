package ch.hearc.swissbox.notepad;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.hearc.swissbox.R;

public class NotePadActivity extends AppCompatActivity {

    private List<Note> notes;
    private final String FILE_NAME = "notes.json";
    private FloatingActionButton fab;


    public List<Note> getNotes() {
        return notes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        notes = new ArrayList<>();
        //saveNotes();
        readNotes();


        NotesContainer.init(notes);

        NoteListFragment fragment = new NoteListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("notes", NotesContainer.getInstance());
        fragment.setArguments(bundle);
        getFragmentManager().
                beginTransaction()
                .replace(R.id.myfragment, fragment)
                .commit();

        setContentView(R.layout.activity_note_pad);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.notepad_toolbar);
        setSupportActionBar(toolbar);
    }

    public List<Note> readNotes() {
        notes.clear();
        try {
            FileInputStream fileInputStream = openFileInput(FILE_NAME);
            int size = fileInputStream.available();
            byte[] buffer = new byte[size];
            fileInputStream.read(buffer);
            fileInputStream.close();
            String json = new String(buffer, "UTF-8");

            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String title = object.getString("title");
                String description = object.getString("description");
                Date date = Note.DATE_FORMAT.parse(object.getString("date"));

                Note note = new Note(title, description, date);
                notes.add(note);
            }

        } catch (ParseException | JSONException | IOException e) {
            e.printStackTrace();
        }
        return notes;
    }

    public void saveNotes() {

        JSONArray jsonArray = new JSONArray();

        for (Note note : notes) {
            jsonArray.put(note.getJSONObject());
        }

        Log.i("JSONARRAY", jsonArray.toString());

        try {

            FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(jsonArray.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FloatingActionButton getFab() {
        return fab;
    }
}
