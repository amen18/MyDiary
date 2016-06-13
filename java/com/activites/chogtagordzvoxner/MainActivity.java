package com.activites.chogtagordzvoxner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.DAL.manager.DB_Note;
import com.activites.AddActivity;
import com.adapters.RecyclerViewAdapter;
import com.navigation.R;
import com.DAO.model.NoteListViewModel;

import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DB_Note db = DB_Note.getInstance(this);
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private TextView txt;
    private List<NoteListViewModel> model;
    private Spinner lockeFilterSpinner;
    private int filterPossition = 6;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
//    private ListView listView;
//    private NoteAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(com.navigation.R.id.toolbar);
        setSupportActionBar(toolbar);
//        lockeFilterSpinner = (Spinner) findViewById(R.id.isLockedSpinner);
        ArrayAdapter<String> lockedAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
                new String[]{"All","Locked","Unlocked"});


        lockeFilterSpinner.setAdapter(lockedAdapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(com.navigation.R.id.fab);

        drawer = (DrawerLayout) findViewById(com.navigation.R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, com.navigation.R.string.navigation_drawer_open, com.navigation.R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(com.navigation.R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        txt = (TextView) findViewById(com.navigation.R.id.firstNoteTextView);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        if(db.getAllNotes().isEmpty()){
            txt.setText("Add your first note");
            txt.setTextColor(Color.WHITE);
            txt.setTextSize(20);
            txt.setGravity(View.TEXT_ALIGNMENT_CENTER);
            recyclerView.setVisibility(View.GONE);
            lockeFilterSpinner.setVisibility(View.GONE);
        }else {
            txt.setVisibility(View.GONE);
            if(filterPossition == 2){
                model = db.getNotes(0);
            }else if(filterPossition == 1){
                model = db.getNotes(1);
            }
            else {
                model = db.getAllNotes();
            }

            mAdapter = new RecyclerViewAdapter(model,MainActivity.this);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();

        }


        lockeFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterPossition = position;
                if(filterPossition == 1){
                    model = db.getNotes(1);
                }else if(filterPossition == 2){
                    model = db.getNotes(0);
                }else {
                    model = db.getAllNotes();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if(!db.getAllNotes().isEmpty()) {
                    model = db.getAllNotes();
                    mAdapter = new RecyclerViewAdapter(model,MainActivity.this);
                    recyclerView.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                }
            }

        });
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case com.navigation.R.id.add_note:
                Log.i("+++++++-----", "1111");
                break;

            case com.navigation.R.id.color_styles:
                Log.i("+++++++-----", "2222");

                break;
            case com.navigation.R.id.change_pass:
                Log.i("+++++++-----", "3333");
                break;
            case com.navigation.R.id.search_filter:
                Log.i("+++++++-----", "4444");
                break;
            default:
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void AddNote_Fab(View view){
        Intent i = new Intent(MainActivity.this,AddActivity.class);
        startActivity(i);
    }

    public void datePicker(View view){
//            DatePickerDialog d = new DatePickerDialog(MainActivity.this);
//            d.show(getFragmentManager(),"d");
    }
}
