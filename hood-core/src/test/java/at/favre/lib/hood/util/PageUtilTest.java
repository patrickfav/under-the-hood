package at.favre.lib.hood.util;

import android.view.View;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.Page;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.actions.ButtonDefinition;
import at.favre.lib.hood.interfaces.actions.OnClickAction;
import at.favre.lib.hood.internal.DebugPages;

import static junit.framework.Assert.assertEquals;

public class PageUtilTest {
    private Page page;

    @Before
    public void setup() {
        Pages pages = DebugPages.Factory.create(Config.newBuilder().build());
        page = pages.addNewPage("empty");
        assertEquals(0, page.getEntries().size());
    }

    @Test
    public void testAddProperty() throws Exception {
        PageUtil.addProperty(page, "key", "value");
        assertEquals(1, page.getEntries().size());
    }

    @Test
    public void testAddNullActions() throws Exception {
        PageUtil.addAction(page, null);
        assertEquals(0, page.getEntries().size());

        PageUtil.addAction(page, null, null);
        assertEquals(0, page.getEntries().size());
    }

    @Test
    public void testAddAction() throws Exception {
        PageUtil.addAction(page, new ButtonDefinition("test", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {

            }
        }));
        assertEquals(1, page.getEntries().size());
    }

    @Test
    public void testAdd() throws Exception {
        PageUtil.addHeader(page, "test");
        assertEquals(1, page.getEntries().size());
    }
}
