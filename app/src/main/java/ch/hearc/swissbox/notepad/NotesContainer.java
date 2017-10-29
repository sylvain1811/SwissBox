package ch.hearc.swissbox.notepad;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sylvain.renaud on 29.10.2017.
 */

public class NotesContainer implements Serializable {

    private List<Note> notes;

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }


    public NotesContainer(List<Note> notes) {
        this.notes = notes;
    }
}
