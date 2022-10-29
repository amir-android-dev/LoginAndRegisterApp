package com.ema.loginandregisterapp.view;

import static android.content.Context.MODE_PRIVATE;
import static com.ema.loginandregisterapp.Constants.INTENT_PASSWORD;
import static com.ema.loginandregisterapp.Constants.INTENT_USERNAME;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ema.loginandregisterapp.Constants;
import com.ema.loginandregisterapp.R;
import com.ema.loginandregisterapp.User;
import com.ema.loginandregisterapp.UserDataStoreImpl;

import java.util.List;
public class LoginFragment extends BaseFragment {
    private SharedPreferences sharedPreferences;
    private UserDataStoreImpl userDataStoreImpl;
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
            userDataStoreImpl = new UserDataStoreImpl(sharedPreferences);
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
        getSendDataFromRegisterFragment(etUsername, etPassword, loadSavedUserFromPreferences());
        setBackButton(getString(R.string.app_name),false);
    }

    private void setupUI(View view) {
        etUsername = view.findViewById(R.id.et_username_login);
        etPassword = view.findViewById(R.id.et_pass_login);
        tvRegister = view.findViewById(R.id.tv_register_login);
        btnLogin = view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString();
                String pass = etPassword.getText().toString();

                if (checkIfTheRegisteredUserExist(username, pass)) {
                    displayToast(requireContext(), "successfully registered");
                    navigateFromLoginToWelcome(view);
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
                navigateFromLoginToRegister(username, password, view);
            }
        });
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
        userDataStoreImpl.saveUsername(username);
    }

    private String loadSavedUserFromPreferences() {
        String username = userDataStoreImpl.loadUser();
        if (!username.isEmpty() && username != null) {
            return username;
        } else {
            return "";
        }
    }

    private boolean checkIfTheRegisteredUserExist(String username, String pass) {
        List<User> usersFromString = userDataStoreImpl.loadUsers();
        for (User user : usersFromString) {
            if (user.getUsername().equals(username) && user.getPassword().equals(pass)) {
                return true;
            }
        }
        return false;
    }

//    protected static LoginFragment newInstance() {
//        return new LoginFragment();
//    }


}