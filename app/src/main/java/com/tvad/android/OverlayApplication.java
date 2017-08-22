package com.tvad.android;

import android.app.Application;

public class OverlayApplication extends Application {
	private String overlayState = OutgoingReceiver.OVERLAY_INTENT_STATE_STOPPED;
	private boolean otherOverlayAppActive = false;
	private long overlayTime = 0;
	private boolean isEarliestOverlay = true;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public String getOverlayState() {
		return overlayState;
	}

	public void setOverlayState(String state) {
		this.overlayState = state;
		isEarliestOverlay = true;
		otherOverlayAppActive = false;

		// send broadcast to other overlay apps
		if (state.equals(OutgoingReceiver.OVERLAY_INTENT_STATE_QUERY)) {
			OutgoingReceiver.sendOverlayQueryIntent(this);
		} else {
			OutgoingReceiver.sendOverlayIntent(this);
		}
	}

	public boolean isOtherOverlayAppActive() {
		return otherOverlayAppActive;
	}

	public void setOtherOverlayAppActive(boolean otherOverlayAppActive) {
		this.otherOverlayAppActive = otherOverlayAppActive;
	}

	public long getOverlayTime() {
		return overlayTime;
	}

	public void setOverlayTime(long overlayTime) {
		this.overlayTime = overlayTime;
	}
	
	public boolean isEarliestOverlay() {
		return isEarliestOverlay;
	}

	public void setEarliestOverlay(boolean isEarliestOverlay) {
		this.isEarliestOverlay = isEarliestOverlay;
	}

}
