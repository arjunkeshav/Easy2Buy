package com.example.arjun.easy2buy.update.notify;

import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("title")
    public String title;

    @SerializedName("body")
    public String body;

    @SerializedName("click_action")
    public String click_action;

}
