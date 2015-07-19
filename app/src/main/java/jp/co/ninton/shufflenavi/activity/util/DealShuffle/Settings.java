package jp.co.ninton.shufflenavi.activity.util.DealShuffle;

import android.content.Context;

import jp.co.ninton.shufflenavi.util.PrefUtil;

public class Settings {
    PrefUtil mPrefUtil;

    public Settings(Context i_context) {
        mPrefUtil = new PrefUtil(i_context, "DealShuffle.Settings");
    }

    public int getNumCards() {
        return mPrefUtil.getInt("NumCards", 52);
    }

    public void putNumCards( int i_value ) {
        mPrefUtil.putInt("NumCards", i_value);
    }

    public int getNumPackets() {
        return mPrefUtil.getInt("NumPackets", 10);
    }

    public void putNumPackets( int i_value ) {
        mPrefUtil.putInt("NumPackets", i_value);
    }

    public int getNumSpeed() {
        return mPrefUtil.getInt("NumSpeed", 3);
    }

    public void putNumSpeed( int i_value ) {
        mPrefUtil.putInt("NumSpeed", i_value);
    }
};

