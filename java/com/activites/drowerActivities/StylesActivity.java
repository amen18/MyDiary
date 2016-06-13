package com.activites.drowerActivities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;

import com.activites.HomeActivity;
import com.adapters.ColorImageAdapter;
import com.navigation.R;

/**
 * Created by AMEN on 10.05.2016.
 */
public class StylesActivity extends AppCompatActivity{
    private int colorImageBackground;
    private Gallery galleryColor;
    private ImageView selectedImage;
    private ColorImageAdapter galleryImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.styles_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(com.Utilities.SecurityManager.getToolbarColor(StylesActivity.this)));

        selectedImage=(ImageView)findViewById(R.id.imageView);
        galleryColor = (Gallery)findViewById(R.id.galleryColor);
        galleryColor.setSpacing(1);
        galleryColor.startLayoutAnimation();
        galleryImageAdapter = new ColorImageAdapter(this);
        galleryColor.setAdapter(galleryImageAdapter);

        galleryColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                selectedImage.setImageResource(galleryImageAdapter.mImageIds[position]);
                colorImageBackground = galleryImageAdapter.mImageIds[position];
                Log.e("colorpicker++++---", colorImageBackground + "");
            }
        });
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.color_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_save:
                com.Utilities.SecurityManager.setBackgroundColorPref(StylesActivity.this,colorImageBackground);
                Intent iColor = new Intent(StylesActivity.this,HomeActivity.class);
                startActivity(iColor);
                overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
                Log.e("++++++------","save" + colorImageBackground);
                break;
            default:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(StylesActivity.this,ColorStileActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.exit_out_left, R.anim.neitral_anim);
    }
}
