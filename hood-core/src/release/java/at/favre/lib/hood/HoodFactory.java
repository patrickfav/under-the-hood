package at.favre.lib.hood;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.SystemClock;
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
import at.favre.lib.hood.interfaces.actions.ManagerControl;
import at.favre.lib.hood.interfaces.actions.OnClickAction;
import at.favre.lib.hood.interfaces.actions.SingleSelectListConfigAction;
import at.favre.lib.hood.interfaces.values.DynamicValue;
import at.favre.lib.hood.internal.DebugPages;
import at.favre.lib.hood.internal.DefaultSection;
import at.favre.lib.hood.internal.entries.ActionEntry;
import at.favre.lib.hood.internal.entries.ConfigBoolEntry;
import at.favre.lib.hood.internal.entries.ConfigSpinnerEntry;
import at.favre.lib.hood.internal.entries.HeaderEntry;
import at.favre.lib.hood.internal.entries.KeyValueEntry;
import at.favre.lib.hood.internal.entries.SpacerEntry;
import at.favre.lib.hood.internal.entries.TextMessageEntry;
import at.favre.lib.hood.util.ArbitraryTapListener;
import timber.log.Timber;

final class HoodFactory implements HoodAPI.Factory {

    @Override
    public HoodAPI createHoodApi() {
        return new HoodImpl();
    }

    @Override
    public HoodAPI.Extension createHoodApiExtension() {
        return new HoodExtensionImpl();
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

        @Override
        public PageEntry<?> createSpacer() {
            return new SpacerEntry();
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
        public OnClickAction createOnClickActionAskPermission(String androidPermission, Activity activity) {
            return new KeyValueEntry.AskPermissionClickAction(androidPermission, activity);
        }

        @Override
        public OnClickAction createOnClickActionStartIntent(Intent intent) {
            return new KeyValueEntry.StartIntentAction(intent);
        }

        @Override
        public OnClickAction createOnClickActionDialog() {
            return new KeyValueEntry.DialogClickAction();
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
        public ManagerControl registerShakeToOpenDebugActivity(final Context ctx, final Intent intent) {
            final SensorManager sensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
            return new ManagerControl() {
                private boolean isSupported = true;
                private ShakeDetector shakeDetector;

                @Override
                public void start() {
                    shakeDetector = new ShakeDetector(new ShakeDetector.Listener() {
                        private long lastEvent = 0;

                        @Override
                        public void hearShake() {
                            if (SystemClock.elapsedRealtime() - lastEvent < 1000) {
                                return;
                            }

                            if (ContextCompat.checkSelfPermission(ctx, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
                                Vibrator vibrator = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
                                if (vibrator.hasVibrator()) {
                                    vibrator.vibrate(200);
                                }
                            }

                            lastEvent = SystemClock.elapsedRealtime();
                            ctx.startActivity(intent);
                        }
                    });

                    isSupported = shakeDetector.start(sensorManager);
                }

                @Override
                public void stop() {
                    shakeDetector.stop();
                }

                @Override
                public boolean isSupported() {
                    return isSupported;
                }
            };
        }

        @Override
        public View.OnTouchListener createArbitraryTapListener(int numOfTaps, @NonNull View.OnClickListener onClickListener) {
            return new ArbitraryTapListener(numOfTaps, onClickListener);
        }

        @Override
        public Pages createUnmodifiablePages(Pages pages) {
            return DebugPages.Factory.createImmutableCopy(pages);
        }
    }
}
