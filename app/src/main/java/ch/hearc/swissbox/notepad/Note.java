package ch.hearc.swissbox.notepad;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sylvain.renaud on 27.10.2017.
 */

public class Note implements Serializable {

    // Attributs
    private String title;
    private String description;
    private Date date;
    public static DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault());

    // Constructeur
    public Note(String title, String description, Date date) {
        this.setTitle(title);
        this.setDescription(description);
        this.setDate(date);
    }

    public JSONObject getJSONObject() {
        JSONObject object = new JSONObject();
        try {
            object.put("title", title);
            object.put("description", description);
            object.put("date", DATE_FORMAT.format(date));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
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
