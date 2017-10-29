package ch.hearc.swissbox.notepad;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import ch.hearc.swissbox.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


    private Note note;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.note = (Note) args.get("note");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView title = getView().findViewById(R.id.note_title);
        TextView description = getView().findViewById(R.id.note_description);
        TextView datetime = getView().findViewById(R.id.note_datetime);

        title.setText(note.getTitle());
        description.setText(note.getDescription());
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm, dd.MM.yyyy", Locale.getDefault());
        datetime.setText(new StringBuilder().append("Added : ").append(dateFormat.format(note.getDate())).toString());
    }
}
