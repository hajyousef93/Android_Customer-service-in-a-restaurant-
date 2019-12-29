package com.example.easyorder.classes;

import com.google.gson.annotations.SerializedName;

public class meal_type {
    @SerializedName("id_meal")
    private int id_meal;
    @SerializedName("meal_image")
    private String meal_image;
    @SerializedName("meal_type")
    private String meal_type;

    public int getId_meal() {
        return id_meal;
    }

    public void setId_meal(int id_meal) {
        this.id_meal = id_meal;
    }

    public String getMeal_type() {
        return meal_type;
    }

    public void setMeal_type(String meal_type) {
        this.meal_type = meal_type;
    }

    public String getMeal_image() {
        return meal_image;
    }

    public void setMeal_image(String meal_image) {
        this.meal_image = meal_image;
    }
}
