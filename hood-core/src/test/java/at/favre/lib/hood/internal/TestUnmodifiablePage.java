package at.favre.lib.hood.internal;


import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import at.favre.lib.hood.Hood;
import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class TestUnmodifiablePage {
    private Page original;
    private Page unmodifiablePages;

    @Before
    public void setup() {
        Pages pages = DebugPages.Factory.create(Config.newBuilder().build());
        original = pages.addNewPage("empty");
        unmodifiablePages = new UnmodifiablePageDelegate(original);
        checkEqual(original, unmodifiablePages);
    }

    private static void checkEqual(Page p1, Page p2) {
        assertEquals(p1.getEntries().size(), p2.getEntries().size());
        assertEquals(p1.getConfig(), p2.getConfig());
        assertEquals(p1.getTitle(), p2.getTitle());
    }

    @Test
    public void testEmptyPage() throws Exception {
        assertTrue(unmodifiablePages.getEntries().isEmpty());
    }

    @Test
    public void testModifyShouldNotWork() throws Exception {
        try {
            unmodifiablePages.add(Hood.get().createHeaderEntry("test"));
            fail();
        } catch (UnsupportedOperationException e) {
        }

        try {
            unmodifiablePages.add((List) Collections.singletonList(Hood.get().createHeaderEntry("test2")));
            fail();
        } catch (UnsupportedOperationException e) {
        }

        try {
            unmodifiablePages.add(new DefaultSection("test3"));
            fail();
        } catch (UnsupportedOperationException e) {
        }
    }

    @Test
    public void testNonEmptyPage() throws Exception {
        original.add(Hood.get().createHeaderEntry("test"));
        unmodifiablePages = new UnmodifiablePageDelegate(original);
        checkEqual(original, unmodifiablePages);
    }
}