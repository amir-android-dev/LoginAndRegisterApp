package com.ema.loginandregisterapp;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    void displayToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
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



}
