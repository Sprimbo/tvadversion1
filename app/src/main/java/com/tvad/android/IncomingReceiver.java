package com.tvad.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class IncomingReceiver extends BroadcastReceiver {
	private static final String LOG_TAG = "IncomingReceiver";

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d(LOG_TAG, "intent=" + intent);
		if (intent.getAction().equals(OutgoingReceiver.OVERLAY_QUERY_INTENT)) {
			if (intent.getExtras() != null
					&& !intent.getExtras().getString(OutgoingReceiver.OVERLAY_INTENT_PACKAGE_NAME, "").equals(OutgoingReceiver.PACKAGE_NAME)) {
				Log.d(LOG_TAG, "state="+((OverlayApplication) context.getApplicationContext()).getOverlayState());
				if (((OverlayApplication) context.getApplicationContext()).getOverlayState().equals(OutgoingReceiver.OVERLAY_INTENT_STATE_QUERY)) {
					long otherTime = intent.getExtras().getLong(OutgoingReceiver.OVERLAY_INTENT_TIME, System.currentTimeMillis());
					Log.d(LOG_TAG, "time: "+otherTime+" vs "+((OverlayApplication) context.getApplicationContext()).getOverlayTime());
					if (otherTime > ((OverlayApplication) context.getApplicationContext()).getOverlayTime()) {
						// our query time is earlier than their query time
						((OverlayApplication) context.getApplicationContext()).setEarliestOverlay(true);
					} else {
						((OverlayApplication) context.getApplicationContext()).setEarliestOverlay(false);
					}
				} else {
					OutgoingReceiver.sendOverlayIntent(context);
				}
			}
		}
	}
}