package jp.co.ninton.shufflenavi.activity.util.DealShuffle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import jp.co.ninton.shufflenavi.util.Log;

public class Deck {
    private static final String TAG = "Deck";
    int mNumPackets = 10;
    int mNumCards = 52;
    Integer[] mCards;
    int mPosition = 0;
    Random mRandom = null;

    public Deck() {
        long seed = generateSeed();
        mRandom = new Random( seed );
    }

    public Deck( long i_seed ) {
        mRandom = new Random( i_seed );
    }

    public long generateSeed() {
        long seed = urandom();
        if ( seed == 0 ) {
            seed = System.currentTimeMillis();
        }

        return seed;
    }

    public long urandom() {
        long r = 0L;
        InputStream is = null;

        try {
            is = new FileInputStream("/dev/urandom");
            long r0 = (long)is.read();
            long r1 = (long)is.read();
            long r2 = (long)is.read();
            long r3 = (long)is.read();
            Log.v(TAG, String.format("urandom=%02x %02x %02x %02x", r0, r1, r2, r3));
            r1 <<= 8;
            r2 <<= 16;
            r3 <<= 24;
            r = r0 | r1 | r2 | r3;
            Log.v(TAG, String.format("r=%08x", r));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if ( is != null ) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return r;
    }

    public int getPosition() {
        return mPosition;
    }

    public void shuffle( int i_numCards, int i_numPackets ) {
        mNumCards = i_numCards;
        mNumPackets = i_numPackets;

        if ( mCards != null ) {
            mCards = null;
        }
        mCards = new Integer[mNumCards];

        shuffleCards_v1();

        mPosition = 0;
    }

    public void shuffleCards_v1() {
        // 10パケットの枚数を揃えない方法
        // 隣り合ったカードが隣り合ったままの確率は、1/10

        for ( int i = 0; i < mNumCards; ++i ) {
            mCards[i] = mRandom.nextInt(mNumPackets);
        }
    }

    public void shuffleCards_v0() {
        // 10パケットの枚数がなるべく同じにする方法
        // 52枚を10パケットに配る場合、0, 1, 2, 3 ... 9, 0, 1, 2, 3...9, 0, 1 と初期化しておき、シャッフルする。
        // 6枚 x 2パケ,5枚 x 8パケ
        for ( int i = 0; i < mNumCards; ++i ) {
            mCards[i] = i % mNumPackets;
        }

        List<Integer> list = Arrays.asList(mCards);
        Collections.shuffle(list);
        Integer[] arr = (Integer[]) list.toArray(new Integer[list.size()]);
        mCards = arr;
    }

    public int pull() {
        int pkt_idx = -1;
        if ( mPosition < mNumCards ) {
            pkt_idx = mCards[mPosition];
            ++mPosition;
        }
        return pkt_idx;
    }

    public boolean isEnd() {
        boolean f;

        f = (mNumCards <= mPosition);

        return f;
    }

    public int restCount() {
        return mNumCards - mPosition;
    }

    public int pulledCount() {
        return mPosition;
    }
};

