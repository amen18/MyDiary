package com.adapters.imageAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.navigation.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * Created by AMEN on 30.05.2016.
 */
public class CustomPageAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private List<String> list;
    private Bitmap decode = null ;
    private Animation zoomAmim;
    private boolean showHome = true;


    public CustomPageAdapter(Context context, List<String> list) {
        this.mContext = context;
        this.list = list;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((FrameLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
        final FrameLayout frame = (FrameLayout) itemView.findViewById(R.id.background_lay_home);
        final ViewAnimator animator = (ViewAnimator)itemView.findViewById(R.id.animator);
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        if(!list.isEmpty()) {
            String a = list.get(position);
            animator.setDisplayedChild(1);
            Picasso.with(mContext).load(new File(a)).fit().centerInside().into(imageView, new Callback.EmptyCallback() {
                @Override
                public void onSuccess() {
                    frame.setOnTouchListener(new PanAndZoomListener(frame, imageView, PanAndZoomListener.Anchor.TOPLEFT));
                    animator.setDisplayedChild(0);
                }
            });
        }
//
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(showHome) {
//                    showHome = false;
//                    Animation topAnim = AnimationUtils.loadAnimation(mContext, R.anim.top_enter_anim);
//                    linearLayout.setAnimation(topAnim);
//                    linearLayout.setVisibility(View.INVISIBLE);
//                }
//                else {
//                    showHome = true;
//                    Animation bottomAnim = AnimationUtils.loadAnimation(mContext, R.anim.bottom_enter_anim);
//                    linearLayout.setAnimation(bottomAnim);
//                    linearLayout.setVisibility(View.VISIBLE);
//
//                }
//
//            }
//        });

//        homeButton.setOnClickListener(new View.OnClickListener() {
//            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(mContext,InfoActivity.class);
//                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                Bundle translateBundle = ActivityOptions.makeCustomAnimation(mContext,
//                        R.anim.exit_out_left, R.anim.neitral_anim).toBundle();
//                mContext.startActivity(i, translateBundle);
//            }
//        });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
    }
}