package com.example.lifestyle;

import java.util.HashMap;
import java.util.Map;

public class UserCredential {
    Map<String, String> map = new HashMap<>();

    private UserCredential(){ }

    private static final UserCredential userCredential = new UserCredential();

    public static UserCredential getInstance() {
        return userCredential;
    }

    public boolean hasUser(String username) {
        return map.containsKey(username);
    }

    public void addUser(String username, String password) {
        map.put(username, password);
    }

    public String getUserPassword(String username) {
        return map.get(username);
    }

}


