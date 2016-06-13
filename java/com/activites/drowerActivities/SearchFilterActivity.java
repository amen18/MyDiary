package com.activites.drowerActivities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.activites.HomeActivity;
import com.navigation.R;
import com.squareup.timessquare.CalendarPickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by AMEN on 10.05.2016.
 */
public class SearchFilterActivity extends AppCompatActivity {
    private CalendarPickerView calendar;
    private boolean filtered;
    private long firstDate;
    private long lastDate;
    private List<Date> selectedDates;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_filter_layout);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(com.Utilities.SecurityManager.getToolbarColor(SearchFilterActivity.this)));

        selectedDates = new ArrayList<>();

        Date today = new Date();
        Calendar prevYear = Calendar.getInstance();
        prevYear.add(Calendar.YEAR, -1);

        calendar = (CalendarPickerView) findViewById(R.id.calendar_view);

        calendar.init(prevYear.getTime(), today)
//                .withSelectedDate(today)
           //     .inMode(CalendarPickerView.SelectionMode.RANGE);
     //   calendar.highlightDates(getHolidays());
        .inMode(CalendarPickerView.SelectionMode.MULTIPLE);

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.save_search:
                selectedDates = calendar.getSelectedDates();
                if(selectedDates.isEmpty()){
                    cancle();
                }else {
                    search();
                }
                break;
            case R.id.cancel_search:
                cancle();
                break;
            default:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void search() {
        selectedDates = calendar.getSelectedDates();
        filtered = true;
        firstDate = selectedDates.get(0).getTime();
        lastDate = selectedDates.get(selectedDates.size() - 1).getTime();

        Log.e(" dates----search", selectedDates + "");

        intent =  new Intent(SearchFilterActivity.this,HomeActivity.class);
        intent.putExtra("filtered",filtered);
        intent.putExtra("firstDate", firstDate);
        intent.putExtra("lastDate", lastDate);
        startActivity(intent);
        overridePendingTransition(R.anim.exit_out_left, R.anim.neitral_anim);

    }

    public void cancle() {
        filtered = false;

        intent =  new Intent(SearchFilterActivity.this,HomeActivity.class);
        intent.putExtra("filtered",filtered);
        startActivity(intent);
        overridePendingTransition(R.anim.exit_out_left, R.anim.neitral_anim);

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SearchFilterActivity.this, HomeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.exit_out_left, R.anim.neitral_anim);
    }
}
