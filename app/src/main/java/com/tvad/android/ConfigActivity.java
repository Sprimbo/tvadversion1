package com.tvad.android;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ConfigActivity extends Activity {
	public static final String LOG_CAT = "ConfigActivity";
	public static final String PREFS_NAME = "preferences";
	public static String PREFERENCE_TYPE = "preference.type";
	public static String PREFERENCE_TYPE_OGLAS = "preference.type.oglas";
	public static String PREFERENCE_TYPE_DEFAULT = PREFERENCE_TYPE_OGLAS;
	public static String PREFERENCE_TIMING = "preference.timing";
	public static int PREFERENCE_TIMING_DEFAULT = 3;
	public static String PREFERENCE_DURATION = "preference.duration";
	public static int PREFERENCE_DURATION_DEFAULT = 2;
	public static String PREFERENCE_ON_OFF = "preference.onoff";
	public static final int CONFIG_COUNT = 20;
	public static String LAST_TIME_RUN = "last.time.run";
	private MoverView moverView;
	private Spinner typeSpinner;
	private Spinner timingSpinner;
	private Spinner durationSpinner;
	private ToggleButton toggleButton;
	private int width, height;
	private Handler handler = new Handler();
	private boolean changed; // track configuration changes

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.config);

		moverView = (MoverView) findViewById(R.id.moverView);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		width = dm.widthPixels;
		height = dm.heightPixels;

		typeSpinner = (Spinner) findViewById(R.id.spinnerType);
		List<String> typeList = new ArrayList<String>();
		typeList.add(getString(R.string.type_1));
		ArrayAdapter<String> typeDataAdapter = new ArrayAdapter<String>(ConfigActivity.this, android.R.layout.simple_spinner_item, typeList);
		typeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeSpinner.setAdapter(typeDataAdapter);
		typeSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				changed = true;
				String type = PREFERENCE_TYPE_DEFAULT;
				switch (pos) {
				case 0: // oglas
					type = PREFERENCE_TYPE_OGLAS;
					Mover oglas = new Oglas(ConfigActivity.this, width, height, CONFIG_COUNT, true);
					moverView.setMover(oglas);
					break;
				default:
					break;
				}

				SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
				SharedPreferences.Editor edit = prefs.edit();
				edit.putString(PREFERENCE_TYPE, type);
				edit.commit();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		final SharedPreferences preferences = getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
		String type = preferences.getString(PREFERENCE_TYPE, PREFERENCE_TYPE_DEFAULT);
		if (type.equals(PREFERENCE_TYPE_OGLAS)) {
			typeSpinner.setSelection(0); // oglas
		}

		timingSpinner = (Spinner) findViewById(R.id.spinnerTiming);
		List<String> timingList = new ArrayList<String>();
		timingList.add(getString(R.string.timing_1));
		timingList.add(getString(R.string.timing_2));
		timingList.add(getString(R.string.timing_3));
		ArrayAdapter<String> timingDataAdapter = new ArrayAdapter<String>(ConfigActivity.this, android.R.layout.simple_spinner_item, timingList);
		timingDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		timingSpinner.setAdapter(timingDataAdapter);
		timingSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				changed = true;
				int timing = 25;
				switch (pos) {
				case 0: // 3 min
					timing = 25;
					break;
				case 1: // 30 min
					timing = 30;
					break;
				case 2: // 60 min
					timing = 60;
					break;
				default:
					timing = 25;
					break;
				}
				SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
				SharedPreferences.Editor edit = prefs.edit();
				edit.putInt(PREFERENCE_TIMING, timing);
				edit.commit();
				edit.apply();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
		int timing = preferences.getInt(PREFERENCE_TIMING, PREFERENCE_TIMING_DEFAULT);
		switch (timing) {
		case 3: // 3 mins
			timingSpinner.setSelection(0);
			break;
		case 30: // 30 min
			timingSpinner.setSelection(1);
			break;
		case 60: // 60 min
			timingSpinner.setSelection(2);
			break;
		default:
			timingSpinner.setSelection(0);
			break;
		}

		durationSpinner = (Spinner) findViewById(R.id.spinnerDuration);
		List<String> durationList = new ArrayList<String>();
		durationList.add(getString(R.string.duration_1));
		durationList.add(getString(R.string.duration_2));
		durationList.add(getString(R.string.duration_3));
		durationList.add(getString(R.string.duration_4));
		ArrayAdapter<String> durationDataAdapter = new ArrayAdapter<String>(ConfigActivity.this, android.R.layout.simple_spinner_item, durationList);
		durationDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		durationSpinner.setAdapter(durationDataAdapter);
		durationSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				changed = true;
				SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
				SharedPreferences.Editor edit = prefs.edit();
				edit.putInt(PREFERENCE_DURATION, pos + 1);
				edit.commit();
				edit.apply();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}

		});
		int duration = preferences.getInt(PREFERENCE_DURATION, PREFERENCE_DURATION_DEFAULT);
		durationSpinner.setSelection(duration - 1);

		toggleButton = (ToggleButton) findViewById(R.id.onOffButton);
		toggleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				boolean checked = toggleButton.isChecked();
				SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
				SharedPreferences.Editor edit = prefs.edit();
				edit.putBoolean(PREFERENCE_ON_OFF, checked);
				edit.apply();
				edit.commit();

				AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
				Intent alarmIntent = new Intent(ConfigActivity.this, AlarmReceiver.class);
				alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				PendingIntent pendingIntent = PendingIntent.getBroadcast(ConfigActivity.this, 0, alarmIntent, 0);
				if (checked) { // ON
					Calendar calendar = Calendar.getInstance();

					int timing = preferences.getInt(PREFERENCE_TIMING, PREFERENCE_TIMING_DEFAULT);
					// configure the alarm manager to invoke the mover activity every x mins
					alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), timing* 1000 * 6, pendingIntent);
					// set the default for first time run
					edit.putLong(ConfigActivity.LAST_TIME_RUN, System.currentTimeMillis() - timing  * 1000 * 60);
					edit.commit();
					edit.apply();
				} else {
					alarmManager.cancel(pendingIntent);
					pendingIntent.cancel();
				}
			}

		});
		boolean onOff = preferences.getBoolean(PREFERENCE_ON_OFF, false);
		toggleButton.setChecked(onOff);

	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onStop() {
		super.onStop();

	}


	public void onPause() {
		if (changed && toggleButton.isChecked()) {
			// show the current mover if the user changed the config and goes to live TV
			handler.post(new Runnable() {
				public void run() {
					AlarmReceiver.startMover(ConfigActivity.this, true);
				}
			});
		}
		super.onPause();
	}

	public void onResume() {
		super.onResume();
		changed = false;

	}

}
