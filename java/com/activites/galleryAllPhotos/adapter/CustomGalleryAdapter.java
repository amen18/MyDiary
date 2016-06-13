package com.activites.galleryAllPhotos.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

public class CustomGalleryAdapter extends BaseAdapter {
    public  List<String> list;
    private Context mContext;
    private int screenWidth;

    public CustomGalleryAdapter(Context c) {
        this.mContext = c;
    }

    public CustomGalleryAdapter(Context c, List<String> list, int screenWidth) {
        this.mContext = c;
        this.list = list;
        this.screenWidth = screenWidth;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size(); // длина массива
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String a = list.get(position);
        ImageView imageView = new ImageView(mContext);

        Picasso.with(mContext).load(new File(a)).fit().centerInside().into(imageView);

        int imLay = screenWidth /100*48;

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(imLay, imLay));
//        imageView.setPadding(10, 10, 10, 10);
        return imageView;

    }
}