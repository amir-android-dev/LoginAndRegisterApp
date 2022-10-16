package com.ema.loginandregisterapp;

import static com.ema.loginandregisterapp.Constants.INTENT_FIRSTNAME;
import static com.ema.loginandregisterapp.Constants.INTENT_LASTNAME;
import static com.ema.loginandregisterapp.Constants.INTENT_PASSWORD;
import static com.ema.loginandregisterapp.Constants.INTENT_USERNAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class LoginActivity extends BaseActivity {
    private SharedPreferences sharedPreferences;
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences(Constants.KEY_MAIN_SHARED_PREFERENCES, MODE_PRIVATE);
        setupUI();
    }

    private void setupUI() {
        etUsername = findViewById(R.id.et_username_login);
        etPassword = findViewById(R.id.et_pass_login);
        tvRegister = findViewById(R.id.tv_register_login);
        btnLogin = findViewById(R.id.btn_login);

        etUsername.setText(loadSavedUserFromPreferences());

        //fetchsendUserDataFromRegisterActivity();
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                //openActivity(LoginActivity.this, WelcomeActivity.class, username);
                Intent intent = new Intent(LoginActivity.this, WelcomeActivity.class);
                startActivity(intent);
                saveUsernameInPreferences(username);
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                openActivity(LoginActivity.this, RegisterActivity.class, username, password);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String username = null;
        if (data != null) {
            username = data.getStringExtra(INTENT_USERNAME);
        }
        String pass = null;
        if (data != null) {
            pass = data.getStringExtra(INTENT_PASSWORD);
        }
        String firstname = null;
        if (data != null) {
            firstname = data.getStringExtra(INTENT_FIRSTNAME);
        }
        String lastname = null;
        if (data != null) {
            lastname = data.getStringExtra(INTENT_LASTNAME);
        }
        if (requestCode == Constants.INTENT_START_ACTIVITY_FOR_RESULT && resultCode == RESULT_OK) {
            if (username != null && pass != null && firstname != null && lastname != null) {
                etUsername.setText(username);
                etPassword.setText(pass);
                displayToast(this, "Hi " + firstname + " " + lastname);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(INTENT_USERNAME, etUsername.getText().toString());
        outState.putString(INTENT_PASSWORD, etPassword.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        etUsername.setText(savedInstanceState.getString(INTENT_USERNAME, ""));
        etPassword.setText(savedInstanceState.getString(INTENT_PASSWORD, ""));
        super.onRestoreInstanceState(savedInstanceState);
    }

    //shared preferances
    @SuppressLint("CommitPrefEdits")
    private void saveUsernameInPreferences(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_SHARED_PREFERENCES_EDIT_LOAD, username);
        editor.commit();
    }

    private String loadSavedUserFromPreferences() {
        String username = sharedPreferences.getString(Constants.KEY_SHARED_PREFERENCES_EDIT_LOAD, "");

        if (!username.isEmpty() && username != null) {
            return username;
        } else {
            return "";
        }
    }


}