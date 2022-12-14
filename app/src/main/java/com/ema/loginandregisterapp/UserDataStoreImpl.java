package com.ema.loginandregisterapp;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class UserDataStoreImpl implements UserDataStore {
    SharedPreferences sharedPreferences;

    public UserDataStoreImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public void saveUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_SHARED_PREFERENCES_EDIT_LOAD, username);
        editor.commit();
    }

    @Override
    public void addUser(User user) {
        List<User> userList = (ArrayList<User>) loadUsers();
        userList.add(user);
        sharedPreferences.edit().putString(Constants.KEY_SHARED_PREFERENCES_LIST, UserUtil.userListToString(userList)).apply();
    }


    @Override
    public String loadUser() {
        return sharedPreferences.getString(Constants.KEY_SHARED_PREFERENCES_EDIT_LOAD, "");
    }

    @Override
    public void addUsers(String userListToString) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Constants.KEY_SHARED_PREFERENCES_LIST, userListToString);
        editor.commit();
    }


    @Override
    public List<User> loadUsers() {
      String users = sharedPreferences.getString(Constants.KEY_SHARED_PREFERENCES_LIST, "");
        return UserUtil.userListFromString(users);
    }

    @Override
    public void logoutUser() {

    }
}
