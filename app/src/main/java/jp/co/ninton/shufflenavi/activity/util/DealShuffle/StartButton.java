package jp.co.ninton.shufflenavi.activity.util.DealShuffle;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import jp.co.ninton.shufflenavi.R;

public class StartButton {
    final int ID_ON = R.string.stop;
    final int ID_OFF = R.string.start;
    TextView mView = null;
    boolean mIsOn = false;
    AlphaAnimation mAnim;

    public StartButton( View i_rootView ) {
        mView = (TextView) i_rootView.findViewById(R.id.btn_start);
        mAnim = new AlphaAnimation( 0, 1 );
        mAnim.setDuration( 300 );
        setOff();
    }

    public void setOnClickListener( View.OnClickListener i_onClickListener ) {
        mView.setOnClickListener(i_onClickListener);
    }

    public void setOnLongClickListener( View.OnLongClickListener i_onLongClickListenr ) {
        mView.setOnLongClickListener(i_onLongClickListenr);
    }

    public void setOn() {
        if ( !mIsOn ) {
            mView.startAnimation(mAnim);
        }
        mIsOn = true;
        mView.setText(ID_ON);
    }

    public void setOff() {
        if ( mIsOn ) {
            mView.startAnimation(mAnim);
        }
        mIsOn = false;
        mView.setText(ID_OFF);
    }

    public boolean isOn() {
        return mIsOn;
    }

    public boolean isOff() {
        return !mIsOn;
    }

}

