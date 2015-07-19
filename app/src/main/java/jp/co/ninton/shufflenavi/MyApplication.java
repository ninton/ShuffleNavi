package jp.co.ninton.shufflenavi;

import android.app.Application;

import com.flurry.android.FlurryAgent;

import jp.co.ninton.shufflenavi.util.Log;

/**
 * Created by kuma on 2015/06/27.
 */
public class MyApplication extends Application {
    static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        flurry_init();
    }

    private void flurry_init() {
        FlurryAgent.setLogLevel(Log.VERBOSE);
        FlurryAgent.setLogEnabled(true);

        String api_key = getFlurryApiKey();
        if ( !api_key.isEmpty() ) {
            FlurryAgent.init(this, api_key);
        }
    }

    private String getFlurryApiKey() {
        String api_key = "";
        int id;

        try {
            // R.string.flurry_api_key
            id = getResources().getIdentifier("flurry_api_key", "string", getPackageName());
            Log.i(TAG, "id=" + id);
            api_key = getString(id);
            Log.i(TAG, "flurry_api_key=" + api_key);
        } catch (Exception e) {
            Log.w(TAG, "flurry_api_key is not defined");
        }
        return api_key;
    }
}