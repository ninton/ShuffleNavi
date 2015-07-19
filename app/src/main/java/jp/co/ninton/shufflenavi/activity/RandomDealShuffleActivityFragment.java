package jp.co.ninton.shufflenavi.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;

import jp.co.ninton.shufflenavi.R;
import jp.co.ninton.shufflenavi.activity.ui.PacketsUI;
import jp.co.ninton.shufflenavi.activity.util.DealShuffle.Deck;
import jp.co.ninton.shufflenavi.activity.util.DealShuffle.NumberUI;
import jp.co.ninton.shufflenavi.activity.util.DealShuffle.Packets;
import jp.co.ninton.shufflenavi.activity.util.DealShuffle.Settings;
import jp.co.ninton.shufflenavi.util.Log;
import jp.co.ninton.shufflenavi.util.MyTimer;
import jp.co.ninton.shufflenavi.activity.util.DealShuffle.StartButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class RandomDealShuffleActivityFragment extends Fragment {
    static final String TAG = "RandomDealShuffleActivityFragment";

    enum Status {
        READY,
        PLAYING,
        PAUSE,
        FINISH
    }
    enum Event {
        START,
        STOP,
        PAUSE,
        RESTART,
        DESTRIBURE_CARD,
        FINISH
    }
    class Vars {
        boolean mHasFocus = true;
        Status mStatus = Status.READY;
        Deck mDeck = new Deck();
        Packets mPackets = new Packets();
    }

    Settings mSettings = null;
    Vars mVars = new Vars();

    View mRootView;
    PacketsUI mPacketsUI;
    StartButton mStartButton;
    NumberUI mNumSpeedUI;
    NumberUI mNumCardsUI;
    NumberUI mNumPacketsUI;
    MyTimer mMyTimer = null;
    int mTick = 0;

    public RandomDealShuffleActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FlurryAgent.logEvent(TAG);

        mRootView = inflater.inflate(R.layout.fragment_random_deal_shuffle, container, false);
        mSettings = new Settings(getActivity());

        mPacketsUI = new PacketsUI(getActivity().getApplicationContext(), mRootView);

        numSpeedUI_init();
        numCardsUI_init();
        numPacketsUI_init();
        startButton_init();
        myTimer_init();
        btnDebug_init();

        action_stop();

        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mMyTimer.cancel();
    }

    @Override
    public void onPause() {
        super.onPause();
        mVars.mHasFocus = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        mVars.mHasFocus = true;
    }

    private void numSpeedUI_init() {
        mNumSpeedUI = new NumberUI(mRootView, R.id.txt_shuffle_num_speed, R.id.btn_shuffle_num_speed_plus, R.id.btn_shuffle_num_speed_minus);
        mNumSpeedUI.setMaxValue(5);
        mNumSpeedUI.setMinValue(1);
        mNumSpeedUI.setValue(mSettings.getNumSpeed());
        mNumSpeedUI.setOnValueChangedListener(new NumberUI.OnValueChangeListener() {
            @Override
            public void onValueChange(int i_oldVal, int i_newVal) {
                mSettings.putNumSpeed(i_newVal);
            }
        });
    }

    private void numCardsUI_init() {
        mNumCardsUI = new NumberUI(mRootView, R.id.txt_shuffle_num_cards, R.id.btn_shuffle_num_cards_plus, R.id.btn_shuffle_num_cards_minus);
        mNumCardsUI.setMaxValue(132);
        mNumCardsUI.setMinValue(2);
        mNumCardsUI.setValue(mSettings.getNumCards());
        mNumCardsUI.setOnValueChangedListener(new NumberUI.OnValueChangeListener() {
            @Override
            public void onValueChange(int i_oldVal, int i_newVal) {
                mSettings.putNumCards(i_newVal);
                stateTransition( Event.STOP );
            }
        });
    }

    private void numPacketsUI_init() {
        mNumPacketsUI = new NumberUI(mRootView, R.id.txt_shuffle_num_packets, R.id.btn_shuffle_num_packets_plus, R.id.btn_shuffle_num_packets_minus);
        mNumPacketsUI.setMaxValue(10);
        mNumPacketsUI.setMinValue(2);
        mNumPacketsUI.setValue(mSettings.getNumPackets());
        mNumPacketsUI.setOnValueChangedListener(new NumberUI.OnValueChangeListener() {
            @Override
            public void onValueChange(int i_oldVal, int i_newVal) {
                mSettings.putNumPackets(i_newVal);
                stateTransition( Event.STOP );
            }
        });
    }

    private void btnDebug_init() {
        Button btn = (Button) mRootView.findViewById(R.id.btnDebug);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action_distribute_card();
            }
        });
    }

    private void myTimer_init() {
        mMyTimer = new MyTimer(new Handler());

        //  NumSpeed    max_tick    interval_sec
        //      1       15          1.5sec
        //      2       13          1.3sec
        //      3       11          1.1sec
        //      4       9           0.9sec
        //      5       7           0.7sec
        final SparseIntArray max_tick_map = new SparseIntArray();
        max_tick_map.put(1, 15);
        max_tick_map.put(2, 13);
        max_tick_map.put(3, 11);
        max_tick_map.put(4, 9);
        max_tick_map.put(5, 7);

        mMyTimer.start(100, 100, new Runnable() {

            @Override
            public void run() {
                // MyTimer内で、mHandlerにpostされているので、action_start()やaction_stop()と排他的に実行されるはず。
                int max_tick = max_tick_map.get(mSettings.getNumSpeed());
                ++mTick;
                mTick %= max_tick;
                if (mTick == 0) {
                    stateTransition( Event.DESTRIBURE_CARD );
                }
            }
        });
    }

    private void startButton_init() {
        mStartButton = new StartButton( mRootView );
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mStartButton.isOff()) {
                    stateTransition(Event.START);
                } else {
                    stateTransition(Event.PAUSE);
                }
            }
        });
    }

    private void confirm_restart() {
        AlertDialog.Builder bld = new AlertDialog.Builder(getActivity());
        bld.setTitle(R.string.pause);
        bld.setCancelable(true);
        bld.setPositiveButton(R.string.restart, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                stateTransition(Event.RESTART);
            }
        });
        bld.setNegativeButton(R.string.stop, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                stateTransition(Event.STOP);
            }
        });
        bld.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                stateTransition(Event.RESTART);
            }
        });
        bld.create().show();
    }

    private void action_start() {
        if (mVars.mDeck.isEnd()) {
            action_stop();
        }
        mVars.mStatus = Status.PLAYING;
        mStartButton.setOn();
    }

    private void action_stop() {
        mVars.mStatus = Status.READY;
        mStartButton.setOff();

        int num_cards = mSettings.getNumCards();
        int num_packets = mSettings.getNumPackets();
        mVars.mDeck.shuffle(num_cards, num_packets);
        mVars.mPackets.init(num_cards, num_packets);

        mPacketsUI.init(num_packets);
        render();
    }

    synchronized public void action_distribute_card() {
        Log.v(TAG, "distributeCard");

        if ( !mVars.mHasFocus ) {
            return;
        }

        if (mVars.mDeck.isEnd()) {
            stateTransition(Event.FINISH);
            return;
        }

        int pkt_idx = mVars.mDeck.pull();
        mVars.mPackets.stackUp(pkt_idx);

        render();
    }

    private void action_finish() {
        mVars.mStatus = Status.FINISH;

        alert_finish();
    }

    private void alert_finish() {
        AlertDialog.Builder bld = new AlertDialog.Builder(getActivity());
        bld.setTitle(R.string.finish);
        bld.setCancelable(true);
        bld.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                stateTransition(Event.STOP);
            }
        });
        bld.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                stateTransition(Event.STOP);
            }
        });
        bld.create().show();
    }

    private void render() {
        render_packets();
        render_counter();
    }

    private void render_packets() {
        int[] pktarr = mVars.mPackets.getPackets();
        int pkt_idx = mVars.mPackets.getPacketIndex();

        mPacketsUI.render(pkt_idx, pktarr);
    }

    private void render_counter() {
        render_counter(R.id.shuffle_pulled_count, mVars.mDeck.pulledCount());
        render_counter(R.id.shuffle_rest_count, mVars.mDeck.restCount());
    }

    private void render_counter( int i_id, int i_value ) {
        TextView v = (TextView)mRootView.findViewById( i_id );
        String s = String.format("%d", i_value);
        v.setText(s);
    }

    private void message(int i_id) {
        Toast.makeText(getActivity(), i_id, Toast.LENGTH_SHORT).show();
    }

    private void stateTransition( Event i_event ) {
        switch ( mVars.mStatus ) {
            case READY:
                switch ( i_event ) {
                    case START:
                        action_start();
                        break;
                    case STOP:
                        action_stop();
                        break;
                    default:
                        ; // do nothing
                        break;
                }
                break;
            case PLAYING:
                switch ( i_event ) {
                    case PAUSE:
                        mVars.mStatus = Status.PAUSE;
                        confirm_restart();
                        break;
                    case DESTRIBURE_CARD:
                        action_distribute_card();
                        break;
                    case FINISH:
                        action_finish();
                        break;
                    case STOP:
                        action_stop();
                        break;
                    default:
                        ; // do nothing
                        break;
                }
                break;
            case PAUSE:
                switch ( i_event ) {
                    case RESTART:
                        mVars.mStatus = Status.PLAYING;
                        break;
                    case STOP:
                        action_stop();
                        break;
                    default:
                        ; // do nothing
                        break;
                }
                break;
            case FINISH:
                switch ( i_event ) {
                    case PAUSE:
                    case STOP:
                        action_stop();
                        break;
                    default:
                        ; // do nothing
                        break;
                }
                break;
        }
    }
}