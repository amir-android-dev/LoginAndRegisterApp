package com.ema.loginandregisterapp;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WelcomeFragment extends BaseFragment {
    SharedPreferences sharedPreferences;
    TextView tvName;

    UserAdapter adapter;
    RecyclerView rv;

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
        return inflater.inflate(R.layout.fragment_welcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI(view);
        sayHelloToCurrentLoginUser(tvName);
        setupAdapter();
    }

    @SuppressLint("SetTextI18n")
    private void setupUI(View view) {
        tvName = view.findViewById(R.id.tv_name_welcome);
        rv = view.findViewById(R.id.rv_welcome);

    }

    @SuppressLint("SetTextI18n")
    private void sayHelloToCurrentLoginUser(TextView textView) {
        String username = sharedPreferences.getString(Constants.KEY_SHARED_PREFERENCES_EDIT_LOAD, "");
        textView.setText(getString(R.string.hello) + " " + username);
    }

    //setup Adapter
    private void setupAdapter(){
        List<User> currentlySavedUserList = getAllUsers();

        //setting up the adapter
        adapter = new UserAdapter(currentlySavedUserList, requireContext());
        rv.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rv.setAdapter(adapter);
    }
    public List<User> getAllUsers() {
        //loading the list of users from login acitivity
        String userJson = sharedPreferences.getString(Constants.KEY_SHARED_PREFERENCES_LIST, "");
        //convert the loaded json String to a list of users
        return UserUtil.userListFromString(userJson);
    }
}