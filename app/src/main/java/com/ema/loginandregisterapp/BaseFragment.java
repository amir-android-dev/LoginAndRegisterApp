package com.ema.loginandregisterapp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

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

    //naviagte
    //login
    public void navigateFromLoginToRegister(String username, String password, View view) {
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment(username, password);
        Navigation.findNavController(view).navigate(action);
    }

    public void navigateFromLoginToWelcome(View view) {
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToWelcomeFragment();
        Navigation.findNavController(view).navigate(action);
    }

    //register
    public void getSendDateFromRegisterFragment(EditText etUsername, EditText etPassword) {
        RegisterFragmentArgs args = RegisterFragmentArgs.fromBundle(getArguments());
        etUsername.setText(args.getUsername());
        etPassword.setText(args.getPassword());
    }
    public void goBackToLoginFragment(View view){
        Navigation.findNavController(view).popBackStack();
    }


}
