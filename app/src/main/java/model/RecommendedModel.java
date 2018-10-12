package model;

/**
 * Created by wolfsoft3 on 17/7/18.
 */

public class RecommendedModel {

    private int nearbyimg1,nearbyimg2;
    private String nearbytext1,nearbytext2,nearbytext3;


    public RecommendedModel(int nearbyimg1, int nearbyimg2, String nearbytext1, String nearbytext2, String nearbytext3) {
        this.nearbyimg1 = nearbyimg1;
        this.nearbyimg2 = nearbyimg2;
        this.nearbytext1 = nearbytext1;
        this.nearbytext2 = nearbytext2;
        this.nearbytext3 = nearbytext3;
    }

    public int getNearbyimg1() {
        return nearbyimg1;
    }

    public void setNearbyimg1(int nearbyimg1) {
        this.nearbyimg1 = nearbyimg1;
    }

    public int getNearbyimg2() {
        return nearbyimg2;
    }

    public void setNearbyimg2(int nearbyimg2) {
        this.nearbyimg2 = nearbyimg2;
    }

    public String getNearbytext1() {
        return nearbytext1;
    }

    public void setNearbytext1(String nearbytext1) {
        this.nearbytext1 = nearbytext1;
    }

    public String getNearbytext2() {
        return nearbytext2;
    }

    public void setNearbytext2(String nearbytext2) {
        this.nearbytext2 = nearbytext2;
    }

    public String getNearbytext3() {
        return nearbytext3;
    }

    public void setNearbytext3(String nearbytext3) {
        this.nearbytext3 = nearbytext3;
    }




}
