package com.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;

/**
 * Created by Ed on 30.04.2016.
 */
public class ImageManager  {
//    public static byte[] getBytes(Bitmap bitmap) {
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
//        return stream.toByteArray();
//    }

//    public static Bitmap getImage(byte[] image) {
//        return BitmapFactory.decodeByteArray(image, 0, image.length);
//    }


    public static byte[] setBytesBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static Bitmap getImage(byte[] image) {
        Bitmap decode = BitmapFactory.decodeByteArray(image, 0, image.length);
        Matrix matrix = new Matrix();
        matrix.postRotate(0);
        if(decode.getWidth()>decode.getHeight()) {
            matrix.postRotate(90);
        }
        Bitmap resizedBitmap = Bitmap.createBitmap(decode, 0, 0, decode.getWidth(), decode.getHeight(), matrix, true);
        return resizedBitmap;
    }

}
