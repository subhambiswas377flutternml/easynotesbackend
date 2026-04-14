package com.app.easy.notes.DTO.response;

public class AuthResponse {
    private String name;
    private String username;
    private String accessToken;

    public AuthResponse(){}
    public AuthResponse(String name, String username, String accessToken){
        this.accessToken=accessToken;
        this.name = name;
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
