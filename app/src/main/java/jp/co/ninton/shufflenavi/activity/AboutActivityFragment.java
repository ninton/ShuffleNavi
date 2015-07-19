package jp.co.ninton.shufflenavi.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import jp.co.ninton.shufflenavi.util.Log;
import jp.co.ninton.shufflenavi.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AboutActivityFragment extends Fragment {
    private static final String TAG = "AboutActivityFragment";
    View mRootView;

    public AboutActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_about, container, false);

        version_init();

        return mRootView;
    }

    private void version_init() {
        TextView txtv;
        txtv = (TextView) mRootView.findViewById(R.id.about_version);

        PackageInfo pkginfo = null;
        try {
            pkginfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.toString());
        }

        if ( pkginfo != null ) {
            String version = String.format("%s.%s", pkginfo.versionName, pkginfo.versionCode);
            Log.i(TAG, "version=" + version);
            txtv.setText(version);
        }
    }
}
