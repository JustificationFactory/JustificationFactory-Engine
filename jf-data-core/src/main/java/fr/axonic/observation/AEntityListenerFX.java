package fr.axonic.observation;

import fr.axonic.observation.event.AEntityChanged;
import fr.axonic.util.FxThreadHelper;

/**
 * 
 * @author bgrabiec
 *
 */
public interface AEntityListenerFX extends AEntityListener {

    default void changed(AEntityChanged events) {
        FxThreadHelper.runAndWait(() -> onChange(events));
    }

    void onChange(AEntityChanged events);

}
