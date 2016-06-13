package com.activites;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.DAL.manager.NoteManager;
import com.DAO.model.NoteImage;
import com.DAO.model.NoteListViewModel;
import com.DAO.model.NoteMedia;
import com.adapters.imageAdapters.CustomImageGalleryAdapter;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.widget.ShareDialog;
import com.navigation.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Lilit on 30.04.2016.
 */
public class InfoActivity extends AppCompatActivity {
    private TextView title;
    private TextView text;
    private TextView date;
    private LinearLayout layout;
    private int noteId;
    private Gallery gallery;
    private CustomImageGalleryAdapter adapter;
    private NoteManager db;
    private List<byte[]> mediaList;
    private NoteListViewModel item;
    private List<NoteImage> noteImages;
    private ScrollView scrollVewInfo;
    private ArrayList<String> imageList;
    private CallbackManager callbackManager;
    private ShareDialog shareDialog;
    private String description,memText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(com.Utilities.SecurityManager.getToolbarColor(InfoActivity.this)));
        scrollVewInfo = (ScrollView)findViewById(R.id.scrollVewInfo);
//        scrollVewInfo.setBackgroundResource(com.Utilities.SecurityManager.getBackgroundColor(InfoActivity.this));
        db = new NoteManager(this);
        gallery = (Gallery) findViewById(R.id.infoImageGallery);
        title = (TextView) findViewById(R.id.viewTitle);
        text = (TextView) findViewById(R.id.viewText);
        date = (TextView) findViewById(R.id.viewDate);
      //  layout = (LinearLayout) findViewById(R.id.imageLayout);
        noteImages = new ArrayList<NoteImage>();
        imageList = new ArrayList<String>();
        mediaList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            item = (NoteListViewModel) extras.getSerializable("listItem");
            noteId = item.getId();
            description = item.getTitle();
            memText = db.getMemoryTextByNoteId(item.getId());
            title.setText(description);
            text.setText(memText);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(item.getDate());

            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH)+1;
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);

            if(mMonth<10) {
                date.setText(mDay + ".0" + mMonth + "." + mYear);
            }else {
                date.setText(mDay + "." + mMonth + "." + mYear);
            }

            noteImages = db.getAllImagesByNoteId(noteId);
            for(NoteImage imagePath : noteImages){
                imageList.add(imagePath.getImagePath());
            }
            adapter = new CustomImageGalleryAdapter(this,noteImages);
            gallery.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intentGal = new Intent(InfoActivity.this, MyGalleryActivity.class);
                    intentGal.putStringArrayListExtra("list", imageList);
                    intentGal.putExtra("position", position);
                    intentGal.putExtra("description",description);
                    intentGal.putExtra("memText",memText);

                    startActivity(intentGal);
                    overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
                }
            });

            for(NoteMedia b:db.getAllMediaByNoteId(noteId)) {
                mediaList.add(b.getMediaContent());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.media_play_menu:
                media_btn();
            break;
//            case R.id.share_menu:
//            break;
            case R.id.delete_menu:
                deleteNote();
                break;
            default:
                Log.e("++++++info", "log log log log ");
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteNote(){

        new AlertDialog.Builder(InfoActivity.this)
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteNote(noteId);
                        for (String s : imageList) {
                            File f = new File(s);
                            f.delete();
                        }
                        Intent intent = new Intent(InfoActivity.this, HomeActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(InfoActivity.this, HomeActivity.class);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void media_btn(){
        Intent i = new Intent(InfoActivity.this,MediaPlayerActivity.class);
        i.putExtra("noteId",noteId);
        startActivity(i);
        overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(InfoActivity.this,HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.exit_out_left, R.anim.neitral_anim);
    }



}
