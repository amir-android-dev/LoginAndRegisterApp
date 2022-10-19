package com.ema.loginandregisterapp;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.ema.loginandregisterapp.Constants.INTENT_FIRSTNAME;
import static com.ema.loginandregisterapp.Constants.INTENT_LASTNAME;
import static com.ema.loginandregisterapp.Constants.INTENT_PASSWORD;
import static com.ema.loginandregisterapp.Constants.INTENT_USERNAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public  class LoginFragment extends BaseFragment {

    private SharedPreferences sharedPreferences;
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    TextView tvRegister;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        if (activity != null) {
            sharedPreferences = requireActivity().getSharedPreferences(Constants.KEY_MAIN_SHARED_PREFERENCES, MODE_PRIVATE);
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupUI(view);
        onRestoreInstanceState(savedInstanceState);
    }

    private void setupUI(View view) {
        etUsername = view.findViewById(R.id.et_username_login);
        etPassword = view.findViewById(R.id.et_pass_login);
        tvRegister = view.findViewById(R.id.tv_register_login);
        btnLogin = view.findViewById(R.id.btn_login);

        etUsername.setText(loadSavedUserFromPreferences());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String pass = etPassword.getText().toString();

                if (checkIfTheRegisteredUserExist(username, pass)) {
                    displayToast(requireContext(), "successfully registered");
                    openActivity(requireContext(), WelcomeActivity.class);
                    saveUsernameInPreferences(username);
                } else {
                    displayToast(requireContext(), "An error has occurred");
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                openActivity(requireContext(), RegisterActivity.class, username, password);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                displayToast(requireContext(), "Hi " + firstname + " " + lastname);
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


    private void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            etUsername.setText(savedInstanceState.getString(INTENT_USERNAME, ""));
        }
        if (savedInstanceState != null) {
            etPassword.setText(savedInstanceState.getString(INTENT_PASSWORD, ""));
        }

    }

    //shared prefrences
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

    private boolean checkIfTheRegisteredUserExist(String username, String pass) {
        String users = sharedPreferences.getString(Constants.KEY_SHARED_PREFERENCES_LIST, "");
        List<User> usersFromString = UserUtil.userListFromString(users);

        for (User user : usersFromString) {
            if (user.getUsername().equals(username) && user.getPassword().equals(pass)) {
                return true;
            }
        }
        return false;
    }


    protected static LoginFragment newInstance() {
        return new LoginFragment();
    }




}