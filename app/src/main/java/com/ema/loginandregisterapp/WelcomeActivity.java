package com.ema.loginandregisterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActivity extends BaseActivity {

    TextView tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setupUI();

    }

    private void setupUI() {
        tvName = findViewById(R.id.tv_name_welcome);

        String username = getIntent().getStringExtra(Constants.INTENT_USERNAME);
        tvName.setText("Hello " + username);

    }
}