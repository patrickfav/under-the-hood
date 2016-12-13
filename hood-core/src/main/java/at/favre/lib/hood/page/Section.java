package at.favre.lib.hood.page;


import java.util.List;

/**
 * A section is a bunch of {@link PageEntry} with some additional business logic (e.g. adding/removing header)
 */
public interface Section {

    /**
     * @return content of this section as list
     */
    List<PageEntry<?>> asEntryList();
}
