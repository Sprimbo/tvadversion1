package com.tvad.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Environment;
import android.os.SystemClock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Calendar;

public class Oglas extends Mover {
    private long lastTime;
    public static int repeat = 0;

    public Oglas(Context context, int width, int height, int duration, boolean config) {
        super(context, width, height, duration, config);
        int count = repeat;

        // Allocate space for the robot sprites
        spriteArray = new Renderable[1];

        bitmaps = getBitmaps();

        int width1 = (width*1000) /2685;
        int heigth1 = (height*1000) / 2394;

        bitmaps[count] = Bitmap.createScaledBitmap(bitmaps[count], width1, heigth1, true);

        Renderable robot = new Renderable();
        robot.bitmap = bitmaps[count];
        robot.width = 64;
        robot.height = 64;

        // Pick a location for this sprite.
        robot.x = (float) width + bitmaps[count].getWidth();
        robot.y = (float) ((height*10) / 35);

        // Add this robot to the spriteArray so it gets drawn
        spriteArray[0] = robot;

        lastTime = SystemClock.uptimeMillis();
        getRepeat();
        Calendar c = Calendar.getInstance();
        long min = c.get(Calendar.MINUTE);

        if (min == 0) {
            repeat = 0;
        }

    }

    public int getRepeat() {
        if (repeat < 19)
            repeat++;
        else {
            repeat = 0;
        }
        return repeat;
    }

    public void doRun() {
        // Perform a single simulation step.
        final long time = SystemClock.uptimeMillis();
        final long timeDelta = time - lastTime;
        lastTime = time;

        for (Renderable object : spriteArray) {
            object.alpha = 255;

            if (object.x > (float) (width - ((width*10) / 27))) {
                object.x = object.x - (width / 40);
            }
        }
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

    private Bitmap loadImageFromStorage(String img) {
        Calendar c = Calendar.getInstance();
        long Hr24 = c.get(Calendar.HOUR_OF_DAY);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        try {
            if (Hr24 == 1) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/01h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 2) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/02h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 3) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/03h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 4) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/04h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 5) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/05h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 6) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/06h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 7) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/07h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 8) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/08h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 9) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/09h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 10) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/10h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 11) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/11h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 12) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/12h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 13) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/13h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 14) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/14h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 15) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/15h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 16) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/16h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 17) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/17h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 18) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/18h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 19) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/19h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 20) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/20h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 21) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/21h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 22) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/22h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 23) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/23h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else if (Hr24 == 0) {
                File f = new File(Environment.getExternalStorageDirectory() + "/DriveSyncFiles/00h/" + img);
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, options);
                return b;
            } else {
                return loadBitmap(R.drawable.oglas_blank);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return loadBitmap(R.drawable.oglas_blank);
        }


    }

    public Bitmap[] getBitmaps() {
        Bitmap[] bitmaps = new Bitmap[20];
        bitmaps[0] = loadImageFromStorage("oglas01.png");
        bitmaps[1] = loadImageFromStorage("oglas02.png");
        bitmaps[2] = loadImageFromStorage("oglas03.png");
        bitmaps[3] = loadImageFromStorage("oglas04.png");
        bitmaps[4] = loadImageFromStorage("oglas05.png");
        bitmaps[5] = loadImageFromStorage("oglas06.png");
        bitmaps[6] = loadImageFromStorage("oglas07.png");
        bitmaps[7] = loadImageFromStorage("oglas08.png");
        bitmaps[8] = loadImageFromStorage("oglas09.png");
        bitmaps[9] = loadImageFromStorage("oglas10.png");
        bitmaps[10] = loadImageFromStorage("oglas11.png");
        bitmaps[11] = loadImageFromStorage("oglas12.png");
        bitmaps[12] = loadImageFromStorage("oglas13.png");
        bitmaps[13] = loadImageFromStorage("oglas14.png");
        bitmaps[14] = loadImageFromStorage("oglas15.png");
        bitmaps[15] = loadImageFromStorage("oglas16.png");
        bitmaps[16] = loadImageFromStorage("oglas17.png");
        bitmaps[17] = loadImageFromStorage("oglas18.png");
        bitmaps[18] = loadImageFromStorage("oglas19.png");
        bitmaps[19] = loadImageFromStorage("oglas20.png");

        return bitmaps;
    }

}
