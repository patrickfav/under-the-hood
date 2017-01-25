package at.favre.lib.hood.internal;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.interfaces.Section;

import static junit.framework.Assert.assertEquals;

public class DefaultSectionTest {
    private Section.ModifiableHeaderSection section;

    @Before
    public void setup() {
        section = new DefaultSection("empty");
        assertEquals(0, section.asEntryList().size());
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testEmptySection() throws Exception {
        section.removeHeader().omitErrorMessage();
        assertEquals(0, section.asEntryList().size());
    }

    @Test
    public void testAddSingleEntry() throws Exception {
        section.add(Hood.get().createPropertyEntry("key", "value"));
        assertEquals(2, section.asEntryList().size());
    }

    @Test
    public void testAddListEntry() throws Exception {
        Section.ModifiableHeaderSection anotherSection = new DefaultSection("empty2");
        anotherSection.add((List) Collections.singletonList(Hood.get().createHeaderEntry("test2")));
        section.add(anotherSection);
        assertEquals(2 + 1, section.asEntryList().size());
    }


    @Test
    public void testAddSection() throws Exception {
        Section.ModifiableHeaderSection anotherSection = new DefaultSection("empty2");
        anotherSection.add(Hood.get().createPropertyEntry("key", "value"));
        section.add(anotherSection);
        assertEquals(2 + 1, section.asEntryList().size());
    }

    @Test
    public void testAddErrorMessage() throws Exception {
        section.setErrorMessage("error");
        assertEquals(2, section.asEntryList().size());
    }
}
