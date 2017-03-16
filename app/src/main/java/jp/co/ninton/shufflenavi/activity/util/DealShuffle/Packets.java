package jp.co.ninton.shufflenavi.activity.util.DealShuffle;

import java.util.Locale;

import jp.co.ninton.shufflenavi.util.Log;

public class Packets {
    private static final String TAG = "Packets";
    int mNumPackets = -1;
    int[] mPackets = null;
    int mLastPacketIndex = -1;
    int mNumCards = -1;
    int[] mCards = null;
    int mCardLen = 0;

    public Packets() {
    }

    public void init( int i_numCards, int i_numPackets ) {
        if ( mNumPackets != i_numPackets ) {
            mNumPackets = i_numPackets;
            if ( mPackets != null ) {
                mPackets = null;
            }
            mPackets = new int[mNumPackets];
        }

        if ( mNumCards != i_numCards ) {
            mNumCards = i_numCards;
            if ( mCards != null ) {
                mCards = null;
            }
            mCards = new int[mNumCards];
        }

        mCardLen = 0;

        for ( int i = 0; i < mNumPackets; ++i ) {
            mPackets[i] = 0;
        }

        mLastPacketIndex = -1;
    }

    public int[] getPackets() {
        return mPackets;
    }

    public int getPacketIndex() {
        return mLastPacketIndex;
    }

    public void stackUp( int i_packetIndex ) {
        mPackets[i_packetIndex] ++;
        mLastPacketIndex = i_packetIndex;

        Log.v( TAG, String.format(Locale.ENGLISH, "stackUp:i_packetIndex=%d, mCardLen=%d, mCards.length=%d", i_packetIndex, mCardLen, mCards.length) );
        mCards[mCardLen] = i_packetIndex;
        ++mCardLen;
    }
}
