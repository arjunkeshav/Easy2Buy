package com.example.arjun.easy2buy.newadmin;

public class VenderReview {

private  String reviewDesc,vendorId;

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    private  String reviewId;

    public VenderReview() {
    }


    public String getReviewDesc() {
        return reviewDesc;
    }

    public void setReviewDesc(String reviewDesc) {
        this.reviewDesc = reviewDesc;
    }

    public VenderReview(String reviewDesc, String vendorId, String reviewId) {
        this.reviewDesc = reviewDesc;
        this.vendorId = vendorId;
        this.reviewId = reviewId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }
}
