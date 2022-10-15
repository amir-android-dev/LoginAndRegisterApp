package com.ema.loginandregisterapp;

import static com.ema.loginandregisterapp.Constants.INTENT_FIRSTNAME;
import static com.ema.loginandregisterapp.Constants.INTENT_LASTNAME;
import static com.ema.loginandregisterapp.Constants.INTENT_PASSWORD;
import static com.ema.loginandregisterapp.Constants.INTENT_USERNAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends BaseActivity {
    EditText etUsername;
    EditText etPassword;
    EditText etFirstname;
    EditText etLastname;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setupUI();
    }


    private void setupUI() {

        etUsername = findViewById(R.id.et_username_register);
        etPassword = findViewById(R.id.et_pass_register);
        etFirstname = findViewById(R.id.et_firstName_register);
        etLastname = findViewById(R.id.et_lastName_register);
        btnRegister = findViewById(R.id.btn_register);


        etUsername.setText(getIntent().getStringExtra(INTENT_USERNAME));
        etPassword.setText(getIntent().getStringExtra(Constants.INTENT_PASSWORD));

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String pass = etPassword.getText().toString();
                String firstname = etFirstname.getText().toString();
                String lastname = etLastname.getText().toString();
                goBackToLoginActivity(username, pass, firstname, lastname);
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(INTENT_USERNAME, etUsername.getText().toString());
        outState.putString(INTENT_PASSWORD, etPassword.getText().toString());
        outState.putString(INTENT_FIRSTNAME, etFirstname.getText().toString());
        outState.putString(INTENT_LASTNAME, etLastname.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        etUsername.setText(savedInstanceState.getString(INTENT_USERNAME, ""));
        etPassword.setText(savedInstanceState.getString(INTENT_PASSWORD, ""));
        etFirstname.setText(savedInstanceState.getString(INTENT_FIRSTNAME, ""));
        etLastname.setText(savedInstanceState.getString(INTENT_LASTNAME, ""));
        super.onRestoreInstanceState(savedInstanceState);
    }
}