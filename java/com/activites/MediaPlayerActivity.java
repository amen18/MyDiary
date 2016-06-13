package com.activites;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.DAL.manager.NoteManager;
import com.DAO.model.NoteMedia;
import com.navigation.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MediaPlayerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private final Handler handler = new Handler();
    TextView tx1,tx2;
    private double startTime = 0;
    private double finalTime = 0;
    Button b1,b2;
    private List<byte[]> mediaList;

    private int position = 0;
    private NoteManager db;
    private int noteId ;
    private ListView listViewMedia;
    private ArrayList<String> mediaNameList;
    private LinearLayout linearMedia,linerList,linerTextView;
    private TextView mediaTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_media);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(com.Utilities.SecurityManager.getToolbarColor(MediaPlayerActivity.this)));

        mediaTextView = (TextView)findViewById(R.id.mediaTextView);
        linearMedia = (LinearLayout)findViewById(R.id.linearMedia);
        linerTextView = (LinearLayout)findViewById(R.id.linerTextView);
        linerList = (LinearLayout)findViewById(R.id.linerList);
        b1 = (Button)findViewById(R.id.play);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        tx1 = (TextView)findViewById(R.id.textView1);
        tx2 = (TextView)findViewById(R.id.textView2);
        listViewMedia = (ListView)findViewById(R.id.list_media);

         Bundle extras = getIntent().getExtras();
        if(extras!=null){
            noteId = extras.getInt("noteId");              // int noteId
        }
        linerTextView.setVisibility(View.GONE);
        mediaTextView.setVisibility(View.GONE);
        mediaPlayer = new MediaPlayer();
        db = new NoteManager(this);
        mediaNameList = new ArrayList<>();
        mediaList = new ArrayList<>();

        for (NoteMedia noteMedia : db.getAllMediaByNoteId(noteId)) {
            mediaList.add(noteMedia.getMediaContent());
            mediaNameList.add("Media" + mediaList.size());
            Log.e("media list ++++--", mediaList.get(mediaList.size() - 1) + "");
        }
        if(mediaList.isEmpty()){
            linerTextView.setVisibility(View.VISIBLE);
            mediaTextView.setVisibility(View.VISIBLE);
            mediaTextView.setTextColor(com.Utilities.SecurityManager.getToolbarColor(MediaPlayerActivity.this));
            mediaTextView.setText("Recordings list is empty!");
            linerList.setVisibility(View.GONE);
            listViewMedia.setVisibility(View.GONE);
            linearMedia.setVisibility(View.GONE);
        }
        else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mediaNameList);
            listViewMedia.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            listViewMedia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                    mediaPlayer.pause();
                    position = pos;
                    take_data(pos);

                }
            });


            seekBar.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (mediaPlayer.isPlaying()) {
                        SeekBar sb = (SeekBar) v;
                        mediaPlayer.seekTo(sb.getProgress());
                    }
                    return false;
                }
            });
        }
    }        //  onCreat



    public  void take_data(int pos){
        try {
            mediaPlayer = new MediaPlayer();
            byte[] b = mediaList.get(pos);

//            Log.e("media byte array-------",b+ "");
            File temp = File.createTempFile("aaa","3ga",getCacheDir());

            temp.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(temp);
            fos.write(b);
            fos.close();
            FileInputStream fis = new FileInputStream(temp);

            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.prepare();
            fis.close();
            musicPlay();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void mNext (View v ){
        int list_size = mediaList.size()-1;
        position = ++position;
        if(position <= list_size) {
            mediaPlayer.reset();
            take_data(position);
        }
        else{
            mediaPlayer.reset();
            position = 0;
            take_data(position);
        }
    }

    public void mBack (View v ){
        int list_size = mediaList.size()-1;
        position = --position;
        if(position >= 0) {
            mediaPlayer.reset();
            take_data(position);
        }
        else {
            mediaPlayer.reset();
            position = list_size;
            take_data(position);
        }
    }

    public  void mPlay(View view){
        if (b1.getText() == getString(R.string.play_str)) {
            b1.setText(getString(R.string.pause_str));
            try {
                musicPlay();
            } catch (IllegalStateException e) {
                musicPause();
            }
        } else {
            b1.setText(getString(R.string.play_str));
            musicPause();
        }
    }
    public  void musicPlay(){
        seekBar.setMax(mediaPlayer.getDuration());
        mediaPlayer.start();
        startPlayProgressUpdater();
        finalTime = mediaPlayer.getDuration();
        startTime = mediaPlayer.getCurrentPosition();
        tx1.setText(String.format(" %d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) startTime), TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));
        tx2.setText(String.format(" %d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) finalTime), TimeUnit.MILLISECONDS.toSeconds((long) finalTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime))));
    }

    public  void musicPause(){
        mediaPlayer.pause();
        startPlayProgressUpdater();
    }

    public void startPlayProgressUpdater(){
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        startTime = mediaPlayer.getCurrentPosition();
        if(mediaPlayer.isPlaying()) {
            Runnable notification = new Runnable() {
                @Override
                public void run() {
                    tx1.setText(String.format(" %d min, %d sec", TimeUnit.MILLISECONDS.toMinutes((long) startTime), TimeUnit.MILLISECONDS.toSeconds((long) startTime) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));
                    startPlayProgressUpdater();
                }
            };
            handler.postDelayed(notification, 1);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mediaPlayer.stop();
        mediaPlayer.reset();
        Intent i = new Intent(this,InfoActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.exit_out_left, R.anim.neitral_anim);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                Log.e("++++++mediaaaaaaaa", "log log log log ");
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
