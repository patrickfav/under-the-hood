package at.favre.lib.hood;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.squareup.seismic.ShakeDetector;

import java.util.List;

import at.favre.lib.hood.interfaces.Config;
import at.favre.lib.hood.interfaces.HoodAPI;
import at.favre.lib.hood.interfaces.PageEntry;
import at.favre.lib.hood.interfaces.Pages;
import at.favre.lib.hood.interfaces.Section;
import at.favre.lib.hood.interfaces.actions.BoolConfigAction;
import at.favre.lib.hood.interfaces.actions.ButtonDefinition;
import at.favre.lib.hood.interfaces.actions.OnClickAction;
import at.favre.lib.hood.interfaces.actions.SingleSelectListConfigAction;
import at.favre.lib.hood.interfaces.actions.Stoppable;
import at.favre.lib.hood.interfaces.values.DynamicValue;
import at.favre.lib.hood.noop.HoodNoop;
import at.favre.lib.hood.page.DebugPages;
import at.favre.lib.hood.page.DefaultSection;
import at.favre.lib.hood.page.entries.ActionEntry;
import at.favre.lib.hood.page.entries.ConfigBoolEntry;
import at.favre.lib.hood.page.entries.ConfigSpinnerEntry;
import at.favre.lib.hood.page.entries.HeaderEntry;
import at.favre.lib.hood.page.entries.KeyValueEntry;
import at.favre.lib.hood.page.entries.TextMessageEntry;
import at.favre.lib.hood.util.ArbitraryTapListener;
import timber.log.Timber;

/**
 * This is the main API. Use the factory methods instead of directly
 * instantiating the internal classes.
 */
public final class Hood {
    private static HoodAPI.Extension extensionInstance;
    private static HoodAPI instance;

    private Hood() {
    }

    /**
     * Gets the main API. See {@link HoodAPI}
     *
     * @return singleton instance
     */
    public static HoodAPI get() {
        if (instance == null) {
            if (BuildConfig.NO_OP) {
                instance = new HoodNoop();
            } else {
                instance = new HoodImpl();
            }
        }
        return instance;
    }

    /**
     * Gets the extension API. See {@link at.favre.lib.hood.interfaces.HoodAPI.Extension}
     *
     * @return singleton instance
     */
    public static HoodAPI.Extension ext() {
        if (extensionInstance == null) {
            if (BuildConfig.NO_OP) {
                extensionInstance = new HoodNoop.HoodExtensionNoop();
            } else {
                extensionInstance = new HoodExtensionImpl();
            }
        }
        return extensionInstance;
    }

    private static final class HoodImpl implements HoodAPI {
        /**
         * This constructor will plant a {@link Timber} tree if none is set.
         */
        private HoodImpl() {
            if (Timber.forest().isEmpty()) {
                Timber.plant(new Timber.DebugTree());
            }
        }

        @NonNull
        @Override
        public Pages createPages(@NonNull Config config) {
            return DebugPages.Factory.create(config);
        }

        @Override
        public PageEntry<?> createActionEntry(ButtonDefinition action) {
            return new ActionEntry(action);
        }

        @Override
        public PageEntry<?> createActionEntry(ButtonDefinition actionLeft, ButtonDefinition actionRight) {
            return new ActionEntry(actionLeft, actionRight);
        }

        @Override
        public PageEntry<?> createHeaderEntry(CharSequence header) {
            return new HeaderEntry(header);
        }

        @Override
        public PageEntry<?> createHeaderEntry(CharSequence header, boolean hideInLog) {
            return new HeaderEntry(header, hideInLog);
        }

        @Override
        public PageEntry<?> createMessageEntry(CharSequence message) {
            return new TextMessageEntry(message);
        }

        @Override
        public PageEntry<?> createSwitchEntry(BoolConfigAction action) {
            return new ConfigBoolEntry(action);
        }

        @Override
        public PageEntry<?> createSpinnerEntry(SingleSelectListConfigAction action) {
            return new ConfigSpinnerEntry(action);
        }

        @Override
        public PageEntry<?> createPropertyEntry(CharSequence key, DynamicValue<String> value, OnClickAction action, boolean multiLine) {
            return new KeyValueEntry(key, value, action, multiLine);
        }

        @Override
        public PageEntry<?> createPropertyEntry(CharSequence key, DynamicValue<String> value, boolean multiLine) {
            return new KeyValueEntry(key, value, multiLine);
        }

        @Override
        public PageEntry<?> createPropertyEntry(CharSequence key, DynamicValue<String> value) {
            return new KeyValueEntry(key, value);
        }

        @Override
        public PageEntry<?> createPropertyEntry(CharSequence key, String value, OnClickAction action, boolean multiLine) {
            return new KeyValueEntry(key, value, action, multiLine);
        }

        @Override
        public PageEntry<?> createPropertyEntry(CharSequence key, String value, boolean multiLine) {
            return new KeyValueEntry(key, value, multiLine);
        }

        @Override
        public PageEntry<?> createPropertyEntry(CharSequence key, String value) {
            return new KeyValueEntry(key, value);
        }
    }

    private static final class HoodExtensionImpl implements HoodAPI.Extension {
        private HoodExtensionImpl() {
        }

        @Override
        public Section.ModifiableHeaderSection createSection(String header) {
            return new DefaultSection(header);
        }

        @Override
        public Section.ModifiableHeaderSection createSection(String header, List<PageEntry<?>> entries) {
            return new DefaultSection(header, entries);
        }

        @Override
        public OnClickAction createOnClickActionAskPermission(String perm, Activity activity) {
            return new KeyValueEntry.AskPermissionClickAction(perm, activity);
        }

        @Override
        public OnClickAction createOnClickActionStartIntent(Intent intent) {
            return new KeyValueEntry.StartIntentAction(intent);
        }

        @Override
        public OnClickAction createOnClickActionToast() {
            return new KeyValueEntry.ToastClickAction();
        }

        @Override
        public CharSequence createFullLabel(CharSequence shortLabel, CharSequence fullLabel) {
            return new KeyValueEntry.Label(shortLabel, fullLabel);
        }

        @Override
        public Stoppable registerShakeToOpenDebugActivity(final Context ctx, final Intent intent) {
            final ShakeDetector shakeDetector = new ShakeDetector(new ShakeDetector.Listener() {
                @Override
                public void hearShake() {
                    if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
                        Vibrator vibrator = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(200);
                    }
                    ctx.startActivity(intent);
                }
            });
            shakeDetector.start((SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE));
            return new Stoppable() {
                @Override
                public void stop() {
                    shakeDetector.stop();
                }
            };
        }

        @Override
        public View.OnTouchListener createArbitraryTapListener(int numOfTaps, @NonNull View.OnClickListener onClickListener) {
            return new ArbitraryTapListener(numOfTaps, onClickListener);
        }
    }
}
