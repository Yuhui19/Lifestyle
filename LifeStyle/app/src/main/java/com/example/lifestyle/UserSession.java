package com.example.lifestyle;

public class UserSession {
    String id = "13";

    private UserSession() { }

    private static final UserSession userSessionInstance = new UserSession();

    public static UserSession getInstance() {
        return userSessionInstance;
    }

    public String getSessionId() {
        return id;
    }

    public void setSessionId(String id) {
        this.id = id;
    }
}




