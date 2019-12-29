package com.example.easyorder.classes;

import com.google.gson.annotations.SerializedName;

public class MealDetail {
    @SerializedName("name_ingredient")
    String name_ingredient;

    public String getName_ingredient() {
        return name_ingredient;
    }

    public void setName_ingredient(String name_ingredient) {
        this.name_ingredient = name_ingredient;
    }
}
