package ch.hearc.swissbox.notepad;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import ch.hearc.swissbox.R;
import ch.hearc.swissbox.tools.UsefulTools;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditNoteFragment extends Fragment {

    private Note note;
    private NotePadActivity activity;
    private View.OnClickListener saveListener;
    private int index;

    public EditNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        note = (Note) args.getSerializable("note");
        index = args.getInt("index");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (NotePadActivity) getActivity();
        activity.getFab().setImageResource(R.drawable.ic_save);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView edit_title = (TextView) view.findViewById(R.id.edit_title);
        TextView edit_description = (TextView) view.findViewById(R.id.edit_description);

        createSaveListener(edit_title, edit_description);
        activity.getFab().setOnClickListener(saveListener);

        edit_title.setText(note.getTitle());
        edit_description.setText(note.getDescription());
    }

    private void createSaveListener(final TextView textViewTitle, final TextView textViewDescription) {
        saveListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = textViewTitle.getText().toString();
                String description = textViewDescription.getText().toString();

                if (title.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.msg_title_empty, Toast.LENGTH_SHORT).show();
                    return;
                }

                note = new Note(title, description, new Date(System.currentTimeMillis()));

                NotePadActivity activity = (NotePadActivity) getActivity();
                activity.getNotes().add(note); // Adding new version
                activity.getNotes().remove(index); // Delete old version
                activity.saveNotes();

                UsefulTools.hideKeyboard(getActivity());

                getFragmentManager().popBackStack();
                getFragmentManager().popBackStack();

                Snackbar.make(view, R.string.note_saved, Snackbar.LENGTH_LONG).show();
            }
        };
    }
}
