package com.tvad.android;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;

// TODO send broadcast while animation is active
public class MainActivity extends Activity {
    private CanvasSurfaceView canvasSurfaceView;
    private Mover mover;
    private boolean finished = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        canvasSurfaceView = new CanvasSurfaceView(this);

        init();

        canvasSurfaceView.setRenderer(mover);
        canvasSurfaceView.setEvent(mover);
        setContentView(canvasSurfaceView);
    }

    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void init() {
        SharedPreferences preferences = getSharedPreferences(ConfigActivity.PREFS_NAME, Activity.MODE_PRIVATE);
        String type = preferences.getString(ConfigActivity.PREFERENCE_TYPE, ConfigActivity.PREFERENCE_TYPE_DEFAULT);
        int duration = preferences.getInt(ConfigActivity.PREFERENCE_DURATION, ConfigActivity.PREFERENCE_DURATION_DEFAULT);
        switch (duration) {
            case 1:
                duration = 15;
                break;
            case 2:
                duration = 30;
                break;
            case 3:
                duration = 45;
                break;
            case 4:
                duration = 60;
                break;
            default:
                duration = 30;
        }

        // Tell other overlay apps
        //((OverlayApplication) getApplicationContext()).setOverlayState(OutgoingReceiver.OVERLAY_INTENT_STATE_STARTED);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        // let the system settle to make the animation smooth
        Runtime r = Runtime.getRuntime();
        r.gc();

            mover = new Oglas(this, dm.widthPixels, dm.heightPixels, duration, false);

    }

    //Recycles all of the bitmaps loaded in onCreate().

    @Override
    protected void onDestroy() {
        super.onDestroy();
        canvasSurfaceView.clearEvent();
        canvasSurfaceView.stopDrawing();
        mover.onDestroy();
    }

    //Finish activity when the user interacts

    protected void doFinish() {
        synchronized (this) {
            if (!finished) {
                finished = true;
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        ((OverlayApplication) getApplicationContext()).setOverlayState(OutgoingReceiver.OVERLAY_INTENT_STATE_STOPPED);
                        MainActivity.this.finish();
                    }

                }).start();
            }
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        doFinish();
        return super.dispatchKeyEvent(e);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        doFinish();
        return super.dispatchTouchEvent(e);
    }

    @Override
    public boolean dispatchGenericMotionEvent(MotionEvent e) {
        doFinish();
        return super.dispatchGenericMotionEvent(e);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            doFinish();
        }
    }

}
