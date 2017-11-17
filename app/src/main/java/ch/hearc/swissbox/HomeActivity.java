package ch.hearc.swissbox;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import ch.hearc.swissbox.flashlight.FlashLight;
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
         * Get the instance FlashLight
         */
        final FlashLight flashLight = FlashLight.getInstance();
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

        cardFlashLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flashLight.isFlashAvailable()) {
                    AlertBox();
                    return;
                }

                try {
                    if (flashLight.isFlashOn()) {
                        turnOffFlashLight(flashLight);
                        flashLight.setFlashOn(false);
                    } else {
                        turnOnFlashLight(flashLight);
                        flashLight.setFlashOn(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Alert the user that the flash is not available on his device
     */
    private void AlertBox() {
        AlertDialog alert = new AlertDialog.Builder(this)
                .create();
        alert.setTitle("Error !!");
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
        return;
    }

    /**
     * Set the flash mode ON
     */
    public void turnOnFlashLight(FlashLight fl) {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                fl.getCameraManager().setTorchMode(fl.getCameraId(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Set the flash mode OFF
     */
    public void turnOffFlashLight(FlashLight fl) {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                fl.getCameraManager().setTorchMode(fl.getCameraId(), false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
