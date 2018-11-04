package model;

import com.example.arjun.easy2buy.update.notify.FirebaseMessage;
import com.google.firebase.iid.FirebaseInstanceId;

public class Followers {
    String userId,vendorId,followId,token;

    public Followers(String userId, String vendorId, String followId) {
        this.userId = userId;
        this.vendorId = vendorId;
        this.followId = followId;
        this.token= FirebaseInstanceId.getInstance().getToken();
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }
}
