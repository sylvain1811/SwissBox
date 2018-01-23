package ch.hearc.swissbox;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import ch.hearc.swissbox.counter.CounterActivity;
import ch.hearc.swissbox.dice.DiceActivity;
import ch.hearc.swissbox.flashlight.FlashLight;
import ch.hearc.swissbox.location.MapsActivity;
import ch.hearc.swissbox.mirror.MirrorActivity;
import ch.hearc.swissbox.notepad.NotePadActivity;
import ch.hearc.swissbox.settings.SettingsActivity;
import ch.hearc.swissbox.recorder.RecorderActivity;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = "HomeActivity";

    private View.OnClickListener cardClickListener = null;
    private FlashLight flashLight;
    private ArrayList<CardView> cardViews;

    @Override
    protected void onResume() {
        super.onResume();
        // Read preference to enable location or not
        //cardLocation.setEnabled(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("switch_experimental", false));
        if (!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("switch_experimental", false)) {
            for (CardView cardView : cardViews) {
                if (cardView.getId() == R.id.card_location_id) {
                    cardView.setOnClickListener(null);
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        if (UsefulTools.isIsNightModeEnabled())
            setTheme(R.style.ActivityTheme_Primary_Base_Dark);
        else
            setTheme(R.style.ActivityTheme_Primary_Base_Light);
        */
        setContentView(R.layout.activity_home);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        initCardListener();
        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        /*
         * Get the instance FlashLight
         */
        flashLight = FlashLight.getInstance();
        flashLight.setFlashAvailable(getApplicationContext()
                .getPackageManager()
                .hasSystemFeature(PackageManager
                        .FEATURE_CAMERA_FLASH));

        flashLight.setCameraManager((CameraManager) getSystemService(Context.CAMERA_SERVICE));

        try {
            flashLight.setCameraId(flashLight.getCameraManager().getCameraIdList()[0]);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        /*
          Target the different card view
         */
        cardViews = new ArrayList<>(10);
        cardViews.add((CardView) findViewById(R.id.card_light_id));
        cardViews.add((CardView) findViewById(R.id.card_notepad_id));
        cardViews.add((CardView) findViewById(R.id.card_mirror_id));
        cardViews.add((CardView) findViewById(R.id.card_dice_id));
        cardViews.add((CardView) findViewById(R.id.card_recorder_id));
        cardViews.add((CardView) findViewById(R.id.card_counter_id));
        cardViews.add((CardView) findViewById(R.id.card_location_id));


        /*
          Set click listener
         */
        for (CardView card : cardViews) {
            card.setOnClickListener(cardClickListener);
        }
    }

    /**
     * Alert the user that the flash is not available on his device
     */
    private void AlertBox() {
        AlertDialog alert = new AlertDialog.Builder(this)
                .create();
        alert.setTitle("Error");
        alert.setMessage("Your device doesn't support flash light!");
        alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // closing the application
                        finish();
                        System.exit(0);
                    }
                });
        alert.show();
    }

    /**
     * Set the flash mode ON
     */
    public void turnOnFlashLight() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flashLight.getCameraManager().setTorchMode(flashLight.getCameraId(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Set the flash mode OFF
     */
    public void turnOffFlashLight() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                flashLight.getCameraManager().setTorchMode(flashLight.getCameraId(), false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void flashlight() {
        if (!flashLight.isFlashAvailable()) {
            AlertBox();
            return;
        }

        try {
            if (flashLight.isFlashOn()) {
                turnOffFlashLight();
                flashLight.setFlashOn(false);
            } else {
                turnOnFlashLight();
                flashLight.setFlashOn(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mirror() {
        Intent intent = new Intent(HomeActivity.this, MirrorActivity.class);
        startActivity(intent);
    }

    private void notepad() {
        Intent intent = new Intent(HomeActivity.this, NotePadActivity.class);
        startActivity(intent);
    }

    private void dice() {
        Intent intent = new Intent(HomeActivity.this, DiceActivity.class);
        startActivity(intent);
    }

    private void counter() {
        Intent intent = new Intent(HomeActivity.this, CounterActivity.class);
        startActivity(intent);
    }

    private void recorder() {
        Intent intent = new Intent(HomeActivity.this, RecorderActivity.class);
        startActivity(intent);
    }

    private void location() {
        Intent intent = new Intent(HomeActivity.this, MapsActivity.class);
        startActivity(intent);
    }

    private void initCardListener() {
        cardClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Different action foreach card.
                switch (v.getId()) {
                    case R.id.card_notepad_id:
                        notepad();
                        break;
                    case R.id.card_mirror_id:
                        mirror();
                        break;
                    case R.id.card_light_id:
                        flashlight();
                        break;
                    case R.id.card_dice_id:
                        dice();
                        break;
                    case R.id.card_recorder_id:
                        recorder();
                        break;
                    case R.id.card_location_id:
                        location();
                        break;
                    case R.id.card_counter_id:
                        counter();
                        break;
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_settings:
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
