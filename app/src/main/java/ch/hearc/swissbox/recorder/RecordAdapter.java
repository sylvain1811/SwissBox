package ch.hearc.swissbox.recorder;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import ch.hearc.swissbox.R;

/**
 * Created by axel.rieben on 22.12.2017.
 */

public class RecordAdapter extends ArrayAdapter<File> {

    public RecordAdapter(Context context, List<File> files) {
        super(context, 0, files);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        File file = getItem(position);

        Uri uri = Uri.parse(file.getAbsolutePath());
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(getContext(), uri);
        String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        int durationSeconds = Integer.parseInt(duration) / 1000;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.recorder_list_item, parent, false);
        }
        TextView textTop = (TextView) convertView.findViewById(R.id.text_top);
        TextView textBottom = (TextView) convertView.findViewById(R.id.text_bottom);
        textTop.setText(file.getName());
        textBottom.setText(Integer.toString(durationSeconds) + " s");

        return convertView;
    }
}

