package ch.hearc.swissbox;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ch.hearc.swissbox.dice.DiceActivity;
import ch.hearc.swissbox.mirror.MirrorActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        launchDice();
    }

    private void launchMirror() {
        Intent intent = new Intent(this, MirrorActivity.class);
        startActivity(intent);
    }

    private void launchDice() {
        Intent intent = new Intent(this, DiceActivity.class);
        startActivity(intent);
    }
}
