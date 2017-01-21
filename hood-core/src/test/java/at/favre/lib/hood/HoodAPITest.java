package at.favre.lib.hood;

import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.HoodAPI;
import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.actions.BoolConfigAction;
import at.favre.lib.hood.interfaces.actions.ButtonDefinition;
import at.favre.lib.hood.interfaces.actions.OnClickAction;
import at.favre.lib.hood.interfaces.actions.SingleSelectListConfigAction;
import at.favre.lib.hood.interfaces.values.ChangeableValue;
import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.interfaces.values.SpinnerValue;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class HoodAPITest {
    private HoodAPI hoodAPI;

    @Before
    public void setup() {
        hoodAPI = Hood.get();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testCreateProperty() throws Exception {
        checkNotNull(hoodAPI.createPropertyEntry("key", "value"));
        checkNotNull(hoodAPI.createPropertyEntry("key", "value", true));

        PageEntry<?> pageEntry = hoodAPI.createPropertyEntry("key", "value", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {

            }
        }, false);
        checkNotNull(pageEntry);
    }

    @Test
    public void testCreateAction() throws Exception {
        PageEntry<?> pageEntry = hoodAPI.createActionEntry(new ButtonDefinition("empty", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {

            }
        }));
        checkNotNull(pageEntry);

        pageEntry = hoodAPI.createActionEntry(new ButtonDefinition("empty", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {

            }
        }), new ButtonDefinition("empty2", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {

            }
        }));
        checkNotNull(pageEntry);
    }

    @Test
    public void testCreateHeader() throws Exception {
        checkNotNull(hoodAPI.createHeaderEntry("title"));
    }

    @Test
    public void testCreateMessage() throws Exception {
        checkNotNull(hoodAPI.createMessageEntry("message"));
    }

    @Test
    public void testCreateSwitch() throws Exception {
        PageEntry<?> pageEntry = hoodAPI.createSwitchEntry(new BoolConfigAction("label", new ChangeableValue<Boolean, Boolean>() {
            @Override
            public void onChange(Boolean value) {

            }

            @Override
            public Boolean getValue() {
                return null;
            }
        }));
        checkNotNull(pageEntry);
    }

    @Test
    public void testCreateSpinner() throws Exception {
        PageEntry<?> pageEntry = hoodAPI.createSpinnerEntry(new SingleSelectListConfigAction("label", new SpinnerValue<List<SpinnerElement>, SpinnerElement>() {
            @Override
            public void onChange(SpinnerElement value) {

            }

            @Override
            public SpinnerElement getValue() {
                return null;
            }

            @Override
            public List<SpinnerElement> getAllPossibleValues() {
                return null;
            }
        }));
        checkNotNull(pageEntry);
    }

    @Test
    public void testCreatePages() throws Exception {
        Config config = Config.newBuilder()
                .setShowZebra(false)
                .setAutoRefresh(true)
                .setLogTag("smth")
                .build();
        Pages pages = hoodAPI.createPages(config);
        assertEquals(config, pages.getConfig());
        assertEquals(0, pages.size());
        assertEquals(pages.size(), pages.getAll().size());
    }

    private static void checkNotNull(PageEntry<?> pageEntry) {
        assertNotNull(pageEntry.getValue());
        assertNotNull(pageEntry);
        assertNotNull(pageEntry.createViewTemplate());
    }
}
