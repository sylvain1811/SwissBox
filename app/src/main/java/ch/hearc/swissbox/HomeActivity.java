package ch.hearc.swissbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ch.hearc.swissbox.mirror.MirrorActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        launchMirror();

    }

    private void launchMirror() {
        Intent intent = new Intent(this, MirrorActivity.class);
        startActivity(intent);
    }
}
