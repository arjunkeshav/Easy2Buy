package com.example.arjun.easy2buy.newadmin;

public class ProductReviewModel{


        private  String reviewDesc, productId,userName,userImage;



        private  String reviewId,userid;


    public ProductReviewModel() {
    }

    public ProductReviewModel(String reviewDesc, String productId, String reviewId, String userid, String userName, String userImage) {
            this.reviewDesc = reviewDesc;
            this.productId = productId;
            this.userName = userName;
            this.userImage = userImage;
            this.reviewId = reviewId;
            this.userid = userid;

        }

    public String getProductId() {
        return productId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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







}
