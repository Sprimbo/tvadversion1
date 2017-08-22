package com.tvad.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;


public class MoverView extends ImageView {
	private Mover mover;

	public MoverView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode()) { // support visual editor
			Activity activity = (Activity) context;
			DisplayMetrics dm = new DisplayMetrics();
			activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
			mover = new Oglas(getContext(), dm.widthPixels,
					dm.heightPixels, ConfigActivity.CONFIG_COUNT, true);
		}
	}


	public void onDraw(Canvas canvas) {
		if (mover != null) {
			mover.drawFrame(canvas);
		}
	}

	public void setMover(Mover mover) {
		this.mover = mover;
		invalidate();
	}

}