package model;

/**
 * Created by wolfsoft3 on 17/7/18.
 */

public class UsersearchModel {

    private int nearbyimg2;
    private String  nearbyimg1,nearbytext1,nearbytext2,nearbytext3,nearbytext4;


    public UsersearchModel(String nearbyimg1, int nearbyimg2, String nearbytext1, String nearbytext2, String nearbytext3, String nearbytext4) {
        this.nearbyimg1 = nearbyimg1;
        this.nearbyimg2 = nearbyimg2;
        this.nearbytext1 = nearbytext1;
        this.nearbytext2 = nearbytext2;
        this.nearbytext3 = nearbytext3;
        this.nearbytext4 = nearbytext4;
    }

    public String getNearbytext4() {
        return nearbytext4;
    }

    public void setNearbytext4(String nearbytext4) {
        this.nearbytext4 = nearbytext4;
    }

    public String getNearbyimg1() {
        return nearbyimg1;
    }

    public void setNearbyimg1(String nearbyimg1) {
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
