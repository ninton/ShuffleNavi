package jp.co.ninton.shufflenavi.activity.util.DealShuffle;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.NumberPicker;
import android.widget.TextView;

import jp.co.ninton.shufflenavi.R;

/**
 * Created by kuma on 2015/06/25.
 */
public class NumberUI {
    public interface OnValueChangeListener {
        public void onValueChange( int i_oldVal, int i_newVal );
    }
    protected int mValue = 0;
    protected int mMinValue = 0;
    protected int mMaxValue = 0;

    protected View mRootView;
    protected TextView mTextView;
    protected TextView mPlusButton;
    protected TextView mMinusButton;
    protected OnValueChangeListener mOnValueChangeListener;

    protected long mTime = 0;

    public NumberUI(View i_rootView, int i_id_textView, int i_id_plusButton, int i_id_minusButton) {
        mRootView = i_rootView;
        mTextView = (TextView) mRootView.findViewById(i_id_textView);
        mPlusButton = (TextView) mRootView.findViewById(i_id_plusButton);
        mMinusButton = (TextView) mRootView.findViewById(i_id_minusButton);

        mPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addValue(1);
            }
        });
        mMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addValue(-1);
            }
        });

        mPlusButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                addValue(9);
                return false;
            }
        });

        mMinusButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                addValue(-9);
                return false;
            }
        });
    }

    public void addValue( int i_delta ) {
        int old_val = mValue;
        setValue( mValue + i_delta );
        if ( old_val != mValue ) {
            mOnValueChangeListener.onValueChange(old_val, mValue );
        }
    }

    public void setMinValue( int i_value ) {
        if ( 0 <= i_value && i_value <= mMaxValue ) {
            mMinValue = i_value;
            if ( mValue <= mMinValue ) {
                mValue = mMinValue;
            }
        }
    }

    public void setMaxValue( int i_value ) {
        if ( 0 <= i_value && mMinValue <= i_value ) {
            mMaxValue = i_value;
            if ( mMaxValue < mValue ) {
                mValue = mMaxValue;
            }
        }
    }

    public void setValue( int i_value ) {
        if ( mMinValue <= i_value && i_value <= mMaxValue ) {
            mValue = i_value;
            mTextView.setText(String.valueOf(mValue));
        }
    }

    public int getValue() {
        return mValue;
    }

    public void setOnValueChangedListener(OnValueChangeListener i_OnValueChangeListener ) {
        mOnValueChangeListener = i_OnValueChangeListener;
    }
}
