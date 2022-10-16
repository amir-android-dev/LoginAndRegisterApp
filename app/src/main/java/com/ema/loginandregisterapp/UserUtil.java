package com.ema.loginandregisterapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class UserUtil {
    public static String userToString(User user) {
        return new Gson().toJson(user);
    }

    public static User userFromString(String user) {
        return new Gson().fromJson(user, User.class);
    }

    public static String userListToString(List<User> userList) {
        return new Gson().toJson(userList);
    }

    public static ArrayList<User> userListFromString(String userList) {
        ArrayList<User> list = new Gson().fromJson(userList, new TypeToken<ArrayList<User>>() {
        }.getType());
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

}

