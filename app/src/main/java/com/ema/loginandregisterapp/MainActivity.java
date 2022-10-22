package com.ema.loginandregisterapp;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //loadLoginFragment();
    }


}

//    void loadLoginFragment() {
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//        LoginFragment loginFragment = LoginFragment.newInstance();
//        fragmentTransaction.replace(R.id.fragment_container_view_main_activity, loginFragment);
//        fragmentTransaction.commit();
//
//    }