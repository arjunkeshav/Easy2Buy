package model;

/**
 * Created by wolfsoft3 on 18/7/18.
 */

public class ReviewsModel {
    public ReviewsModel(String foodtext1, String foodtext2, String foodtext3, String foodtext4, Integer circleviewimg1, Integer foodimg1, Integer foodimg2, Integer foodimg3, Integer foodimg4) {
        this.foodtext1 = foodtext1;
        this.foodtext2 = foodtext2;
        this.foodtext3 = foodtext3;
        this.foodtext4 = foodtext4;
        this.circleviewimg1 = circleviewimg1;
        this.foodimg1 = foodimg1;
        this.foodimg2 = foodimg2;
        this.foodimg3 = foodimg3;
        this.foodimg4 = foodimg4;
    }



    public String getFoodtext1() {
        return foodtext1;
    }

    public void setFoodtext1(String foodtext1) {
        this.foodtext1 = foodtext1;
    }

    public String getFoodtext2() {
        return foodtext2;
    }

    public void setFoodtext2(String foodtext2) {
        this.foodtext2 = foodtext2;
    }

    public String getFoodtext3() {
        return foodtext3;
    }

    public void setFoodtext3(String foodtext3) {
        this.foodtext3 = foodtext3;
    }

    public String getFoodtext4() {
        return foodtext4;
    }

    public void setFoodtext4(String foodtext4) {
        this.foodtext4 = foodtext4;
    }

    public Integer getCircleviewimg1() {
        return circleviewimg1;
    }

    public void setCircleviewimg1(Integer circleviewimg1) {
        this.circleviewimg1 = circleviewimg1;
    }

    public Integer getFoodimg1() {
        return foodimg1;
    }

    public void setFoodimg1(Integer foodimg1) {
        this.foodimg1 = foodimg1;
    }

    public Integer getFoodimg2() {
        return foodimg2;
    }

    public void setFoodimg2(Integer foodimg2) {
        this.foodimg2 = foodimg2;
    }

    public Integer getFoodimg3() {
        return foodimg3;
    }

    public void setFoodimg3(Integer foodimg3) {
        this.foodimg3 = foodimg3;
    }

    public Integer getFoodimg4() {
        return foodimg4;
    }

    public void setFoodimg4(Integer foodimg4) {
        this.foodimg4 = foodimg4;
    }

    String foodtext1,foodtext2,foodtext3,foodtext4;

   Integer circleviewimg1,foodimg1,foodimg2,foodimg3,foodimg4;
}
