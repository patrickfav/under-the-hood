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
import at.favre.lib.hood.interfaces.values.DynamicValue;
import at.favre.lib.hood.interfaces.values.SpinnerElement;
import at.favre.lib.hood.interfaces.values.SpinnerValue;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

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
        checkPageEntry(hoodAPI.createPropertyEntry("key", "value"));
        checkPageEntry(hoodAPI.createPropertyEntry("key", "value", true));

        PageEntry<?> pageEntry = hoodAPI.createPropertyEntry("key", "value", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {

            }
        }, false);
        checkPageEntry(pageEntry);

        pageEntry = hoodAPI.createPropertyEntry("key", new DynamicValue<String>() {
            @Override
            public String getValue() {
                return "value";
            }
        });
        checkPageEntry(pageEntry);

        pageEntry = hoodAPI.createPropertyEntry("key", new DynamicValue.Async<String>() {
            @Override
            public String getValue() {
                return "value";
            }
        });
        checkPageEntry(pageEntry);
    }

    @Test
    public void testCreateAction() throws Exception {
        PageEntry<?> pageEntry = hoodAPI.createActionEntry(new ButtonDefinition("empty", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {

            }
        }));
        checkPageEntry(pageEntry);

        pageEntry = hoodAPI.createActionEntry(new ButtonDefinition("empty", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {

            }
        }), new ButtonDefinition("empty2", new OnClickAction() {
            @Override
            public void onClick(View v, Map.Entry<CharSequence, String> value) {

            }
        }));
        checkPageEntry(pageEntry);
    }

    @Test
    public void testCreateHeader() throws Exception {
        checkPageEntry(hoodAPI.createHeaderEntry("title"));
    }

    @Test
    public void testCreateMessage() throws Exception {
        checkPageEntry(hoodAPI.createMessageEntry("message"));
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
        checkPageEntry(pageEntry);
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
        checkPageEntry(pageEntry);
    }

    @Test
    public void testSpace() throws Exception {
        checkPageEntry(hoodAPI.createSpacer(), true);
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

    private static void checkPageEntry(PageEntry<?> pageEntry, boolean valueNullable) {
        pageEntry.refresh();
        pageEntry.toLogString();
        if (!valueNullable) {
            assertNotNull(pageEntry.getValue());
        }
        assertNotNull(pageEntry);
        assertTrue(pageEntry.getViewType() >= (1 << 16));
        assertNotNull(pageEntry.createViewTemplate());
    }

    private static void checkPageEntry(PageEntry<?> pageEntry) {
        checkPageEntry(pageEntry, false);
    }
}
