package com.tvad.android;

import android.os.SystemClock;


public class ProfileRecorder {
    // A type for recording actual draw command time.
    public static final int PROFILE_DRAW = 0;
    // A type for recording the time it takes to display the scene.
    public static final int PROFILE_PAGE_FLIP = 1;
    // A type for recording the time it takes to run a single simulation step.
    public static final int PROFILE_SIM = 2;
    // A type for recording the total amount of time spent rendering a frame.
    public static final int PROFILE_FRAME = 3;
    private static final int PROFILE_COUNT = PROFILE_FRAME + 1;
    
    private ProfileRecord[] mProfiles;
    private int mFrameCount;
    
    public static ProfileRecorder sSingleton = new ProfileRecorder();
    
    public ProfileRecorder() {
        mProfiles = new ProfileRecord[PROFILE_COUNT];
        for (int x = 0; x < PROFILE_COUNT; x++) {
            mProfiles[x] = new ProfileRecord();
        }
    }

    public void start(int profileType) {
        if (profileType < PROFILE_COUNT) {
            mProfiles[profileType].start(SystemClock.uptimeMillis());
        }
    }

    public void stop(int profileType) {
        if (profileType < PROFILE_COUNT) {
            mProfiles[profileType].stop(SystemClock.uptimeMillis());
        }
    }

    public void endFrame() {
        mFrameCount++;
    }
    

    public long getAverageTime(int profileType) {
        long time = 0;
        if (profileType < PROFILE_COUNT) {
            time = mProfiles[profileType].getAverageTime(mFrameCount);
        }
        return time;
    }

    public long getMinTime(int profileType) {
        long time = 0;
        if (profileType < PROFILE_COUNT) {
            time = mProfiles[profileType].getMinTime();
        }
        return time;
    }
    

    public long getMaxTime(int profileType) {
        long time = 0;
        if (profileType < PROFILE_COUNT) {
            time = mProfiles[profileType].getMaxTime();
        }
        return time;
    }

    protected class ProfileRecord {
        private long mStartTime;
        private long mTotalTime;
        private long mMinTime;
        private long mMaxTime;
        
        public void start(long time) {
            mStartTime = time;
        }
        
        public void stop(long time) {
            final long timeDelta = time - mStartTime;
            mTotalTime += timeDelta;
            if (mMinTime == 0 || timeDelta < mMinTime) {
                mMinTime = timeDelta;
            }
            if (mMaxTime == 0 || timeDelta > mMaxTime) {
                mMaxTime = timeDelta;
            }
        }
        
        public long getAverageTime(int frameCount) {
            long time = frameCount > 0 ? mTotalTime / frameCount : 0;
            return time;
        }
        
        public long getMinTime() {
            return mMinTime;
        }
        
        public long getMaxTime() {
            return mMaxTime;
        }
        

    }
}
