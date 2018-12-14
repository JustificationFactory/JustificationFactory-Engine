package fr.axonic.observation;

import fr.axonic.observation.event.AEntityChanged;
import fr.axonic.observation.event.ChangedEventType;

import java.util.EventListener;

/**
 * Created by cboinaud on 12/07/2016.
 */
public interface AEntityListener extends EventListener {

    void changed(AEntityChanged events);

    default boolean listenOnlyEffectiveChanges() {
        return true;
    }

    default boolean acceptChangedType(ChangedEventType changedEventType) {
        return true;
    }
}
