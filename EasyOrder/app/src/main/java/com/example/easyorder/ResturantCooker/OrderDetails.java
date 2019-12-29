package com.example.easyorder.ResturantCooker;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderDetails implements Serializable {
    @SerializedName("id_order_resturant_food")
    public int id_order_resturant_food;
    @SerializedName("id_order")
    public int id_order;
    @SerializedName("quantity")
    public int quantity;
    @SerializedName("name_food")
    public String name_food;

    public int getId_order_resturant_food() {
        return id_order_resturant_food;
    }

    public void setId_order_resturant_food(int id_order_resturant_food) {
        this.id_order_resturant_food = id_order_resturant_food;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName_food() {
        return name_food;
    }

    public void setName_food(String name_food) {
        this.name_food = name_food;
    }
}
