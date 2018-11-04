
package com.example.arjun.easy2buy.update.notify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("data")
    @Expose
    private Data data;

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    @SerializedName("notification")
    public Notification notification;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
