package com.activites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.Utilities.SecurityManager;
import com.navigation.R;

/**
 * Created by AMEN on 06.05.2016.
 */
public class StartProjectActivity extends AppCompatActivity {
    private LinearLayout text;
    private LinearLayout linerView;
    private EditText password;
    private ImageView saveButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        text = (LinearLayout)findViewById(R.id.text);
        linerView = (LinearLayout)findViewById(R.id.linerView);
        password = (EditText)findViewById(R.id.passEditText);
        saveButton = (ImageView) findViewById(R.id.savePasswordButton);
        saveButton.setVisibility(View.GONE);

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveButton.setVisibility(View.VISIBLE);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if(com.Utilities.SecurityManager.isFirstLoad(StartProjectActivity.this)) {
                            editText_show();
                        } else {
                            Intent i = new Intent(StartProjectActivity.this,HomeActivity.class);
                            startActivity(i);
                            StartProjectActivity.this.overridePendingTransition(R.anim.enter_from_right,
                                    R.anim.neitral_anim);
                        }
                    }
                }, 3000);
    }


    private void editText_show(){


        linerView.setVisibility(View.VISIBLE);
        TranslateAnimation slide = new TranslateAnimation(0, 0, 1500, 0);
        slide.setDuration(1000);
        slide.setFillAfter(true);
        linerView.startAnimation(slide);
    }


    public void savePassword(View view){
        final Animation animationAnim = new AlphaAnimation(10.0f, 0.0f);
        animationAnim.setDuration(250);
        animationAnim.setFillAfter(true);
        view.startAnimation(animationAnim);

        String pass = password.getText().toString();
        if (pass.equals("") || pass.length() < 4) {
            saveButton.setVisibility(View.GONE);
            password.setError("Minimum 4 symbols");
        } else {
            saveButton.setVisibility(View.VISIBLE);
            com.Utilities.SecurityManager.setStartPref(StartProjectActivity.this);
            SecurityManager.setPassword(pass, StartProjectActivity.this);
            Intent i = new Intent(StartProjectActivity.this, HomeActivity.class);
            startActivity(i);
        }
    }

}
