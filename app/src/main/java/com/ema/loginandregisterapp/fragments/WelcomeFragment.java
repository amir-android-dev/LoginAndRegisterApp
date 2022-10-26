package com.ema.loginandregisterapp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ema.loginandregisterapp.Constants;
import com.ema.loginandregisterapp.R;
import com.ema.loginandregisterapp.User;
import com.ema.loginandregisterapp.UserAdapter;
import com.ema.loginandregisterapp.UserBroadcastReceiver;
import com.ema.loginandregisterapp.UserDataStoreImpl;
import com.ema.loginandregisterapp.UserIntentService;
import com.ema.loginandregisterapp.UserUtil;

import java.util.List;

public class WelcomeFragment extends BaseFragment implements UserBroadcastReceiver.UpdateCallback {
    SharedPreferences sharedPreferences;
    UserDataStoreImpl userDataStoreImpl;
    BroadcastReceiver broadcastReceiver;
    TextView tvName;

    UserAdapter adapter;
    RecyclerView rv;

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
        String username = userDataStoreImpl.loadUser();
        textView.setText(getString(R.string.hello) + " " + username);
    }

    //setup Adapter
    private void setupAdapter() {
        List<User> currentlySavedUserList = getAllUsers();

        //setting up the adapter
        adapter = new UserAdapter(currentlySavedUserList, requireContext());
        rv.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rv.setAdapter(adapter);
    }

    public List<User> getAllUsers() {
        return userDataStoreImpl.loadUsers();
    }

    /** intentService implementation**/

    @Override
    public void newUserReceived(User user) {
        userDataStoreImpl.addUser(user);
        adapter.getUpdateUsers(userDataStoreImpl.loadUsers());
    }
    public void startServiceAndRegisterBroadcast() {
        requireActivity().startService(new Intent(requireActivity(), UserIntentService.class));
        broadcastReceiver = new UserBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter(Constants.NEW_USER_SERVICE_ACTION);
        requireContext().registerReceiver(broadcastReceiver,filter);
    }

    private void stopAndUnregisterService(){
        requireActivity().stopService(new Intent(requireActivity(),UserIntentService.class));

        if(broadcastReceiver != null){
            requireContext().unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startServiceAndRegisterBroadcast();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopAndUnregisterService();
    }



}