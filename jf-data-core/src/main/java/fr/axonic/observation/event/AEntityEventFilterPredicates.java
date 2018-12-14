package fr.axonic.observation.event;

import java.util.function.Predicate;

import fr.axonic.base.engine.AEntity;

public class AEntityEventFilterPredicates {

    public static <T extends AEntityEvent> Predicate<T> filterByPathSuffixes(final String... fullPathSuffixes) {
        return event -> {
            if (fullPathSuffixes != null && event.getSource() instanceof AEntity) {
                for (String fullPathSuffix : fullPathSuffixes) {

                    AEntity entity = (AEntity) event.getSource();

                    if (entity.getFullPath().endsWith(fullPathSuffix)) {
                        return true;
                    }

                }
            }
            return false;
        };
    }

}
