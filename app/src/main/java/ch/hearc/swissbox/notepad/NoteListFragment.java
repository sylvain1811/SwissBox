package ch.hearc.swissbox.notepad;


import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.ListFragment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ch.hearc.swissbox.R;


public class NoteListFragment extends ListFragment implements SearchView.OnQueryTextListener {

    private List<Note> notes;
    private View.OnClickListener fabListener;
    private NotePadActivity activity;
    private AlertDialog.Builder alertDialogBuilder = null;
    private MenuItem searchMenuItem;
    private SearchView searchView;
    private ArrayAdapter arrayAdapter;

    public NoteListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = (NotePadActivity) getActivity();
        activity.getFab().setImageResource(R.drawable.ic_add);
        createFabListener();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        this.notes = ((NotesContainer) getArguments().get("notes")).getNotes();

        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, notes);
        setListAdapter(arrayAdapter);
        setListListener();
        TextView emptyView = getView().findViewById(R.id.empty);
        getListView().setEmptyView(emptyView);

        //activity.changeFab(fabListener);
        activity.getFab().setOnClickListener(fabListener);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager)
                getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setSearchableInfo(searchManager.
                getSearchableInfo(getActivity().getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setListListener() {
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DetailFragment detailFragment = new DetailFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", (Note) getListView().getItemAtPosition(position));
                bundle.putInt("index", position);
                detailFragment.setArguments(bundle);

                getActivity().getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.myfragment, detailFragment)
                        .addToBackStack("")
                        .commit();
            }
        });

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                if (alertDialogBuilder == null) {
                    alertDialogBuilder = new AlertDialog.Builder(getActivity());
                }
                alertDialogBuilder.setItems(R.array.item_options_notepad, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // Share
                                share(position);
                                break;
                            case 1:
                                // Clone
                                activity.getNotes().add(new Note(activity.getNotes().get(position)));
                                arrayAdapter.notifyDataSetChanged();
                                break;
                            case 2:
                                // Delete
                                activity.getNotes().remove(position);
                                activity.saveNotes();
                                Snackbar.make(view, R.string.note_deleted, Snackbar.LENGTH_LONG);
                                arrayAdapter.notifyDataSetChanged();
                                break;
                        }
                    }
                });
                alertDialogBuilder.create().show();
                return true;
            }
        });
    }

    private void share(int position) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        Note note = activity.getNotes().get(position);
        shareIntent.putExtra(Intent.EXTRA_TEXT, note.getTitle() + "\n\n" + note.getDescription());
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.share)));
    }

    private void createFabListener() {
        fabListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddNoteFragment addNoteFragment = new AddNoteFragment();
                getFragmentManager().
                        beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.myfragment, addNoteFragment)
                        .addToBackStack("")
                        .commit();
            }
        };
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    /**
     * For search feature : https://coderwall.com/p/zpwrsg/add-search-function-to-list-view-in-android
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        arrayAdapter.getFilter().filter(newText);
        arrayAdapter.notifyDataSetChanged();
        return true;
    }
}
