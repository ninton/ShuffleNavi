package jp.co.ninton.shufflenavi.activity.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import jp.co.ninton.shufflenavi.R;
import jp.co.ninton.shufflenavi.util.Log;

public class PacketsUI {
    private static final String TAG = "PacketsUI";

    class ViewHolder {
        View mLayout;
        TextView mPacketName;
        TextView mStackedCount;
        ImageView mBgNormal;
        ImageView mBgCurrent;

        public ViewHolder( View i_layout, TextView i_packetName, TextView i_stackedCount, ImageView i_bgNormal, ImageView i_bgCurrent ) {
            mLayout = i_layout;
            mPacketName = i_packetName;
            mStackedCount = i_stackedCount;
            mBgNormal = i_bgNormal;
            mBgCurrent = i_bgCurrent;
        }
    };

    Context mContext;
    int mNumPackets = 10;
    int mCurrent = -1;
    ViewHolder[] mViewHolders =  new ViewHolder[10];
    AlphaAnimation mAnim;

    public PacketsUI(Context i_context, View i_rootView) {

/*      mDecks[0].mLayout = i_rootView.findViewById(R.id.deck_0);
        mDecks[1].mLayout = i_rootView.findViewById(R.id.deck_1);*/
        mContext = i_context;
        String pkg_name = mContext.getPackageName();
        Resources res = mContext.getResources();

        for ( int i = 0; i < 10; ++i ) {
            String res_name = String.format("deck_%d", i);
            int res_id = res.getIdentifier(res_name, "id", pkg_name);
            View layout = i_rootView.findViewById(res_id);
            TextView packet_name = (TextView)layout.findViewById( R.id.packet_name );
            TextView stacked_cnt = (TextView)layout.findViewById( R.id.stacked_count );
            ImageView bg_normal = (ImageView)layout.findViewById( R.id.packet_bg_normal );
            ImageView bg_current = (ImageView)layout.findViewById( R.id.packet_bg_current );

            mViewHolders[i] = new ViewHolder(layout, packet_name, stacked_cnt, bg_normal, bg_current );
        }

        mAnim = new AlphaAnimation( 0, 1 );
        mAnim.setDuration( 500 );

        init();
    }

    public void init(int i_numPackets) {
        mNumPackets = i_numPackets;
        init();
    }

    public void init () {
        for ( int i = 0; i < 10; ++i ) {
            setPacketName( i,  String.valueOf(i + 1) );
            setStackedCount( i, 0 );
            renderNormal( i );

            boolean f = ( i < mNumPackets );
            visible(i, f);
        }
    }

    public void render( int i_packetIndex, int[] i_stackedCount ) {
        for ( int i = 0; i < mNumPackets; ++i ) {
            setStackedCount(i, i_stackedCount[i]);
        }

        renderNormal(mCurrent);
        mCurrent = i_packetIndex;
        renderCurrent(mCurrent);
    }

    public void setPacketName( int i_index, String i_value ) {
        mViewHolders[i_index].mPacketName.setText(i_value);
    }

    public void setStackedCount( int i_index, int i_value ) {
        String unit;
        if ( i_value <= 1 ) {
            unit = mContext.getString(R.string.stacked_count_unit_singular);
        } else {
            unit = mContext.getString(R.string.stacked_count_unit_plural);
        }
        String s = String.format( "%d %s", i_value, unit );
        mViewHolders[i_index].mStackedCount.setText(s);
    }

    public void visible( int i_index, boolean i_isVisible ) {
        int vis = i_isVisible ? View.VISIBLE : View.INVISIBLE;
        mViewHolders[i_index].mLayout.setVisibility(vis);
    }

    public void renderCurrent( int i_index ) {
        if ( i_index < 0 || mNumPackets <= i_index ) {
            return;
        }
        mViewHolders[i_index].mBgCurrent.setVisibility(View.VISIBLE);
        mViewHolders[i_index].mBgCurrent.startAnimation(mAnim);
    }

    public void renderNormal( int i_index ) {
        if ( i_index < 0 || 10 <= i_index ) {
            return;
        }
        mViewHolders[i_index].mBgCurrent.setVisibility( View.GONE );
    }
}

