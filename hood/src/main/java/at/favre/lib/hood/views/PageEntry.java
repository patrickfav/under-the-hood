package at.favre.lib.hood.views;


public interface PageEntry<T> {

    T getValue();

    ViewTemplate<T> getViewTemplate();

    boolean isStaticContent();

}
