package fr.axonic.util;

public interface Bindable<T> {

    void bind(T model);

    void unbind();

    default boolean isBound() {
        return getBoundEntity() != null;
    }

    T getBoundEntity();

}
