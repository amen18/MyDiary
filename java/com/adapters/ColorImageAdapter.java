package com.adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.navigation.R;

/**
 * Created by AMEN on 10.05.2016.
 */
public class ColorImageAdapter extends BaseAdapter {
        private Context mContext;

        public ColorImageAdapter(Context context)
        {
            mContext = context;
        }

        public int getCount() {
            return mImageIds.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }


        // Override this method according to your need
        public View getView(int index, View view, ViewGroup viewGroup)
        {
            DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
            int screenWidth = metrics.widthPixels/4;
            // TODO Auto-generated method stub
            ImageView i = new ImageView(mContext);
            i.setImageResource(mImageIds[index]);
            i.setScaleType(ImageView.ScaleType.FIT_CENTER);
            i.setLayoutParams(new Gallery.LayoutParams(screenWidth, screenWidth));
//            i.setLayoutParams(new Gallery.LayoutParams(200, 200));
//            i.setScaleType(ImageView.ScaleType.FIT_XY);

            return i;
        }

        public Integer[] mImageIds = {
                R.drawable.bc00,
                R.drawable.bc1,
                R.drawable.bc2,
                R.drawable.bc3,
                R.drawable.bc7,
                R.drawable.bc8
//                R.drawable.b6,
//                R.drawable.b7,
//                R.drawable.b8,
//                R.drawable.b9,
//                R.drawable.b10,
//                R.drawable.b11,
//                R.drawable.b12
    };

}
