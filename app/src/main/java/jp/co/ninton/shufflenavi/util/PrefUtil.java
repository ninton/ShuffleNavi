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
    SharedPreferences.Editor mEditor;

    public PrefUtil(Context i_context, String i_name) {
        mContext = i_context;
        mName = i_name;
        mPref = mContext.getApplicationContext().getSharedPreferences(i_name, Context.MODE_PRIVATE);
        mEditor = mPref.edit();
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
        mEditor.putInt(i_key, i_value);
        mEditor.commit();
    }

    public void putBoolean(String i_key, boolean i_value) {
        mEditor.putBoolean(i_key, i_value);
        mEditor.commit();
    }

    public void putString(String i_key, String i_value) {
        mEditor.putString(i_key, i_value);
        mEditor.commit();
    }
}
