package com.tvad.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class AlarmReceiver extends BroadcastReceiver {
	private static final String LOG_TAG = "AlarmReceiver";


	@Override
	public void onReceive(Context context, Intent arg1) {
		Log.d(LOG_TAG, "onReceive");
		startMover(context, false);
	}

	public static void startMover(final Context context, boolean force) {
		final SharedPreferences preferences = context.getSharedPreferences(ConfigActivity.PREFS_NAME, Activity.MODE_PRIVATE);
		int timing = preferences.getInt(ConfigActivity.PREFERENCE_TIMING, ConfigActivity.PREFERENCE_TIMING_DEFAULT);
		Calendar calendar = Calendar.getInstance();
		boolean display = false;
			if(calendar.get(Calendar.MINUTE)%3==0)
			display = true;


		if (force || display) {

			((OverlayApplication) context.getApplicationContext()).setOverlayState(OutgoingReceiver.OVERLAY_INTENT_STATE_QUERY);
			// wait for 5 seconds to see if any other overlay apps are running
			try {
				final Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					public void run() {

						if (!((OverlayApplication) context.getApplicationContext()).isOtherOverlayAppActive()
								&& ((OverlayApplication) context.getApplicationContext()).isEarliestOverlay()) {
							if (true) { //isLiveTv(context
								Log.d(LOG_TAG, "is live tv");
								// only show this during live TV since the
								// activity will block user input
								Intent intent = new Intent(context, MainActivity.class);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								context.startActivity(intent);
							} else {
								Log.d(LOG_TAG, "not live tv");
								((OverlayApplication) context.getApplicationContext()).setOverlayState(OutgoingReceiver.OVERLAY_INTENT_STATE_STOPPED);
								timer.cancel();
							}
						} else {
							Log.d(LOG_TAG, "other overlay app active");
							// keep trying
							((OverlayApplication) context.getApplicationContext()).setOverlayState(OutgoingReceiver.OVERLAY_INTENT_STATE_QUERY);
						}
					}
				}, 5 * 1000, 180 * 1000);
			} catch (Exception e) {
				Log.e(LOG_TAG, "AlarmReceiver timer", e);
			}
		}
	}


}
