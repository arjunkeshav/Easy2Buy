package com.example.arjun.easy2buy.newadmin;

import java.io.Serializable;

public class ProductReviewModel implements Serializable {
    public ProductReviewModel(String s, String id, String reviewId) {

        this.reviewDesc = s;
        this.productId = id;
        this.reviewId = reviewId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getReviewDesc() {
        return reviewDesc;
    }

    public void setReviewDesc(String reviewDesc) {
        this.reviewDesc = reviewDesc;
    }

    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    private  String productId,reviewDesc,reviewId;



}
