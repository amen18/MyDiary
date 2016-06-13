package com.activites;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Gallery;

import com.DAL.manager.NoteManager;
import com.DAO.model.NoteImage;
import com.adapters.imageAdapters.CustomImageGalleryAdapter;
import com.navigation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AMEN on 23.05.2016.
 */
public class GalleryPhotoActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private Gallery gallery;
    private CustomImageGalleryAdapter adapter;
    private int noteId;
    private NoteManager db;
    private List<NoteImage> noteImages;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.navigation.R.layout.galerea_layout);
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(com.Utilities.SecurityManager.getToolbarColor(GalleryPhotoActivity.this)));
        gallery = (Gallery)findViewById(com.navigation.R.id.gallery1);

        Bundle extras = getIntent().getExtras();
        if(extras!=null) {
            noteId = extras.getInt("noteID");
        }
        db = new NoteManager(this);
        noteImages = new ArrayList<NoteImage>();

        noteImages = db.getAllImagesByNoteId(noteId);
        adapter = new CustomImageGalleryAdapter(this,noteImages);
        gallery.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
                default:
                    onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(GalleryPhotoActivity.this,HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.exit_out_left, R.anim.neitral_anim);
        finish();
    }
}
