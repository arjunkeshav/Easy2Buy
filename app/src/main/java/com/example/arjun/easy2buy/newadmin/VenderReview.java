package com.example.arjun.easy2buy.newadmin;

public class VenderReview {

private  String reviewDesc,vendorId,userName,userImage;



    private  String reviewId;

    public VenderReview() {
    }

    public VenderReview(String reviewDesc, String vendorId, String userName, String userImage, String reviewId) {
        this.reviewDesc = reviewDesc;
        this.vendorId = vendorId;
        this.userName = userName;
        this.userImage = userImage;
        this.reviewId = reviewId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }


    public String getReviewDesc() {
        return reviewDesc;
    }

    public void setReviewDesc(String reviewDesc) {
        this.reviewDesc = reviewDesc;
    }



    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
}
