package ch.hearc.swissbox.notepad;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sylvain.renaud on 29.10.2017.
 */

public class NotesContainer implements Serializable {

    private static List<Note> notes = null;
    private static NotesContainer INSTANCE = null;

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public static void init(List<Note> notes) {
        NotesContainer.notes = notes;
    }

    public static NotesContainer getInstance() {
        if (INSTANCE == null) {
            if (notes == null)
                return null;
            INSTANCE = new NotesContainer();
        }
        return INSTANCE;
    }

    private NotesContainer() {
    }
}
