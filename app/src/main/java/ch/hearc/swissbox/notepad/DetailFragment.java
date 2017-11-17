package ch.hearc.swissbox.notepad;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.hearc.swissbox.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {

    private Note note;
    private int index;
    private View.OnClickListener editListener;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        this.note = (Note) args.get("note");
        this.index = args.getInt("index");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable("note", note);
        savedInstanceState.putInt("index", index);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (savedInstanceState != null)
            this.note = (Note) savedInstanceState.getSerializable("note");

        NotePadActivity activity = (NotePadActivity) getActivity();
        activity.getFab().setImageResource(R.drawable.ic_mode_edit);
        createEditListener();
        activity.getFab().setOnClickListener(editListener);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView title = getView().findViewById(R.id.note_title);
        TextView description = getView().findViewById(R.id.note_description);
        description.setMovementMethod(new ScrollingMovementMethod());
        TextView datetime = getView().findViewById(R.id.note_datetime);

        title.setText(note.getTitle());
        description.setText(note.getDescription());
        //SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        datetime.setText(String.format("Added : %s", Note.DATE_FORMAT.format(note.getDate())));
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("MYTAG", "onResume");
    }

    private void createEditListener() {
        editListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EditNoteFragment editNoteFragment = new EditNoteFragment();
                Bundle args = new Bundle();
                args.putSerializable("note", note);
                args.putInt("index", index);
                editNoteFragment.setArguments(args);
                getFragmentManager().
                        beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.myfragment, editNoteFragment)
                        .addToBackStack("")
                        .commit();
            }
        };
    }
}
