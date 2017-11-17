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
public class AddNoteFragment extends Fragment {

    private Note note = null;
    private NotePadActivity activity;
    private View.OnClickListener saveListener;

    public AddNoteFragment() {
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

        activity = (NotePadActivity) getActivity();
        return inflater.inflate(R.layout.fragment_add_note, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {

        TextView textViewTitle = view.findViewById(R.id.add_title);
        TextView textViewDescription = view.findViewById(R.id.add_description);

        createSaveListener(textViewTitle, textViewDescription);
        activity.getFab().setImageResource(R.drawable.ic_save);
        activity.getFab().setOnClickListener(saveListener);

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
                activity.getNotes().add(note);
                activity.saveNotes();

                UsefulTools.hideKeyboard(getActivity());

                getFragmentManager().popBackStack();
                Snackbar.make(view, R.string.note_saved, Snackbar.LENGTH_LONG).show();
            }
        };
    }

}
