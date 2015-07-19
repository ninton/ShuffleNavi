package jp.co.ninton.shufflenavi.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.flurry.android.FlurryAgent;

import jp.co.ninton.shufflenavi.R;
import jp.co.ninton.shufflenavi.util.Log;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    static final String TAG = "MainActivityFragment";
    View mRootView;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if ( FlurryAgent.isSessionActive() ) {
            FlurryAgent.logEvent(TAG);
        }

        mRootView = inflater.inflate(R.layout.fragment_main, container, false);

        item_init();

        return mRootView;
    }

    private void item_init() {
        View.OnTouchListener on_touch_listener = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        v.setBackgroundColor(Color.parseColor("#cfe8f0"));
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        restoreBackground(v);
                        if ( 16 <= Build.VERSION.SDK_INT ) {
                            restoreBackground_api16(v);
                        } else {
                            restoreBackground(v);
                        }
                        break;
                }
                return false;
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            private void restoreBackground_api16(View i_v) {
                i_v.setBackground(null);
            }

            private void restoreBackground(View i_v) {
                i_v.setBackgroundColor(Color.parseColor("#eeeeee"));
            }

        };

        View.OnClickListener on_click_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = null;
                String tag = v.getTag().toString();

                if ( tag.equals("random_deal_shuffle") ) {
                    i = new Intent(getActivity().getApplicationContext(), RandomDealShuffleActivity.class);
                }

                if ( i != null ) {
                    startActivity(i);
                }
            }
        };

        LinearLayout v;
        v = (LinearLayout)mRootView.findViewById(R.id.main_item_0);
        v.setOnTouchListener(on_touch_listener);
        v.setOnClickListener(on_click_listener);
    }
}
