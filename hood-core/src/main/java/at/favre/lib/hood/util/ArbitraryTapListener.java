package at.favre.lib.hood.util;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;


/**
 * This an listener which can be used to require more than x taps before the {@link android.view.View.OnClickListener}
 * will be triggered
 */
public class ArbitraryTapListener implements View.OnTouchListener {
    private static final String TAG = ArbitraryTapListener.class.getName();

    private final int targetNumberOfTaps;
    private final View.OnClickListener onClickListener;
    private int numberOfTaps = 0;
    private long lastTapTimeMs = 0;
    private long touchDownMs = 0;

    /**
     * Creates a new tap listener, wraps an {@link android.view.View.OnClickListener}
     *
     * @param targetNumberOfTaps num of consecutive taps until the onClickListener will be triggered
     * @param onClickListener    will be triggered if enough taps are recognized
     */
    public ArbitraryTapListener(int targetNumberOfTaps, @NonNull View.OnClickListener onClickListener) {
        this.targetNumberOfTaps = targetNumberOfTaps;
        this.onClickListener = onClickListener;

        if (targetNumberOfTaps <= 0) {
            throw new IllegalArgumentException("target num taps must be greater or equal than 1");
        }
    }

    /**
     * Creates an listener that will never consume the event
     */
    public ArbitraryTapListener() {
        this.targetNumberOfTaps = 0;
        this.onClickListener = null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (onClickListener == null) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "down");
                touchDownMs = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "up");
                if ((System.currentTimeMillis() - touchDownMs) > ViewConfiguration.getTapTimeout()) {
                    //it was not a tap
                    numberOfTaps = 0;
                    lastTapTimeMs = 0;
                    Log.d(TAG, "reset");
                    break;
                }

                if (numberOfTaps > 0
                        && (System.currentTimeMillis() - lastTapTimeMs) < ViewConfiguration.getDoubleTapTimeout()) {
                    numberOfTaps += 1;
                    Log.d(TAG, "+1");
                } else {
                    Log.d(TAG, "1");
                    numberOfTaps = 1;
                }

                lastTapTimeMs = System.currentTimeMillis();

                if (numberOfTaps >= targetNumberOfTaps) {
                    Log.d(TAG, "goal");
                    onClickListener.onClick(v);
                    numberOfTaps = 0;
                    lastTapTimeMs = 0;
                }
        }
        return true;
    }
}
