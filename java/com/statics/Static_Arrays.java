package com.statics;

import android.os.Environment;

import com.adapters.ItemData;
import com.navigation.R;

import java.util.ArrayList;



/**
 * Created by AMEN on 04.05.2016.
 */
public class Static_Arrays {
    public static final String PATH_EXTERNAL_IMAGE =  Environment.getExternalStorageDirectory() + "/MyFile/";
    public static final int NOTE_PAGE_TWO = 2;
    public static final int NOTE_PAGE_THREE = 3;
    public static final int LOCK_NOTES = 1;
    public static final int UNLOCK_NOTES = 0;


    public static  ArrayList<ItemData> lockList = new ArrayList<ItemData>(){
        {
            add(new ItemData("Unlock", R.drawable.unlock_icon));
            add(new ItemData("Lock", R.drawable.lock_icon));
        }
    };


    public static  ArrayList<ItemData> smileList = new ArrayList<ItemData>(){
        {
            add(new ItemData("Very happy", R.drawable.rejoice_smile));
            add(new ItemData("Happy", R.drawable.happy_smile));
            add(new ItemData("All good", R.drawable.all_good_smile));
            add(new ItemData("Love", R.drawable.love_smile));
            add(new ItemData("Surprise", R.drawable.surprised_smile));
            add(new ItemData("Loath", R.drawable.loath_smile));
            add(new ItemData("Weeps", R.drawable.weeps_smile));
        }
    };



}
