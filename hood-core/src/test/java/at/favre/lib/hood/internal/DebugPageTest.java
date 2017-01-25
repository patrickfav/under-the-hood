package at.favre.lib.hood.internal;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.Section;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;

public class DebugPageTest {
    private Page page;

    @Before
    public void setup() {
        Pages pages = DebugPages.Factory.create(Config.newBuilder().build());
        page = pages.addNewPage("empty");
        assertEquals(0, page.getEntries().size());
    }

    @After
    public void tearDown() {
        page.removeEntries();
        assertEquals(0, page.getEntries().size());
    }


    @Test
    public void testEmptyPage() throws Exception {
        final String title = UUID.randomUUID().toString();
        final Config config = Config.newBuilder()
                .setShowZebra(false)
                .setAutoRefresh(true)
                .setLogTag("smth")
                .build();

        page = DebugPage.Factory.create(DebugPages.Factory.create(config), title);
        assertEquals(0, page.getEntries().size());
        assertEquals(title, page.getTitle());
        assertEquals(config, page.getConfig());
    }

    @Test
    public void testAddSingleEntry() throws Exception {
        PageEntry<?> pageEntry = Hood.get().createPropertyEntry("key", "value");
        page.add(pageEntry);
        assertEquals(1, page.getEntries().size());
        assertEquals(pageEntry, page.getEntries().get(0));
    }

    @Test
    public void testRemoveSingleEntry() throws Exception {
        PageEntry<?> pageEntry = Hood.get().createPropertyEntry("key", "value");
        page.add(pageEntry);

        assertEquals(1, page.getEntries().size());
        assertEquals(pageEntry, page.getEntries().get(0));

        page.removeEntries();
        assertEquals(0, page.getEntries().size());
    }

    @Test
    public void testRemoveMultiEntry() throws Exception {
        for (int i = 0; i < 100; i++) {
            PageEntry<?> pageEntry = Hood.get().createPropertyEntry("key" + i, "value" + i);
            page.add(pageEntry);
            assertEquals(i + 1, page.getEntries().size());
            assertEquals(pageEntry, page.getEntries().get(i));
        }
    }

    @Test
    public void testAddListOfEntries() throws Exception {
        List<PageEntry<?>> entries = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            entries.add(Hood.get().createPropertyEntry("key" + i, "value" + i));
        }

        page.add(entries);
        assertEquals(entries.size(), page.getEntries().size());

        for (int i = 0; i < entries.size(); i++) {
            assertEquals(entries.get(i), page.getEntries().get(i));
        }
    }

    @Test
    public void testAddSection() throws Exception {
        final int sectionCount = 33;
        Section.ModifiableHeaderSection section = new DefaultSection("");
        for (int i = 0; i < sectionCount; i++) {
            section.add(Hood.get().createPropertyEntry("key" + i, "value" + i));
        }

        page.add(section.removeHeader().omitErrorMessage());
        assertEquals(sectionCount, page.getEntries().size());
    }

    @Test
    public void testPagesEqualsLogic() throws Exception {
        Pages defaultPages = DebugPages.Factory.create(Config.newBuilder().build());
        Page page1a = DebugPage.Factory.create(defaultPages, "one");
        Page page1b = DebugPage.Factory.create(defaultPages, "two");

        assertNotSame(page1a, page1b);

        Page page2a = DebugPage.Factory.create(DebugPages.Factory.create(Config.newBuilder().setAutoRefresh(true).build()), "same");
        Page page2b = DebugPage.Factory.create(DebugPages.Factory.create(Config.newBuilder().setAutoRefresh(false).build()), "same");

        assertNotSame(page2a, page2b);

        Page page3a = DebugPage.Factory.create(defaultPages, "same");
        Page page3b = DebugPage.Factory.create(defaultPages, "same");

        page3a.add(Hood.get().createPropertyEntry("key", "value"));
        assertNotSame(page3a, page3b);
        page3b.add(Hood.get().createPropertyEntry("key_a", "value_a"));
        assertNotSame(page3a, page3b);

        Page page4a = DebugPage.Factory.create(defaultPages, "one");
        Page page4b = DebugPage.Factory.create(defaultPages, "one");
        assertEquals(page4a, page4b);
    }
}
