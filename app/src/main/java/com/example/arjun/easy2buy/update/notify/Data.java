
package com.example.arjun.easy2buy.update.notify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;

    public Data(String title, String message, String imageUrl, String uid) {
        this.title = title;
        this.message = message;
        this.imageUrl = imageUrl;
        this.click_action=".update.Notify";
        this.uid=uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getClick_action() {
        return click_action;
    }

    public void setClick_action(String click_action) {
        this.click_action = click_action;
    }

    private String uid;
    @SerializedName("image-url")
    @Expose
    private String imageUrl;
    private String click_action;
    public Data(String title, String message, String imageUrl) {
        this.title = title;
        this.message = message;
        this.imageUrl = imageUrl;
        this.click_action=".update.Notify";
       // "click_action" : ".MainActivity"
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
