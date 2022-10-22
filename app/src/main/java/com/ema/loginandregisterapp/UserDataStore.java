package com.ema.loginandregisterapp;

import android.content.SharedPreferences;

import java.util.List;

public interface UserDataStore {

    public void addUser(String username);

    public String loadUser();

    public void addUsers(String userListToString);
    public List<User> loadUsers();

    public void logoutUser();
}
