package at.favre.lib.hood;

import android.content.Intent;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import at.favre.lib.hood.interfaces.HoodAPI;
import at.favre.lib.hood.interfaces.Section;
import at.favre.lib.hood.interfaces.actions.OnClickAction;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class HoodApiExtensionTest {
    private HoodAPI.Extension hoodAPI;

    @Before
    public void setup() {
        hoodAPI = Hood.ext();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testCreateTapListener() throws Exception {
        View.OnTouchListener tapListener = hoodAPI.createArbitraryTapListener(3,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

        assertNotNull(tapListener);
    }

    @Test
    public void testCreateLabel() throws Exception {
        CharSequence charSequence = hoodAPI.createFullLabel("short", "long");
        assertEquals("short", charSequence.toString());
    }

    @Test
    public void testCreateSection() throws Exception {
        Section section = hoodAPI.createSection("test");
        assertNotNull(section);
        assertEquals(0, section.asEntryList().size());

        section = hoodAPI.createSection("test", (List) Collections.singletonList(Hood.get().createHeaderEntry("test2")));
        assertNotNull(section);
        assertEquals(2, section.asEntryList().size());
    }

    @Test
    public void testCreateOnClickActions() throws Exception {
        OnClickAction onClickAction = hoodAPI.createOnClickActionAskPermission("perm", null);
        assertNotNull(onClickAction);
        onClickAction = hoodAPI.createOnClickActionDialog();
        assertNotNull(onClickAction);
        onClickAction = hoodAPI.createOnClickActionStartIntent(new Intent());
        assertNotNull(onClickAction);
        onClickAction = hoodAPI.createOnClickActionToast();
        assertNotNull(onClickAction);
    }
}
