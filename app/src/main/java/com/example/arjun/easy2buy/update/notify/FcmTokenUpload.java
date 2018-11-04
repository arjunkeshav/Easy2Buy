package com.example.arjun.easy2buy.update.notify;

public class FcmTokenUpload {
    private  String id;
    private String token;

    public FcmTokenUpload() {
    }

    public FcmTokenUpload(String id, String token, String name) {
        this.id = id;
        this.token = token;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
