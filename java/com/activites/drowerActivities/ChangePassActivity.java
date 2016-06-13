package com.activites.drowerActivities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.Utilities.SecurityManager;
import com.activites.HomeActivity;
import com.navigation.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by AMEN on 10.05.2016.
 */
public class ChangePassActivity extends AppCompatActivity {
    @Bind(R.id.oldPass) EditText passwordOld;
    @Bind(R.id.pass1) EditText password1;
    @Bind(R.id.pass2) EditText password2;
    private String pass,pass1,pass2,passOld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_pass_layout);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(com.Utilities.SecurityManager.getToolbarColor(ChangePassActivity.this)));


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.color_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_save:
                changePass();
                break;
            default:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void changePass(){
        if (!validate()) {
            return;
        }
        pass = password1.getText().toString();
        SecurityManager.setStartPref(ChangePassActivity.this);
        SecurityManager.setPassword(pass, ChangePassActivity.this);
        Intent intent = new Intent(ChangePassActivity.this,HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_from_right, R.anim.neitral_anim);
    }

    public boolean validate() {
        boolean valid = true;
        passOld = passwordOld.getText().toString();
        pass1 = password1.getText().toString();
        pass2 = password2.getText().toString();

        if (!SecurityManager.isPasswordCorrect(passOld,ChangePassActivity.this)) {
            passwordOld.setError("Please fill in the field");
            valid = false;
        } else {
            passwordOld.setError(null);
        }

        if (pass1.equals("") || pass1.length()<4){
            password1.setError("Type correct password");
            valid = false;
        } else {
            password1.setError(null);
        }

        if (pass2.equals("") || pass2.length()<4){
            password2.setError("Type correct password");
            valid = false;
        } else {
            password2.setError(null);
        }

        if (!pass1.equals(pass2)){
            password1.setError("Type correct password");
            password2.setError("Type correct password");
            valid = false;
        } else {
            password1.setError(null);
            password2.setError(null);
        }
        return valid;
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ChangePassActivity.this,HomeActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.exit_out_left, R.anim.neitral_anim);
        finish();
    }
}
