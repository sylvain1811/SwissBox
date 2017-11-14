package ch.hearc.swissbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ch.hearc.swissbox.notepad.NotePadActivity;
import ch.hearc.swissbox.mirror.MirrorActivity;

import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    private View.OnClickListener btnClickListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initBtnListener();

        Button btnNotepad = (Button) findViewById(R.id.btn_open_notepad);
        Button btnMirror = (Button) findViewById(R.id.btn_open_mirror);

        if (btnClickListener != null) {
            btnNotepad.setOnClickListener(btnClickListener);
            btnMirror.setOnClickListener(btnClickListener);
        }
    }

    private void launchMirror() {
        Intent intent = new Intent(this, MirrorActivity.class);
        startActivity(intent);
    }

    private void launchNotepad() {
        Intent intent = new Intent(HomeActivity.this, NotePadActivity.class);
        startActivity(intent);
    }


    private void initBtnListener() {
        btnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_open_notepad:
                        launchNotepad();
                        break;
                    case R.id.btn_open_mirror:
                        launchMirror();
                        break;
                }
            }
        };
    }
}
