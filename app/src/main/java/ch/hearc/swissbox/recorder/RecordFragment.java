package ch.hearc.swissbox.recorder;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.hearc.swissbox.R;

public class RecordFragment extends Fragment {

    public static final String TAG = "RecordFragment";
    private RecorderActivity mActivity;

    //Listener
    private View.OnClickListener mFabListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getFragmentManager().popBackStack();
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
        mActivity.getFab().setImageResource(R.drawable.ic_arrow_back);
        mActivity.getFab().setOnClickListener(mFabListener);
    }
}
