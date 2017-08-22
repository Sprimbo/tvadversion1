package com.tvad.android;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class OutgoingReceiver extends BroadcastReceiver {
	public static final String OVERLAY_INTENT = "com.tvad.overlay.action.OVERLAY";
	public static final String OVERLAY_QUERY_INTENT = "com.tvad.overlay.action.OVERLAY_QUERY";
	public static final String OVERLAY_INTENT_STATE = "com.tvad.intent.extra.overlay.STATE";
	public static final String OVERLAY_INTENT_STATE_STARTED = "started";
	public static final String OVERLAY_INTENT_STATE_STOPPED = "stopped";
	public static final String OVERLAY_INTENT_STATE_QUERY = "query";
	public static final String OVERLAY_INTENT_PACKAGE_NAME = "com.tvad.intent.extra.overlay.PACKAGE_NAME";
	public static final String OVERLAY_INTENT_SCREEN_RECTANGLE = "com.tvad.intent.extra.overlay.SCREEN_RECTANGLE";
	public static final String OVERLAY_INTENT_DURATION = "com.tvad.intent.extra.overlay.DURATION"; // seconds
	public static final String OVERLAY_INTENT_TIME = "com.tvad.intent.extra.overlay.TIME"; // ms
	public static final String PACKAGE_NAME = "com.tvad.android.overlay";

	@Override
	public void onReceive(Context context, Intent intent) {

		if (intent.getAction().equals(OutgoingReceiver.OVERLAY_INTENT)) {
			if (intent.getExtras() != null
					&& !intent.getExtras().getString(OutgoingReceiver.OVERLAY_INTENT_PACKAGE_NAME, "").equals(OutgoingReceiver.PACKAGE_NAME)) {

				if (intent.getExtras().getString(OutgoingReceiver.OVERLAY_INTENT_STATE, "").equals(OutgoingReceiver.OVERLAY_INTENT_STATE_STARTED)) {
					((OverlayApplication) context.getApplicationContext()).setOtherOverlayAppActive(true);
				}
			}
		}
	}

	public static void sendOverlayIntent(Context context) {
		// send broadcast to other overlay apps
		SharedPreferences preferences = context.getSharedPreferences(ConfigActivity.PREFS_NAME, Activity.MODE_PRIVATE);
		int duration = preferences.getInt(ConfigActivity.PREFERENCE_DURATION, ConfigActivity.PREFERENCE_DURATION_DEFAULT);
		Intent overlayIntent = new Intent();
		overlayIntent.setAction(OutgoingReceiver.OVERLAY_INTENT);
		overlayIntent.putExtra(OutgoingReceiver.OVERLAY_INTENT_PACKAGE_NAME, OutgoingReceiver.PACKAGE_NAME);
		overlayIntent.putExtra(OutgoingReceiver.OVERLAY_INTENT_STATE, ((OverlayApplication) context.getApplicationContext()).getOverlayState());
		overlayIntent.putExtra(OutgoingReceiver.OVERLAY_INTENT_SCREEN_RECTANGLE, "0,0,0,0");
		overlayIntent.putExtra(OutgoingReceiver.OVERLAY_INTENT_DURATION, duration);
		context.sendBroadcast(overlayIntent);
	}

	public static void sendOverlayQueryIntent(Context context) {
		// send query broadcast to other overlay apps
		long currentTime = System.currentTimeMillis();
		((OverlayApplication) context.getApplicationContext()).setOverlayTime(currentTime);
		Intent overlayIntent = new Intent();
		overlayIntent.setAction(OutgoingReceiver.OVERLAY_QUERY_INTENT);
		overlayIntent.putExtra(OutgoingReceiver.OVERLAY_INTENT_PACKAGE_NAME, OutgoingReceiver.PACKAGE_NAME);
		overlayIntent.putExtra(OutgoingReceiver.OVERLAY_INTENT_TIME, currentTime);
		context.sendBroadcast(overlayIntent);
	}

}