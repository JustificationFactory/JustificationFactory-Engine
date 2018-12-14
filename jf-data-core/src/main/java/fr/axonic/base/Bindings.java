package fr.axonic.base;

import java.util.HashSet;
import java.util.Set;

import fr.axonic.base.engine.AEntity;

public class Bindings {

    /**
     * Searches and returns a root element in the binding hierarchy of the given entity. If there is a cycle the method
     * returns an element of the cycle.
     * 
     * @param entity
     * @return
     */
    public static <T extends AEntity> T getBindingRootElement(T entity) {
        T rootElement = entity;

        Set<Integer> identityHashCodes = new HashSet<>();
        identityHashCodes.add(System.identityHashCode(entity));

        while (rootElement.isBound()) {
            rootElement = (T) (rootElement.getBindElement());
            int identityHashCode = System.identityHashCode(rootElement);
            // FIXME check if they are really the same objects
            if (identityHashCodes.contains(identityHashCode)) {
                // A cycle detected
                break;
            } else {
                identityHashCodes.add(identityHashCode);
            }
        }

        return rootElement;
    }

}
