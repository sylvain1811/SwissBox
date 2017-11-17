package ch.hearc.swissbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ch.hearc.swissbox.notepad.NotePadActivity;
import ch.hearc.swissbox.mirror.MirrorActivity;

import android.support.v7.widget.CardView;
import android.view.View;

public class HomeActivity extends AppCompatActivity {


    private View.OnClickListener cardClickListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initCardListener();

        /**
         * Target the different card view
         */
        CardView cardFlashLight = (CardView) findViewById(R.id.card_light_id);
        CardView cardNotePad = (CardView) findViewById(R.id.card_notepad_id);
        CardView cardMirror = (CardView) findViewById(R.id.card_mirror_id);

        /**
         * Set click listener
         */
        cardNotePad.setOnClickListener(cardClickListener);
        cardMirror.setOnClickListener(cardClickListener);
        cardFlashLight.setOnClickListener(cardClickListener);
    }

    private void launchMirror() {
        Intent intent = new Intent(this, MirrorActivity.class);
        startActivity(intent);
    }

    private void launchNotepad() {
        Intent intent = new Intent(HomeActivity.this, NotePadActivity.class);
        startActivity(intent);
    }


    private void launchFlashlight() {

    }

    private void initCardListener() {
        cardClickListener = new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.card_notepad_id:
                        launchNotepad();
                        break;
                    case R.id.card_mirror_id:
                        launchMirror();
                        break;
                    case R.id.card_light_id:
                        launchFlashlight();
                        break;
                }
            }
        };
    }

}
