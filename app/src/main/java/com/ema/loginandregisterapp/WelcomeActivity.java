package com.ema.loginandregisterapp;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActivity extends BaseActivity {
    SharedPreferences sharedPreferences;
    TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        sharedPreferences = getSharedPreferences(Constants.KEY_MAIN_SHARED_PREFERENCES, MODE_PRIVATE);
        setupUI();

    }

    @SuppressLint("SetTextI18n")
    private void setupUI() {
        tvName = findViewById(R.id.tv_name_welcome);

     //  String username = getIntent().getStringExtra(Constants.INTENT_USERNAME);
        String username = sharedPreferences.getString(Constants.KEY_SHARED_PREFERENCES_EDIT_LOAD, "");
        tvName.setText("Hello "+ username);

    }
}