package ch.hearc.swissbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ch.hearc.swissbox.notepad.NotePadActivity;
import ch.hearc.swissbox.mirror.MirrorActivity;

import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button btnNotepad = (Button) findViewById(R.id.btn_open_notepad);
        btnNotepad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            launchNotepad();
            }
        });
    }

    private void launchMirror() {
        Intent intent = new Intent(this, MirrorActivity.class);
        startActivity(intent);
    }

    private void launchNotepad() {
        Intent intent = new Intent(HomeActivity.this, NotePadActivity.class);
        startActivity(intent);
    }
}
