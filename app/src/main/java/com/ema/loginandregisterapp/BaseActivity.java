package com.ema.loginandregisterapp;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    //todo login go to registerActivity and take the user and pass with you
    public void openActivity(Context context, Class<?> cls, String username) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(Constants.INTENT_USERNAME, username);
        startActivity(intent);

    }
    public void openActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }

    public void openActivity(Context context, Class<?> cls, String username, String pass) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(Constants.INTENT_USERNAME, username);
        intent.putExtra(Constants.INTENT_PASSWORD, pass);
        startActivityForResult(intent, Constants.INTENT_START_ACTIVITY_FOR_RESULT);
    }

    //todo go back to login acitivty and take data with you
    void goBackToLoginActivity(String username, String password, String firstname, String lastname) {
        // create an Intent to pass the updated data back to the LoginActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra(Constants.INTENT_USERNAME, username);
        resultIntent.putExtra(Constants.INTENT_PASSWORD, password);
        resultIntent.putExtra(Constants.INTENT_FIRSTNAME, firstname);
        resultIntent.putExtra(Constants.INTENT_LASTNAME, lastname);
        // set the result and finish this activity
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    void displayToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

}
