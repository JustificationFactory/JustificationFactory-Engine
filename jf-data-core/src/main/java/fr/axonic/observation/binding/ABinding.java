package fr.axonic.observation.binding;

import java.util.Arrays;

import fr.axonic.base.engine.AEntity;
import fr.axonic.base.engine.AList;
import fr.axonic.observation.AEntityListener;
import fr.axonic.observation.event.AEntityChanged;
import fr.axonic.observation.event.AEntityEvent;

public abstract class ABinding<T extends AEntity> {

    private AList<T>        dependencies  = new AList<>();

    private AEntityListener eventListener = this::notifyDependencies;

    public void bind(T... dependenciesArray) {
        if (dependenciesArray != null) {
            dependencies.addAll(Arrays.asList(dependenciesArray));
            dependencies.forEach(entity -> entity.addListener(eventListener));
            prepareBinding();
        }
    }

    protected void prepareBinding() {
        // DO NOTHING
    }

    public void unbind() {
        dependencies.forEach(entity -> entity.removeListener(eventListener));
        finalizeBinding();
    }

    protected void finalizeBinding() {
        // DO NOTHING
    }

    protected AList<T> getDependencies() {
        return dependencies;
    }

    protected void notifyDependencies(AEntityChanged events) {
        if (events != null && acceptEvents(events)) {
            for (AEntityEvent event : events.getEvents()) {
                Object eventSource = event.getSource();
                for (T dependency : dependencies) {
                    if (acceptEvent(event, dependency)) {
                        onEventAction(event, dependency);
                    }
                }
            }
        }
    }

    protected boolean acceptEvents(AEntityChanged event) {
        return true;
    }

    protected boolean acceptEvent(AEntityEvent event, T dependency) {
        return true;
    }

    protected abstract void onEventAction(AEntityEvent event, T dependency);
}
