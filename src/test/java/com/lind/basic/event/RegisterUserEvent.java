package com.lind.basic.event;

public class RegisterUserEvent {
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public RegisterUserEvent(String username) {
        this.username = username;
    }
}