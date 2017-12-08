package ch.hearc.swissbox.recorder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Record implements Serializable {

    private String mTitle;
    private String mPath;
    private Date mDate;
    public static DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm\ndd.MM.yyyy", Locale.getDefault());

    public Record(String title, String path, Date date) {
        this.setTitle(title);
        this.setPath(path);
        this.setDate(date);
    }

    public Record(Record source) {
        this.setTitle(new String(source.mTitle));
        this.setPath(new String(source.mPath));
        this.setDate(new Date(System.currentTimeMillis()));
    }

    public JSONObject getJSONObject() {
        JSONObject object = new JSONObject();

        try {
            object.put("title", mTitle);
            object.put("path", mPath);
            object.put("date", DATE_FORMAT.format(mDate));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return object;
    }

    //Get, Set

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String mPath) {
        this.mPath = mPath;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    @Override
    public String toString() {
        return this.mTitle;
    }
}
