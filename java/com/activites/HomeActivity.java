package com.activites;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.DAL.manager.DB_Note;
import com.activites.drowerActivities.AboutProgActivity;
import com.activites.drowerActivities.ChangePassActivity;
import com.activites.drowerActivities.ColorStileActivity;
import com.activites.drowerActivities.SearchFilterActivity;
import com.activites.drowerActivities.SettingsActivity;
import com.activites.filters.SampleFragmentPagerAdapter;
import com.activites.galleryAllPhotos.ImagesList;
import com.astuetz.PagerSlidingTabStrip;
import com.navigation.R;

/**
 * Created by AMEN on 09.05.2016.
 */
public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DB_Note db = DB_Note.getInstance(this);
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private PagerSlidingTabStrip tabsStrip;
    private FloatingActionButton fab;
    private ViewPager viewPager;
    private LinearLayout background_lay_home;
    private static final int REQUEST_REC_WRITE = 100 ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(com.navigation.R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        drawer = (DrawerLayout) findViewById(com.navigation.R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.navigation.R.string.navigation_drawer_open, com.navigation.R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(com.navigation.R.id.nav_view);
        navigationView.setBackgroundColor(getResources().getColor(R.color.nav_Color));
        navigationView.setNavigationItemSelectedListener(this);
        background_lay_home = (LinearLayout)findViewById(R.id.background_lay_home);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new SampleFragmentPagerAdapter(getSupportFragmentManager()));
        tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabsStrip.setTextColor(Color.WHITE);
        tabsStrip.setIndicatorColor(Color.WHITE);
        tabsStrip.setViewPager(viewPager);
        colorToolbar();

        tabsStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageSelected(int position) {
                Log.e("++++++", "------");
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, REQUEST_REC_WRITE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_by_day:
                Intent searchIntent = new Intent(HomeActivity.this,SearchFilterActivity.class);
                startActivity(searchIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    public void AddNote_Fab(View view){
        addNote();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_note:
                addNote();
                overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
                break;
            case R.id.color_styles:
                Intent colorIntent = new Intent(HomeActivity.this,ColorStileActivity.class);
                startActivity(colorIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
                break;
            case R.id.change_pass:
                Intent changeIntent = new Intent(HomeActivity.this,ChangePassActivity.class);
                startActivity(changeIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
                break;
            case R.id.search_filter:
                Intent searchIntent = new Intent(HomeActivity.this,SearchFilterActivity.class);
                startActivity(searchIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
                break;
            case R.id.settings:
                Intent settingsIntent = new Intent(HomeActivity.this,SettingsActivity.class);
                startActivity(settingsIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
                break;
            case R.id.galleryMenu:
                Intent shareIntent = new Intent(HomeActivity.this,ImagesList.class);
                startActivity(shareIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
                Log.i("+++++++-----", "6666");
                break;
            case R.id.program:
                Intent aboutIntent = new Intent(HomeActivity.this,AboutProgActivity.class);
                startActivity(aboutIntent);
                overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
                break;
            default:
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addNote(){
        Intent i = new Intent(HomeActivity.this,AddActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
    }


    public void colorToolbar() {
        Log.e("++++++-----colorHome", com.Utilities.SecurityManager.getToolbarColor(HomeActivity.this) + "");

        int colorToolbar = com.Utilities.SecurityManager.getToolbarColor(HomeActivity.this);
        int backgrColor = com.Utilities.SecurityManager.getBackgroundColor(HomeActivity.this);
        toolbar.setBackgroundColor(colorToolbar);
        tabsStrip.setBackgroundColor(colorToolbar);
        fab.setBackgroundTintList(ColorStateList.valueOf(colorToolbar));
        background_lay_home.setBackgroundResource(backgrColor);

        View header = navigationView.getHeaderView(0);
        RelativeLayout nav_view = (RelativeLayout) header.findViewById(R.id.nav_view_lay);
        nav_view.setBackgroundColor(colorToolbar);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_REC_WRITE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //start audio recording or whatever you planned to do
            }else if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.CAMERA)) {
                    //Show an explanation to the user *asynchronously*
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                    builder.setMessage("This permission is important to Camera.")
                            .setTitle("Important permission required");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_REC_WRITE);
                        }
                    });
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_REC_WRITE);

                    if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        //Show an explanation to the user *asynchronously*
                        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(this);
                        builder1.setMessage("This permission is important to Write External Storage.")
                                .setTitle("Important permission required");
                        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_REC_WRITE);
                            }
                        });
                        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_REC_WRITE);
                    }
                    if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.RECORD_AUDIO)) {
                        //Show an explanation to the user *asynchronously*
                        android.support.v7.app.AlertDialog.Builder builder2 = new android.support.v7.app.AlertDialog.Builder(this);
                        builder2.setMessage("This permission is important to record audio.")
                                .setTitle("Important permission required");
                        builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_REC_WRITE);
                            }
                        });
                        ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_REC_WRITE);
                    }

                }else{
                    //Never ask again and handle your app without permission.
                }
            }
        }
    }
}
