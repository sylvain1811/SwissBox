package ch.hearc.swissbox.notepad;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by sylvain.renaud on 27.10.2017.
 */

public class Note implements Serializable {

    // Attributs
    private String title;
    private String description;
    private Date date;

    // Constructeur
    public Note(String title, String description) {
        this.setTitle(title);
        this.setDescription(description);
        this.setDate(new Date(System.currentTimeMillis()));
    }

    // Get / Set

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return this.title;
    }
}
