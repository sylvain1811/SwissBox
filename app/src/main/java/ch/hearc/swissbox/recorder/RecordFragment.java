package ch.hearc.swissbox.recorder;


import android.Manifest;
import android.app.Fragment;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import ch.hearc.swissbox.R;

public class RecordFragment extends Fragment {

    //Tools
    public static final String TAG = "RecordFragment";
    private static final int REQUEST_RECORDER_PERMISSIONS = 200;
    private String[] mPermissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private RecorderActivity mActivity;
    private static final String PATH_SEPARATOR = "/";

    //UI
    private Button mRecordButton;
    private TextView mTextDuration;

    //Recorder
    private MediaRecorder mRecorder;
    private boolean mIsRecording = false;
    private static final String EXTENSION = ".3gp";
    private String mFilePath;
    private String mFileName;

    //Listener
    private View.OnClickListener mFabListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getFragmentManager().popBackStack();
        }
    };

    private Button.OnClickListener mRecordListener = new Button.OnClickListener() {
        public void onClick(View v) {
            if (mIsRecording) {
                stopRecording();
            } else {
                startRecording();
            }
        }
    };

    //Timer
    private Handler mTimerHandler = new Handler();
    private long mStartTime;

    Runnable mTimerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - mStartTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            mTextDuration.setText(String.format("%d:%02d", minutes, seconds));
            mTimerHandler.postDelayed(this, 500);
        }
    };

    public RecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity = (RecorderActivity) getActivity();
        return inflater.inflate(R.layout.fragment_recorder_record, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(getActivity(), mPermissions, REQUEST_RECORDER_PERMISSIONS);

        mActivity.getFab().setImageResource(R.drawable.ic_arrow_back);
        mActivity.getFab().setOnClickListener(mFabListener);
        mRecordButton = view.findViewById(R.id.button_record);
        mRecordButton.setOnClickListener(mRecordListener);
        mTextDuration = view.findViewById(R.id.text_record_duration);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
    }

    private void startTimer() {
        mStartTime = System.currentTimeMillis();
        mTimerHandler.postDelayed(mTimerRunnable, 0);
    }

    private void stopTimer() {
        mTimerHandler.removeCallbacks(mTimerRunnable);
    }

    private String getFilePath() {
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        filePath += PATH_SEPARATOR;
        filePath += RecorderActivity.STORAGE_DIR;
        filePath += PATH_SEPARATOR;
        mFileName = getCurrentTimeStamp() + EXTENSION;
        filePath += mFileName;
        return filePath;
    }

    private String getCurrentTimeStamp() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date());
            return currentDateTime;
        } catch (Exception e) {
            Log.w(TAG, "Unable to retrieve current timestamp");
            return "audiorecord";
        }
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mFilePath = getFilePath();
        mRecorder.setOutputFile(mFilePath);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
            mRecorder.start();
            mIsRecording = true;
            startTimer();
            mRecordButton.setText(getResources().getText(R.string.stop));
        } catch (IOException e) {
            Log.w(TAG, "prepare() failed : " + e.toString());
            Toast.makeText(getActivity(), R.string.error_recording, Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        stopTimer();
        mIsRecording = false;
        mRecordButton.setText(getResources().getText(R.string.start));
        Toast.makeText(getActivity(), getResources().getString(R.string.saved, mFileName), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORDER_PERMISSIONS:
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    getFragmentManager().popBackStack();
                    Toast.makeText(getActivity(), R.string.record_permission_not_granted, Toast.LENGTH_SHORT).show();
                }
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), R.string.write_permission_not_granted, Toast.LENGTH_SHORT).show();
                    getFragmentManager().popBackStack();
                }
                break;
        }
    }
}

