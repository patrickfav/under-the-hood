package at.favre.lib.hood.views;

public interface ChangeableValue<T> {
    T getValue();
    void onChange(T value);
}
