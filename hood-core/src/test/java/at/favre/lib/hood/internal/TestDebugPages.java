package at.favre.lib.hood.internal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

public class TestDebugPages {
    private Pages pages;

    @Before
    public void setup() {
        pages = DebugPages.Factory.create(Config.newBuilder().build());
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testEmptyPage() throws Exception {
        assertTrue(pages.getAll().isEmpty());
        assertEquals(0, pages.size());

        try {
            assertNull(pages.getFirstPage());
            fail();
        } catch (IllegalStateException e) {
        }
    }

    @Test
    public void testAddSinglePage() throws Exception {
        Page page = pages.addNewPage();
        assertEquals(page, pages.getFirstPage());
        assertEquals(page, pages.getAll().get(0));
        assertEquals(pages.getFirstPage(), pages.getAll().get(0));
        assertEquals(1, pages.size());
        assertEquals(pages.size(), pages.getAll().size());
    }

    @Test
    public void testAddSinglePageWithTitle() throws Exception {
        final String title = "The Title TestTest";
        Page page = pages.addNewPage(title);
        assertEquals(page, pages.getFirstPage());
        assertEquals(title, pages.getAll().get(0).getTitle());
        assertEquals(title, pages.getFirstPage().getTitle());
    }

    @Test
    public void testConfigEquals() throws Exception {
        Config config = Config.newBuilder()
                .setShowZebra(false)
                .setAutoRefresh(true)
                .setLogTag("smth")
                .build();
        pages = DebugPages.Factory.create(config);
        assertEquals(config, pages.getConfig());
    }

    @Test
    public void testAddMultiplePages() throws Exception {
        for (int i = 0; i < 100; i++) {
            Page page = pages.addNewPage(UUID.randomUUID().toString());
            assertEquals(page, pages.getAll().get(i));
            assertEquals(i + 1, pages.size());
            assertEquals(pages.size(), pages.getAll().size());
        }
    }
}
