package at.favre.lib.hood.internal;


import org.junit.Before;
import org.junit.Test;

import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Pages;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class UnmodifiablePagesTest {
    private Pages original;
    private Pages unmodifiablePages;

    @Before
    public void setup() {
        original = DebugPages.Factory.create(Config.newBuilder().build());
        unmodifiablePages = new UnmodifiablePages(original);
        checkEqual(original, unmodifiablePages);
    }

    private static void checkEqual(Pages p1, Pages p2) {
        assertEquals(p1.getAll().size(), p2.getAll().size());
        assertEquals(p1.getConfig(), p2.getConfig());
        assertEquals(p1.size(), p2.size());
    }

    @Test
    public void testEmptyPage() throws Exception {
        assertTrue(unmodifiablePages.getAll().isEmpty());
        assertEquals(0, unmodifiablePages.size());

        try {
            assertNull(unmodifiablePages.getFirstPage());
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void testModifyShouldNotWork() throws Exception {
        try {
            unmodifiablePages.addNewPage();
            fail();
        } catch (UnsupportedOperationException e) {
        }

        try {
            unmodifiablePages.addNewPage("empty");
            fail();
        } catch (UnsupportedOperationException e) {
        }
    }

    @Test
    public void testNonEmptyPage() throws Exception {
        original.addNewPage("newpage");
        unmodifiablePages = new UnmodifiablePages(original);
        checkEqual(original, unmodifiablePages);
        assertNotNull(unmodifiablePages.createDataMap());
    }
}