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
import android.widget.Button;

import com.activites.HomeActivity;
import com.navigation.R;

import petrov.kristiyan.colorpicker.ColorPicker;

/**
 * Created by AMEN on 10.05.2016.
 */
public class ColorStileActivity extends AppCompatActivity {
    private Button btnColorToolbar;
    private Button btnStyle;

//    private int colorPicker;
    private int layoutBackgroundlColor;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_style_layout);
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(com.Utilities.SecurityManager.getToolbarColor(ColorStileActivity.this)));

        btnStyle = (Button)findViewById(R.id.styleBtt);
        btnColorToolbar = (Button)findViewById(R.id.colorColorToolbar);
        btnColorToolbar.setBackgroundColor(com.Utilities.SecurityManager.getToolbarColor(ColorStileActivity.this));
        btnStyle.setBackgroundColor(com.Utilities.SecurityManager.getToolbarColor(ColorStileActivity.this));


//        colorPicker = com.Utilities.SecurityManager.getBackgroundColor(ColorStileActivity.this);
        layoutBackgroundlColor = com.Utilities.SecurityManager.getToolbarColor(ColorStileActivity.this);


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.color_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.save_save:
                com.Utilities.SecurityManager.setToolbarColorPref(ColorStileActivity.this, layoutBackgroundlColor);
                Intent intent = new Intent(ColorStileActivity.this,HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
                break;
            default:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void style(View view){
        if(layoutBackgroundlColor!=0){
            com.Utilities.SecurityManager.setToolbarColorPref(ColorStileActivity.this, layoutBackgroundlColor);
        }
        Intent i = new Intent (ColorStileActivity.this,StylesActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);

    }


    public void btnColorToolbar(View view){
        ColorPicker colorPicker = new ColorPicker(ColorStileActivity.this);
        colorPicker.setColors(new int[]{
                getResources().getColor(R.color.toolcol1),
                getResources().getColor(R.color.toolcol2),
                getResources().getColor(R.color.toolcol3),
                getResources().getColor(R.color.toolcol3_1),
                getResources().getColor(R.color.toolcol4),
                getResources().getColor(R.color.toolcol4_1),
                getResources().getColor(R.color.toolcol5),
                getResources().getColor(R.color.toolcol5_1),
                getResources().getColor(R.color.toolcol6),
                getResources().getColor(R.color.toolcol7),
                getResources().getColor(R.color.toolcol8),
                getResources().getColor(R.color.toolcol9),
                getResources().getColor(R.color.toolcol10),
                getResources().getColor(R.color.toolcol11),
                getResources().getColor(R.color.toolcol12)
        });
        colorPicker.setColumns(5);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                // put code
                Log.e("++++----SETONCHOOS", color + "");
                layoutBackgroundlColor = color ;
                btnColorToolbar.setBackgroundColor(color);
                btnStyle.setBackgroundColor(color);
                actionBar.setBackgroundDrawable(new ColorDrawable(color));

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ColorStileActivity.this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.exit_out_left, R.anim.neitral_anim);
    }
}

