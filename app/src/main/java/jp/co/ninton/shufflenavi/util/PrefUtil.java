package jp.co.ninton.shufflenavi.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kuma on 2015/06/23.
 */
public class PrefUtil {
    Context mContext;
    String mName;
    SharedPreferences mPref;

    public PrefUtil(Context i_context, String i_name) {
        mContext = i_context;
        mName = i_name;
        mPref = mContext.getApplicationContext().getSharedPreferences(i_name, Context.MODE_PRIVATE);
    }

    public int getInt(String i_key, int i_def_value) {
        return mPref.getInt(i_key, i_def_value);
    }

    public boolean getBoolean(String i_key, boolean i_def_value) {
        return mPref.getBoolean(i_key, i_def_value);
    }

    public String getString(String i_key, String i_def_value) {
        return mPref.getString(i_key, i_def_value);
    }

    public void putInt(String i_key, int i_value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putInt(i_key, i_value);
        editor.apply();
        editor.commit();
    }

    public void putBoolean(String i_key, boolean i_value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putBoolean(i_key, i_value);
        editor.apply();
        editor.commit();
    }

    public void putString(String i_key, String i_value) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(i_key, i_value);
        editor.apply();
        editor.commit();
    }
}
