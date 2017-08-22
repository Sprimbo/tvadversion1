package com.tvad.android;

import android.graphics.Bitmap;


public class Renderable {
	// Position.
	public float x;
	public float y;
	public float z;
	public float startx;
    public float starty;
    public float startz;

	// Velocity.
	public float velocityX;
	public float velocityY;
	public float velocityZ;

	// Size.
	public float width;
	public float height;

	// alpha
	public int alpha = 255;

	// counter
	public int count;

	// rotation
	public float rotation;

	public Bitmap bitmap;

	public Renderable() {

	}
}
