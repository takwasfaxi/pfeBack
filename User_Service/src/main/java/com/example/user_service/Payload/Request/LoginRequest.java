package com.example.user_service.Payload.Request;

public class LoginRequest {

    private String username;
    private String password;

    //Getter&Setter

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
