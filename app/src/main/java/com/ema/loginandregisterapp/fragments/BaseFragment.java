package com.ema.loginandregisterapp.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.ema.loginandregisterapp.Constants;
import com.ema.loginandregisterapp.User;


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
    //todo login
    public void navigateFromLoginToRegister(String username, String password, View view) {
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment(username, password);
        Navigation.findNavController(view).navigate(action);
    }

    public void navigateFromLoginToWelcome(View view) {
        NavDirections action = LoginFragmentDirections.actionLoginFragmentToWelcomeFragment();
        Navigation.findNavController(view).navigate(action);
    }

    public void getSendDataFromRegisterFragment(EditText etUsername, EditText etPassword, String usernamePreferences) {
        LoginFragmentArgs args = LoginFragmentArgs.fromBundle(getArguments());
        if (args.getUsername().equals("em")) {
            etUsername.setText(usernamePreferences);
            etPassword.setText("");

        } else {
            etUsername.setText(args.getUsername());
            etPassword.setText(args.getPassword());
        }
    }


    //todo register
    public void getSendDateFromLoginFragment(EditText etUsername, EditText etPassword) {
        RegisterFragmentArgs args = RegisterFragmentArgs.fromBundle(getArguments());
        etUsername.setText(args.getUsername());
        etPassword.setText(args.getPassword());
    }

    public void goBackToLoginFragment(View view, String username, String password, String firstname, String lastname) {
        NavDirections action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment(username, password);
        Navigation.findNavController(view).navigate(action);
        displayToast(requireContext(), "Hello " + firstname + " " + lastname);
        // Navigation.findNavController(view).popBackStack();
    }

    //todo welcome

    public void openAndSendDataToUserProfile( View view,User user){
        NavDirections action = WelcomeFragmentDirections.actionWelcomeFragmentToUserProfileFragment(user);
        Navigation.findNavController(view).navigate(action);
        //or
//        NavDirections action = WelcomeFragmentDirections.actionWelcomeFragmentToUserProfileFragment(user);
//        NavHostFragment.findNavController(this).navigate(action);
    }


    //permission
    //it shows the pop up
    public void checkLocationPermissionAndOpenUserProfile(User user) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            openAndSendDataToUserProfile(requireView(),user);
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    //todo userProfile
    public void getAndSetSendDataFromAdapterOfWelcomeFragment(TextView tvUsername, TextView tvPassword, TextView tvFirstname, TextView tvLastname){
        UserProfileFragmentArgs args = UserProfileFragmentArgs.fromBundle(getArguments());
        User user = args.getUser();
        tvUsername.setText(user.getUsername());
        tvPassword.setText(user.getPassword());
        tvFirstname.setText(user.getFirstname());
        tvLastname.setText(user.getLastname());
    }



}
