package at.favre.lib.hood.page.values;

public interface ChangeableValue<T,E> extends DynamicValue<T>{
    void onChange(E value);
}
