package com.adapters.imageAdapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.DAO.model.NoteImage;

import java.util.List;

/**
 * Created by AMEN on 23.05.2016.
 */
public class CustomImageGalleryAdapter extends BaseAdapter {
    private Context mContext;
    private List<NoteImage> noteImages;
    private Bitmap decode = null ;

    public CustomImageGalleryAdapter(Context mContext, List<NoteImage> noteImages) {
        this.mContext = mContext;
        this.noteImages = noteImages;
    }

    @Override
    public int getCount() {
        return noteImages.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        if(!noteImages.isEmpty()) {

            String a = noteImages.get(position).getImagePath();
            Log.e("------adapter-----",a);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 12;
            decode = BitmapFactory.decodeFile(a, options);
//            Log.e("-----------",decode + "");

            if(decode!=null) {
                Matrix matrix = new Matrix();
                matrix.postRotate(0);
                if (decode.getWidth() > decode.getHeight()) {
                    matrix.postRotate(90);
                }
                Bitmap resizedBitmap = Bitmap.createBitmap(decode, 0, 0, decode.getWidth(), decode.getHeight(), matrix, true);
                imageView.setImageBitmap(resizedBitmap);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
                int screenWidth = metrics.widthPixels/4;
                imageView.setLayoutParams(new Gallery.LayoutParams(screenWidth, screenWidth));
//                imageView.setLayoutParams(new Gallery.LayoutParams(320, 320));
            }
        }

        return imageView;
    }
}