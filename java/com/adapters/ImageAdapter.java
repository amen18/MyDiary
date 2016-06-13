package com.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.DAO.model.NoteImage;
import com.navigation.R;

import java.util.List;

/**
 * Created by Ed on 01.05.2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context context;
    private int itemBackground;

    private List<NoteImage> imageList;

    public ImageAdapter(Context c,List<NoteImage> images)
    {
        context = c;
        // sets a grey background; wraps around the images
        imageList = images;
        TypedArray a = c.obtainStyledAttributes(R.styleable.ImageGallery);
        itemBackground = a.getResourceId(R.styleable.ImageGallery_android_galleryItemBackground, 0);
        a.recycle();
    }

    public int getCount() {
        return imageList.size();
    }
    // returns the ID of an item
    public Object getItem(int position) {
        return position;
    }
    // returns the ID of an item
    public long getItemId(int position) {
        return position;
    }

    // returns an ImageView view
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        if(!imageList.isEmpty())
        {
//            imageView.setImageBitmap(ImageManager.getImage(imageList.get(position).getImagePath()));
            //imageView.setImageResource(imageIDs[position]);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setLayoutParams(new Gallery.LayoutParams(350 , 400));
            imageView.setBackgroundResource(itemBackground);
        }
        return imageView;
    }

}
