package com.example.easyorder.classes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Menu implements Serializable {
    @SerializedName("id_res_food")
    int id_res_food;
    @SerializedName("id_food")
    int id_food;
    @SerializedName("id_resturant")
    int id_resturant;
    @SerializedName("price")
    int price;
    @SerializedName("price_offer")
    int price_offer;
    @SerializedName("Image")
    String Image;
    @SerializedName("offer")
    int offer;
    @SerializedName("name_food")
    private String mealName;
    @SerializedName("id_res_table")
    private int id_res_table;

    public int getId_res_table() {
        return id_res_table;
    }

    public void setId_res_table(int id_res_table) {
        this.id_res_table = id_res_table;
    }

    public int getQuan() {
        return quan;
    }

    public void setQuan(int quan) {
        this.quan = quan;
    }

    int quan;
    public int getId_res_food() {
        return id_res_food;
    }

    public void setId_res_food(int id_res_food) {
        this.id_res_food = id_res_food;
    }

    public int getId_food() {
        return id_food;
    }

    public void setId_food(int id_food) {
        this.id_food = id_food;
    }

    public int getId_resturant() {
        return id_resturant;
    }

    public void setId_resturant(int id_resturant) {
        this.id_resturant = id_resturant;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice_offer() {
        return price_offer;
    }

    public void setPrice_offer(int price_offer) {
        this.price_offer = price_offer;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getOffer() {
        return offer;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }
}
