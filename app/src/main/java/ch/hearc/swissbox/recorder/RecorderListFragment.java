package ch.hearc.swissbox.recorder;


import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.hearc.swissbox.R;


public class RecorderListFragment extends ListFragment implements SearchView.OnQueryTextListener {

    public static final String TAG = "RecordFragment";
    private RecorderActivity mActivity;

    //Data
    private List<File> mListRecords;
    private RecordAdapter mRecordAdapter;

    //UI
    private AlertDialog.Builder mAlertDialogBuilder;
    private MenuItem mSearchMenuItem;
    private SearchView mSearchView;


    private View.OnClickListener mFabListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecordFragment recordFragment = new RecordFragment();
            getFragmentManager().
                    beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.fragment_recorder_content, recordFragment)
                    .addToBackStack("")
                    .commit();
        }
    };

    public RecorderListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (RecorderActivity) getActivity();
        mActivity.getFab().setImageResource(R.drawable.ic_add);

        return inflater.inflate(R.layout.fragment_recorder_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);

        mListRecords = new ArrayList<File>();
        refreshList();

        mRecordAdapter = new RecordAdapter(getActivity(), mListRecords);
        setListAdapter(mRecordAdapter);
        setListListener();

        TextView emptyView = getView().findViewById(R.id.text_empty);
        getListView().setEmptyView(emptyView);

        mActivity.getFab().setOnClickListener(mFabListener);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        mSearchMenuItem = menu.findItem(R.id.search);

        mSearchView = (SearchView) mSearchMenuItem.getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mRecordAdapter.getFilter().filter(newText);
        mRecordAdapter.notifyDataSetChanged();
        return true;
    }

    private void refreshList() {
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), RecorderActivity.STORAGE_DIR);

        File[] files = dir.listFiles();
        mListRecords.addAll(Arrays.asList(files));
    }

    private void setListListener() {
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Play song using default player
                playRecord(position);
            }
        });

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                if (mAlertDialogBuilder == null) {
                    mAlertDialogBuilder = new AlertDialog.Builder(getActivity());
                }
                mAlertDialogBuilder.setItems(R.array.item_options_recorder, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                // Share
                                shareRecord(position);
                                break;
                            case 1:
                                // Delete
                                deleteRecord(position, view);
                                break;
                        }
                    }
                });
                mAlertDialogBuilder.create().show();
                return true;
            }
        });
    }

    private void deleteRecord(int position, View view) {
        mListRecords.get(position).delete();
        mListRecords.remove(position);
        Snackbar.make(view, R.string.record_deleted, Snackbar.LENGTH_LONG).show();
        mRecordAdapter.notifyDataSetChanged();
    }

    private void playRecord(int position) {
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(mListRecords.get(position)), "audio/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }

    private void shareRecord(int position) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("audio/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mListRecords.get(position)));
        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.share)));
    }
}
