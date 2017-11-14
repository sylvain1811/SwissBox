package ch.hearc.swissbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ch.hearc.swissbox.notepad.NotePadActivity;
import ch.hearc.swissbox.mirror.MirrorActivity;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /**
         * Target the different card view
         */
        CardView cardFlashLight = (CardView) findViewById(R.id.card_light_id);
        CardView cardNotePad = (CardView) findViewById(R.id.card_notepad_id);
        CardView cardMirror = (CardView) findViewById(R.id.card_mirror_id);

        /**
         * Init. the differents intent activity
         */
        final Intent mirrortIntent = new Intent(this, MirrorActivity.class);
        final Intent notePadIntent = new Intent(this, NotePadActivity.class);

        /**
         * Set click listener
         */
        cardNotePad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(notePadIntent);
            }
        });

        cardMirror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mirrortIntent);
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
