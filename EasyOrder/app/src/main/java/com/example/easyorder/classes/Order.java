package com.example.easyorder.classes;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("id_order")
    private int id_order;
    @SerializedName("id_res_food")
    private int id_res_food;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("id_res_table")
    private int id_res_table;

    public int getId_ordar() {
        return id_order;
    }

    public void setId_ordar(int id_order) {
        this.id_order = id_order;
    }

    public int getId_res_food() {
        return id_res_food;
    }

    public void setId_res_food(int id_res_food) {
        this.id_res_food = id_res_food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId_res_table() {
        return id_res_table;
    }

    public void setId_res_table(int id_res_table) {
        this.id_res_table = id_res_table;
    }
}
