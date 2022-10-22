package com.ema.loginandregisterapp;

import static android.content.Context.MODE_PRIVATE;
import static com.ema.loginandregisterapp.Constants.INTENT_FIRSTNAME;
import static com.ema.loginandregisterapp.Constants.INTENT_LASTNAME;
import static com.ema.loginandregisterapp.Constants.INTENT_PASSWORD;
import static com.ema.loginandregisterapp.Constants.INTENT_USERNAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class RegisterFragment extends BaseFragment {
    SharedPreferences sharedPreferences;

    EditText etUsername;
    EditText etPassword;
    EditText etFirstname;
    EditText etLastname;
    Button btnRegister;

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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI(view);
        onRestoreInstanceState(savedInstanceState);
        getSendDateFromLoginFragment(etUsername, etPassword);
    }

    private void setupUI(View view) {

        etUsername = view.findViewById(R.id.et_username_register);
        etPassword = view.findViewById(R.id.et_pass_register);
        etFirstname = view.findViewById(R.id.et_firstName_register);
        etLastname = view.findViewById(R.id.et_lastName_register);
        btnRegister = view.findViewById(R.id.btn_register);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String pass = etPassword.getText().toString();
                String firstname = etFirstname.getText().toString();
                String lastname = etLastname.getText().toString();

                saveUsers(username, pass, firstname, lastname);
                goBackToLoginFragment(view, username, pass, firstname, lastname);

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


    private void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            etUsername.setText(savedInstanceState.getString(INTENT_USERNAME, ""));
        }
        if (savedInstanceState != null) {
            etPassword.setText(savedInstanceState.getString(INTENT_PASSWORD, ""));
        }
        if (savedInstanceState != null) {
            etFirstname.setText(savedInstanceState.getString(INTENT_FIRSTNAME, ""));
        }
        if (savedInstanceState != null) {
            etLastname.setText(savedInstanceState.getString(INTENT_LASTNAME, ""));
        }

    }


    private void saveUsers(String username, String password, String firstname, String lastname) {
        List<User> currentlySaveUserList = loadUsers();
        User user = new User(username, password, firstname, lastname);

        currentlySaveUserList.add(user);
        String userListToString = UserUtil.userListToString(currentlySaveUserList);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_SHARED_PREFERENCES_LIST, userListToString);
        editor.commit();

    }

    private List<User> loadUsers() {
        String userInString = sharedPreferences.getString(Constants.KEY_SHARED_PREFERENCES_LIST, "");
        List<User> users = UserUtil.userListFromString(userInString);
        return users;
    }


}