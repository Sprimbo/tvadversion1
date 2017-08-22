package com.tvad.android;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;

public class BootReceiver extends BroadcastReceiver {

	public static String PREFERENCE_TIMING = "preference.timing";
	@Override
	public void onReceive(Context context, Intent intent) {
		SharedPreferences prefs = context.getSharedPreferences(
				ConfigActivity.PREFS_NAME, Context.MODE_PRIVATE);
		boolean checked = prefs.getBoolean(ConfigActivity.PREFERENCE_ON_OFF,
				false);
		if (checked) { // ON
			AlarmManager alarmManager = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			Intent alarmIntent = new Intent(context, AlarmReceiver.class);
			alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
					0, alarmIntent, 0);

			Calendar calendar = Calendar.getInstance();

			// configure the alarm manager to invoke the mover activity
			// every x mins
			int timer = prefs.getInt(PREFERENCE_TIMING, 3);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
					calendar.getTimeInMillis(), 1000 * 60 * timer, pendingIntent);
		}
	}
}