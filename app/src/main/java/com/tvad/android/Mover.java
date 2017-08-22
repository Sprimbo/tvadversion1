package com.tvad.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;

import com.tvad.android.CanvasSurfaceView.Renderer;

import java.io.IOException;
import java.io.InputStream;

public abstract class Mover implements Runnable, Renderer {
	protected Context context;
	protected int width;
	protected int height;
	protected int duration;
	protected boolean config;
	protected static BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
	protected Bitmap[] bitmaps;
	protected Renderable[] spriteArray;
	protected Renderable[] spriteBorder;
	protected Paint paint = new Paint();
	protected long startTime = -1;
	protected boolean finished = false;

	public Mover(Context context, int width, int height, int duration,
			boolean config) {
		this.context = context;
		this.width = width;
		this.height = height;
		this.duration = duration;
		this.config = config;
		//paint.setAntiAlias(true);
		paint.setAlpha(255);
	}

	public void drawFrame(Canvas canvas) {
		if (spriteArray != null) {
			canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
			for (int x = 0; x < spriteArray.length; x++) {
				if (!finished) {
					paint.setAlpha(spriteArray[x].alpha);
				}
				canvas.drawBitmap(spriteArray[x].bitmap, spriteArray[x].x, spriteArray[x].y, paint);

			}
		}
	}

	public void sizeChanged(int width, int height) {
		// nothing to be done
	}

	public void run() {
		if (startTime==-1) {
			startTime = System.currentTimeMillis();
		} else if (!finished){
			if ((System.currentTimeMillis()-startTime)/1000.0f>duration) {
				finished = true;
				paint.setAlpha(255);
			}
		}
		if (!finished)	{
			doRun();
		} else {
			int alpha = paint.getAlpha();
			//alpha = alpha - 5;
			for (int x = 0; x < spriteArray.length; x++) {
				Renderable object = spriteArray[x];

				object.x = object.x + (width/40);
				if (object.x > width) {
					throw new RuntimeException();
				}
			}
			if (alpha < 0) {
				throw new RuntimeException();
			}
			paint.setAlpha(alpha);
		}
	}
	
	public abstract void doRun();

	protected void onDestroy() {
		for (int x = 0; x < bitmaps.length; x++) {
			bitmaps[x].recycle();
			bitmaps[x] = null;
		}
	}

	protected Bitmap loadBitmap(int resourceId) {
		bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		Bitmap bitmap = null;
		if (context != null) {

			InputStream is = context.getResources().openRawResource(resourceId);
			try {
				bitmap = BitmapFactory.decodeStream(is, null, bitmapOptions);
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					// Ignore.
				}
			}
		}

		return bitmap;
	}
}
