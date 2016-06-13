package com.activites.drowerActivities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.DAL.manager.DB_Note;
import com.activites.HomeActivity;
import com.navigation.R;
import com.statics.Static_Arrays;

import java.io.File;
import java.io.IOException;

/**
 * Created by AMEN on 21.05.2016.
 */
public class SettingsActivity extends AppCompatActivity {
    private DB_Note db = DB_Note.getInstance(this);
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(com.Utilities.SecurityManager.getToolbarColor(SettingsActivity.this)));

        String[] section = {"Delete all notes","Reset settings" };
        ListView listView = (ListView) findViewById(R.id.settings_listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, section);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                     case 0:
                         all_delete();
                    break;
                    case 1:
                        reset_settings();
                        break;
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public  void all_delete(){

        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle(R.string.dialog_fire_missiles);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        Log.e("+++++++++++log  ", "ok");
                        db.deleteAllNotes();
                        String path = Static_Arrays.PATH_EXTERNAL_IMAGE;
                        File direct = new File(path);
                        if (direct.exists()) {
                            String deleteCmd = "rm -r " + path;
                            Runtime runtime = Runtime.getRuntime();
                            try {
                                runtime.exec(deleteCmd);
                            } catch (IOException e) { }
                        }
                        final ProgressDialog pDialog = ProgressDialog.show(SettingsActivity.this, "Deleteing All...", "Please wait", true, false);
                        pDialog.show();
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pDialog.dismiss();
                                Intent intent1 = new Intent(SettingsActivity.this, HomeActivity.class);
                                startActivity(intent1);
                                overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
                            }
                        }, 3000);


                    }
                });
        builder.setNegativeButton("Cancle",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        // DO TASK
                        Log.e("+++++++++++log  ","cancle");

                    }
                });
        builder.show();
    }

    public void reset_settings(){

        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle(R.string.dialog_res_missiles);
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {

                        com.Utilities.SecurityManager.setBackgroundColorPref(SettingsActivity.this, R.drawable.bc8);
                        com.Utilities.SecurityManager.setToolbarColorPref(SettingsActivity.this, getResources().getColor(R.color.colorPrimary));
                        final ProgressDialog pDialog = ProgressDialog.show(SettingsActivity.this, "Deleteing All...", "Please wait", true, false);
                        pDialog.show();
                        new android.os.Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent2 = new Intent(SettingsActivity.this, HomeActivity.class);
                                startActivity(intent2);
                                overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
                                finish();
                            }
                        }, 3000);

                    }
                });
        builder.setNegativeButton("Cancle",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        // DO TASK
                        Log.e("+++++++++++log  ", "cancle");

                    }
                });
        builder.show();

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SettingsActivity.this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.exit_out_left, R.anim.neitral_anim);
        finish();
    }
}
