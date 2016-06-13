package com.activites.chogtagordzvoxner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.Utilities.SecurityManager;
import com.activites.HomeActivity;
import com.navigation.R;

/**
 * Created by Lilit on 06.05.2016.
 */
public class PasswordStartActivity extends Activity {
    private EditText password;
    private ImageView saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_start);
        password = (EditText) findViewById(R.id.editText);
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
//        password.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    saveButton.setVisibility(View.VISIBLE);
//                }
//
//                return false;
//            }
//        });

//        password.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveButton.setVisibility(View.VISIBLE);
//            }
//        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = password.getText().toString();
                if (pass.equals("") || pass.length() < 4) {
                    saveButton.setVisibility(View.GONE);
                    password.setError("Minimum 4 symbols");
                } else {
                    saveButton.setVisibility(View.VISIBLE);
                    SecurityManager.setStartPref(PasswordStartActivity.this);
                    SecurityManager.setPassword(pass, PasswordStartActivity.this);
                    Intent i = new Intent(PasswordStartActivity.this, HomeActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
