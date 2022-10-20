package com.ema.loginandregisterapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class UserProfileFragment extends Fragment {
    SharedPreferences sharedPreferences;
    TextView tvUsername;
    TextView tvPassword;
    TextView tvFirstname;
    TextView tvLastname;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Activity activity = getActivity();
        if (activity != null) {
            sharedPreferences = requireActivity().getSharedPreferences(Constants.KEY_MAIN_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI(view);
    }

    private void setupUI(View view){
        tvUsername = view.findViewById(R.id.tv_username_userProfile);
        tvPassword = view.findViewById(R.id.tv_pass_userProfile);
        tvFirstname = view.findViewById(R.id.tv_firstname_userProfile);
        tvLastname = view.findViewById(R.id.tv_lastname_userProfile);

    }
}