package com.activites.galleryAllPhotos;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.activites.galleryAllPhotos.adapter.CustomParse;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.messenger.MessengerUtils;
import com.facebook.messenger.ShareToMessengerParams;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.navigation.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;


public class FullImageActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SHARE_TO_MESSENGER = 1;
    private String path;
    private Toolbar toolbar;
    private ImageView imageView;
    private android.support.design.widget.AppBarLayout appBarLayout;
    private boolean showHome = true;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private LoginManager loginManager;
    private ImageView fbButton,okButton,msgButton;
    private String pathNewImage = CustomParse.PATH_EXTERNAL_IMAGE + String.valueOf(System.currentTimeMillis()) + ".jpg";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        AppEventsLogger.activateApp(FullImageActivity.this);
        setContentView(R.layout.full_image);

        printKeyHash(FullImageActivity.this);

        appBarLayout = (android.support.design.widget.AppBarLayout)findViewById(R.id.appBar);
        toolbar  = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageView = (ImageView) findViewById(R.id.full_image_view);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            path = extras.getString("id");
        }
        Picasso.with(this).load(new File(path)).into(imageView);

    }

    public void imageClick(View view){
        if(showHome) {
            showHome = false;
            Animation topAnim = AnimationUtils.loadAnimation(FullImageActivity.this, R.anim.top_enter_anim);
            appBarLayout.setAnimation(topAnim);
            appBarLayout.setVisibility(View.INVISIBLE);
        }
        else {
            showHome = true;
            Animation bottomAnim = AnimationUtils.loadAnimation(FullImageActivity.this, R.anim.bottom_enter_anim);
            appBarLayout.setAnimation(bottomAnim);
            appBarLayout.setVisibility(View.VISIBLE);
        }
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.share_btt:

                dialogShare();
//                DialogFragment shareDialog = new com.facebook_test.ShareDialog();
//                shareDialog.show(getFragmentManager(),shareDialog.getClass().getName());

//                sharePhotoToFacebook();

        }
        return super.onOptionsItemSelected(item);
    }




    public void sharePhotoToFacebook(){
        List<String> permissionNeeds = Arrays.asList("publish_actions");
        loginManager = LoginManager.getInstance();
        loginManager.logInWithPublishPermissions(FullImageActivity.this, permissionNeeds);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .setCaption("nkar")
                .setUserGenerated(true)
                .build();
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();


        ShareLinkContent content1 = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
                .build();

        ShareOpenGraphAction action = new ShareOpenGraphAction.Builder()
                .setActionType("books.reads")
                .putPhoto("image", photo)
                .build();

        shareDialog = new ShareDialog(FullImageActivity.this);
        shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);



//        ShareApi.share(content, null);

    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }





    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }


    public void dialogShare(){
        final Dialog dialog = new Dialog(FullImageActivity.this);
        dialog.setContentView(R.layout.share_dialog_layout);
        dialog.setTitle("Share ");

        fbButton = (ImageView)dialog.findViewById(R.id.fbButton);
        msgButton = (ImageView)dialog.findViewById(R.id.mesButton);
        okButton = (ImageView)dialog.findViewById(R.id.okButton);
        okButton.setVisibility(View.GONE);
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels / 6;
        int margin = metrics.widthPixels /55;
        Log.e("++++++++++111------",screenWidth + "::||||::" + margin);


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

    public void messengerShare(){
        String metadata = "{ \"image\" : \"trees\" }";
        CustomParse customParse = new CustomParse();
        Uri uri = customParse.uriParse( path , pathNewImage);

        ShareToMessengerParams shareToMessengerParams =
                ShareToMessengerParams
                        .newBuilder( uri , "image/*")
                        .setMetaData(metadata)

                        .build();
        MessengerUtils.shareToMessenger(FullImageActivity.this, REQUEST_CODE_SHARE_TO_MESSENGER, shareToMessengerParams);

    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(REQUEST_CODE_SHARE_TO_MESSENGER == requestCode){
//            new File(pathNewImage).delete();
//        }
//    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("-----------STOP","+++++++++++++++++++");
        new File(pathNewImage).delete();
    }
}