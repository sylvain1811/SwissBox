package ch.hearc.swissbox.flashlight;

/**
 * Created by johnny.dacosta on 14/11/2017.
 * <p>
 * This was inspired by the tutorial from codeproject.com with the new Camera2 api
 * https://www.codeproject.com/articles/1112813/android-flash-light-application-tutorial-using-cam</p>
 */


import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.widget.Button;

/**
 * Pattern : singleton
 */
public class FlashLight {
    public static final String TAG = "FlashLight";
    private static FlashLight INSTANCE = null;

    private Button switchLight;
    private CameraManager cameraManager;
    private String cameraId;
    private boolean isFlashOn;
    private boolean isFlashAvailable;
    private MediaPlayer mp;

    public static FlashLight getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new FlashLight();
        }
        return INSTANCE;
    }

    private FlashLight() {
        this.isFlashOn = false;
        this.isFlashAvailable = false;
        this.cameraManager = null;
        this.switchLight = null;
        this.mp = null;
        this.cameraId = null;
    }

    public Button getSwitchLight() {
        return switchLight;
    }

    public void setSwitchLight(Button switchLight) {
        this.switchLight = switchLight;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void setCameraManager(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }

    public String getCameraId() {
        return cameraId;
    }

    public void setCameraId(String cameraId) {
        this.cameraId = cameraId;
    }

    public boolean isFlashOn() {
        return isFlashOn;
    }

    public void setFlashOn(boolean flashOn) {
        isFlashOn = flashOn;
    }

    public boolean isFlashAvailable() {
        return isFlashAvailable;
    }

    public void setFlashAvailable(boolean flashAvailable) {
        isFlashAvailable = flashAvailable;
    }

    public MediaPlayer getMp() {
        return mp;
    }

    public void setMp(MediaPlayer mp) {
        this.mp = mp;
    }
}
