package com.activites;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.activites.galleryAllPhotos.adapter.CustomParse;
import com.adapters.imageAdapters.CustomPageAdapter;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.messenger.MessengerUtils;
import com.facebook.messenger.ShareToMessengerParams;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.navigation.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by AMEN on 01.06.2016.
 */
public class MyGalleryActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SHARE_TO_MESSENGER = 11;
    private ArrayList<String> list;
    private CustomPageAdapter mCustomPagerAdapter;
    private ViewPager mViewPager;
    private int position = 0;
    private String description,memText;
    private ImageView fbButton,okButton,msgButton;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    String pathNewImage = CustomParse.PATH_EXTERNAL_IMAGE + String.valueOf(System.currentTimeMillis()) + ".jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.my_custom_gallery);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.viewpager1);
        list = new ArrayList<String>();
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            list = extras.getStringArrayList("list");
            position = extras.getInt("position");
            description = extras.getString("description");
            memText = extras.getString("memText");
        }
        mCustomPagerAdapter = new CustomPageAdapter(MyGalleryActivity.this, list);
        mViewPager.setAdapter(mCustomPagerAdapter);
        mViewPager.setCurrentItem(position);
        mCustomPagerAdapter.notifyDataSetChanged();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mygallery_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shereGallery:
                dialogShare();
                break;
            default:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void dialogShare(){
        final Dialog dialog = new Dialog(MyGalleryActivity.this);
        dialog.setContentView(R.layout.share_dialog_layout);
        dialog.setTitle("Share ");

        fbButton = (ImageView)dialog.findViewById(R.id.fbButton);
        msgButton = (ImageView)dialog.findViewById(R.id.mesButton);
        okButton = (ImageView)dialog.findViewById(R.id.okButton);
        okButton.setVisibility(View.GONE);
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels / 6;
        int margin = metrics.widthPixels /55;
        Log.e("++++++++++111------", screenWidth + "::||||::" + margin);


        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(screenWidth,screenWidth);
        parms.setMargins( margin , 10 , margin , 10 );
        fbButton.setLayoutParams(parms);
        msgButton.setLayoutParams(parms);
        okButton.setLayoutParams(parms);

        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePhotoToFacebook();
                dialog.dismiss();
            }
        });
        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messengerShare();
                Log.e("++++++++++111", "Dialog 11111111111111: ");
                dialog.dismiss();

            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("++++++++++222", "Dialog 222222222222: ");
                dialog.dismiss();

            }
        });
        dialog.show();
    }


    public void sharePhotoToFacebook(){
        shareDialog = new ShareDialog(MyGalleryActivity.this);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(list.get(mViewPager.getCurrentItem()), options);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .setUserGenerated(true)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);

    }

    public void messengerShare(){
        String metadata = "{ \"image\" : \"trees\" }";
        CustomParse customParse = new CustomParse();
        Uri uri = customParse.uriParse(list.get(mViewPager.getCurrentItem()), pathNewImage);

        ShareToMessengerParams shareToMessengerParams =
                ShareToMessengerParams
                        .newBuilder( uri , "image/*")
                        .setMetaData(metadata)
                        .build();
        MessengerUtils.shareToMessenger(MyGalleryActivity.this, REQUEST_CODE_SHARE_TO_MESSENGER, shareToMessengerParams);

    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(MyGalleryActivity.this,InfoActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.exit_out_left, R.anim.neitral_anim);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("-----------destroy", "+++++++++++++++++++");
        new File(pathNewImage).delete();
    }
}
