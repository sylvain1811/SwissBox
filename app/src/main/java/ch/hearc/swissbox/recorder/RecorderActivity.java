package ch.hearc.swissbox.recorder;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.io.File;

import ch.hearc.swissbox.R;


public class RecorderActivity extends AppCompatActivity {

    public static final String TAG = "RecorderActivity";

    //UI
    private FloatingActionButton fab;

    //Data
    public static final String STORAGE_DIR = "SwissBoxRecordings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorder);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        createStorageDir();

        RecorderListFragment fragment = new RecorderListFragment();
        getFragmentManager().
                beginTransaction()
                .replace(R.id.fragment_recorder_content, fragment)
                .commit();

        fab = (FloatingActionButton) findViewById(R.id.fab_recorder);
    }

    public FloatingActionButton getFab() {
        return fab;
    }

    private void createStorageDir() {
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), STORAGE_DIR);

        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
