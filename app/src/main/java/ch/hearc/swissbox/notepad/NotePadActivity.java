package ch.hearc.swissbox.notepad;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import ch.hearc.swissbox.R;

public class NotePadActivity extends AppCompatActivity {

    private List<Note> notes;
    //private File file;
    private final String FILE_NAME = "notes.json";

    public List<Note> getNotes() {
        return notes;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*        file = new File(Environment.getExternalStorageDirectory().getPath()
                + "/Android/data"
                + getPackageName()
                + "/files/" + FILE_NAME);*/

        notes = readNotes();

        NoteListFragment fragment = new NoteListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("notes", new NotesContainer(notes));
        fragment.setArguments(bundle);
        getFragmentManager().
                beginTransaction()
                .replace(R.id.myfragment, fragment)
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

    public List<Note> readNotes() {
        notes.clear();
        try {
            FileInputStream fileInputStream = openFileInput(FILE_NAME);
            //FileInputStream fileInputStream = new FileInputStream(file);
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
            //file.createNewFile();
            //FileOutputStream fos = new FileOutputStream(file);
            fos.write(jsonArray.toString().getBytes());
            fos.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
