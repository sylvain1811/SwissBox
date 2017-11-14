package ch.hearc.swissbox.flashlight;

/**
 * Created by johnny.dacosta on 14/11/2017.
 */

class FlashLight {
    private static final FlashLight ourInstance = new FlashLight();

    static FlashLight getInstance() {
        return ourInstance;
    }

    private FlashLight() {
    }
}
