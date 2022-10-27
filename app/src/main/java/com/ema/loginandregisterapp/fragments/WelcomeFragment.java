package com.ema.loginandregisterapp.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import java.util.List;

public class WelcomeFragment extends BaseFragment implements UserBroadcastReceiver.UpdateCallback {
    SharedPreferences sharedPreferences;
    private UserDataStoreImpl userDataStoreImpl;
    private BroadcastReceiver broadcastReceiver;
    private TextView tvName;

    private UserAdapter adapter;
    private RecyclerView rv;
    User clickedUsername;

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
        setupAdapter(view);

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
    private void setupAdapter(View view) {
        List<User> currentlySavedUserList = getAllUsers();

        //setting up the adapter
        adapter = new UserAdapter(currentlySavedUserList, requireContext(), new UserAdapter.Callback() {
            @Override
            public void userClicked(User user) {
                clickedUsername = user;
                checkLocationPermissionAndOpenUserProfile( clickedUsername);
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        rv.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        rv.setAdapter(adapter);
    }

    public List<User> getAllUsers() {
        return userDataStoreImpl.loadUsers();
    }


    //  it handles if the permission is granted or denyed what to do
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //check the send request code
        if (requestCode == Constants.LOCATION_PERMISSION_REQUEST_CODE) {
            //here we check the permission that are returned  ACCESS_COARSE_LOCATION
            //check if the permission is granted
            if (permissions[0].equals(Manifest.permission.ACCESS_COARSE_LOCATION) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
               // openAndSendDataToUserProfile(this,clickedUsername);
                openAndSendDataToUserProfile(requireView(),clickedUsername);

            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    displayToast(requireContext(), "You need this permission to open the user profile ");

                } else {
                    displayToast(requireContext(), "To open the profile give the permission inside of Setting -> permission");
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    /**
     * intentService implementation
     **/

    @Override
    public void newUserReceived(User user) {
        userDataStoreImpl.addUser(user);
        adapter.getUpdateUsers(userDataStoreImpl.loadUsers());
    }

    public void startServiceAndRegisterBroadcast() {
        requireActivity().startService(new Intent(requireActivity(), UserIntentService.class));
        broadcastReceiver = new UserBroadcastReceiver(this);
        IntentFilter filter = new IntentFilter(Constants.NEW_USER_SERVICE_ACTION);
        requireContext().registerReceiver(broadcastReceiver, filter);
    }

    private void stopAndUnregisterService() {
        requireActivity().stopService(new Intent(requireActivity(), UserIntentService.class));

        if (broadcastReceiver != null) {
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