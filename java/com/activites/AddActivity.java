package com.activites;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.DAL.manager.NoteManager;
import com.DAO.model.Note;
import com.DAO.model.NoteImage;
import com.DAO.model.NoteMedia;
import com.Utilities.MediaManager;
import com.adapters.SpinnerAdapterWhite;
import com.adapters.imageAdapters.CustomImageGalleryAdapter;
import com.navigation.R;
import com.statics.Static_Arrays;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AMEN on 28.04.2016.
 */
public class AddActivity extends AppCompatActivity {
    public static final String JPG_IMAGE = "";
    private static final int REQUEST_REC_WRITE = 100 ;
    @Bind(R.id.title_et) EditText et_title;
    @Bind(R.id.memeryText_et) EditText et_memeryText;
    private NoteManager db;
    private String title,memoryText;
    private boolean isLocked;
    public static final int CAMERA_REQUEST = 1111;
    public static final int GALLERY_REQUEST = 2222;
    private List<NoteImage> noteImages;
    private static int RECORD_REQUEST = 0;
    private Uri audioFileUri;
    private NoteMedia noteMedia;
    private List<NoteMedia> mediaList;
    private CustomImageGalleryAdapter adapter;
    private List<Uri> mediaUriList;
    private Spinner moodSpinner;
    private int imageMood;
    private Note note;
    private long dateInMiliseconds;
    private ScrollView scrollVew;
    private Gallery gallery;
    private List<String> imageList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        final int colorToolbar = com.Utilities.SecurityManager.getToolbarColor(AddActivity.this);
        actionBar.setBackgroundDrawable(new ColorDrawable(colorToolbar));
        if (ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddActivity.this, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, REQUEST_REC_WRITE);
        }

        scrollVew = (ScrollView)findViewById(R.id.scrollVewAdd);
//        scrollVew.setBackgroundResource(com.Utilities.SecurityManager.getBackgroundColor(AddActivity.this));
        db = new NoteManager(this);
        gallery = (Gallery) findViewById(R.id.imageGallery);
        mediaList = new ArrayList<>();
        noteImages = new ArrayList<NoteImage>();
        adapter = new CustomImageGalleryAdapter(this,noteImages);
        gallery.setAdapter(adapter);
        mediaUriList = new ArrayList<>();
        File direct = new File(Environment.getExternalStorageDirectory() + "/MyFile/");
        if (!direct.exists()) {
            direct.mkdirs();
        }

        moodSpinner = (Spinner)findViewById(R.id.mood_spinner);     // mood find the spinner
        SpinnerAdapterWhite adapter=new SpinnerAdapterWhite(AddActivity.this, R.layout.custom_spinner_adapter_black,R.id.txt,Static_Arrays.smileList);
        moodSpinner.setAdapter(adapter);
        moodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        imageMood = R.drawable.rejoice_smile;
                        break;
                    case 1:
                        imageMood = R.drawable.happy_smile ;
                        break;
                    case 2:
                        imageMood = R.drawable.all_good_smile ;
                        break;
                    case 3:
                        imageMood = R.drawable.love_smile ;
                        break;
                    case 4:
                        imageMood = R.drawable.surprised_smile ;
                        break;
                    case 5:
                        imageMood = R.drawable.loath_smile ;
                        break;
                    case 6:
                        imageMood = R.drawable.weeps_smile ;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        Spinner s = (Spinner) menu.findItem(R.id.spinner).getActionView();     // find the spinner
        SpinnerAdapterWhite adapter = new SpinnerAdapterWhite(AddActivity.this, R.layout.custom_adapter_spinner_white,R.id.txt,Static_Arrays.lockList);
        s.setAdapter(adapter);

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                view.setBackgroundColor(getResources().getColor(R.color.white));
                if (position == 1) {
                    isLocked = true;
                } else {
                    isLocked = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.save_note:
                addNote();
                break;
            default:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void addNote(){
        if (!validate()) {
            return;
        }

        title = et_title.getText().toString();
        memoryText = et_memeryText.getText().toString();
        Date date = new Date();
        dateInMiliseconds = date.getTime();
        Log.e("save---------",dateInMiliseconds +"");
        note = new Note();
        note.setTitle(title);
        note.setMemeryText(memoryText);
        note.setDate(dateInMiliseconds);
        note.setIsLocked(isLocked);
        note.setMood(imageMood);

            Note n = db.addNote(note);
            for(NoteImage img:noteImages){
                img.setNoteID(n.getId());
                db.addImage(img);
            }
            for(NoteMedia med:mediaList){
                med.setNoteID(n.getId());
                db.addMedia(med);
            }
        Intent i = new Intent(AddActivity.this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
    }

    public boolean validate() {
        boolean valid = true;
        title = et_title.getText().toString();
        memoryText = et_memeryText.getText().toString();

        if (title.isEmpty()) {
            et_title.setError("Please fill in the field");
            valid = false;
        } else {
            et_title.setError(null);
        }

        if (memoryText.isEmpty()) {
            et_memeryText.setError("Please fill in the field");
            valid = false;
        } else {
            et_memeryText.setError(null);
        }

        return valid;
    }

    public void addImage(View view){

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File imageFile = new File(Static_Arrays.PATH_EXTERNAL_IMAGE , String.valueOf(System.currentTimeMillis()) + JPG_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
                    startActivityForResult(intent, CAMERA_REQUEST);
                    NoteImage image = new NoteImage();
                    image.setImagePath(imageFile.getPath());
                    imageList.add(imageFile.getPath());
                    noteImages.add(image);
                    imagesAllNotif();
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, GALLERY_REQUEST);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();    }

    public void addMedia(View view){
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, RECORD_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == RECORD_REQUEST) {
            audioFileUri = data.getData();
            mediaUriList.add(audioFileUri);
            byte[] bytes = MediaManager.getBytes(audioFileUri,AddActivity.this);
            noteMedia = new NoteMedia();
            noteMedia.setMediaContent(bytes);
            mediaList.add(noteMedia);
//            db.addMedia(noteMedia);

        }

        recuestImage(requestCode,resultCode,data);

//        if(requestCode == CAMERA_REQUEST) {
//                Bitmap bit = (Bitmap)data.getExtras().get("data");
//                byte[] byteArray = ImageManager.setBytesBitmap(bit);
//                NoteImage image = new NoteImage();
//                image.setImagePath(byteArray);
//                noteImages.add(image);
//                //save
//                adapter.notifyDataSetChanged();
//        }
    }

    public void recuestImage(int requestCode,int resultCode,Intent data){
        NoteImage image = new NoteImage();
        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK ){
            if(!noteImages.isEmpty()) {
                final ProgressDialog pDialog = ProgressDialog.show(AddActivity.this, "Downloading Data..", "Please wait", true, false);
                pDialog.show();
                new android.os.Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pDialog.dismiss();
                        imagesAllNotif();
                    }
                }, 3000);
            }

        }

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            File source = new File(picturePath);
            String pathNewImage = Static_Arrays.PATH_EXTERNAL_IMAGE + String.valueOf(System.currentTimeMillis()) + JPG_IMAGE;
            File destination = new File(pathNewImage);
            try {
                if (source.exists()) {
                    if (source.exists()) {
                        FileChannel src = new FileInputStream(source).getChannel();
                        FileChannel dst = new FileOutputStream(destination).getChannel();
                        dst.transferFrom(src, 0, src.size());
                        src.close();
                        dst.close();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageList.add(destination.getPath());
            image.setImagePath(destination.getPath());
            noteImages.add(image);

            final ProgressDialog pDialog = ProgressDialog.show(AddActivity.this, "Downloading Data..", "Please wait", true, false);
            pDialog.show();
            new android.os.Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pDialog.dismiss();
                    imagesAllNotif();
                }
            }, 3000);

        }
    }

    public void imagesAllNotif(){

            if (!noteImages.isEmpty()) {
                adapter = new CustomImageGalleryAdapter(this,noteImages);
                gallery.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(AddActivity.this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.exit_out_left, R.anim.neitral_anim);

    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_REC_WRITE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //start audio recording or whatever you planned to do
            }else if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                if (ActivityCompat.shouldShowRequestPermissionRationale(AddActivity.this, Manifest.permission.CAMERA)) {
                    //Show an explanation to the user *asynchronously*
                    android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
                    builder.setMessage("This permission is important to Camera.")
                            .setTitle("Important permission required");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ActivityCompat.requestPermissions(AddActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_REC_WRITE);
                        }
                    });
                    ActivityCompat.requestPermissions(AddActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_REC_WRITE);

                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        //Show an explanation to the user *asynchronously*
                        android.support.v7.app.AlertDialog.Builder builder1 = new android.support.v7.app.AlertDialog.Builder(this);
                        builder1.setMessage("This permission is important to Write External Storage.")
                                .setTitle("Important permission required");
                        builder1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ActivityCompat.requestPermissions(AddActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_REC_WRITE);
                            }
                        });
                        ActivityCompat.requestPermissions(AddActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_REC_WRITE);
                    }
                    if (ActivityCompat.shouldShowRequestPermissionRationale(AddActivity.this, Manifest.permission.RECORD_AUDIO)) {
                        //Show an explanation to the user *asynchronously*
                        android.support.v7.app.AlertDialog.Builder builder2 = new android.support.v7.app.AlertDialog.Builder(this);
                        builder2.setMessage("This permission is important to record audio.")
                                .setTitle("Important permission required");
                        builder2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ActivityCompat.requestPermissions(AddActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_REC_WRITE);
                            }
                        });
                        ActivityCompat.requestPermissions(AddActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_REC_WRITE);
                    }

                }else{
                    //Never ask again and handle your app without permission.
                }
            }
        }
    }


}
