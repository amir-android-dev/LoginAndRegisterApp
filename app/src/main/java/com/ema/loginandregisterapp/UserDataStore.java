package com.ema.loginandregisterapp;

import java.util.List;

public interface UserDataStore {

    public void saveUsername(String username);

    public String loadUser();

    public void addUsers(String userListToString);
    public List<User> loadUsers();

    public void logoutUser();
    public void addUser(User user);
}
