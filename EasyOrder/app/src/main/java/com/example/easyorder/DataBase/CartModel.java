package com.example.easyorder.DataBase;

public class CartModel {
int ID;
int ID_RES_FOOD;
    int meal_id;
    String meal_name;
    int price;
    int quantity;

    public CartModel(int ID,int ID_RES_FOOD,int id_meal, String meal_name, int price, int quantity) {
        this.ID=ID;
        this.ID_RES_FOOD=ID_RES_FOOD;
       this.meal_id=id_meal;
        this.meal_name=meal_name;
       this.price=price;
        this.quantity=quantity;
    }

    public int getID_RES_FOOD() {
        return ID_RES_FOOD;
    }

    public void setID_RES_FOOD(int ID_RES_FOOD) {
        this.ID_RES_FOOD = ID_RES_FOOD;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getMeal_id() {
        return meal_id;
    }

    public void setMeal_id(int meal_id) {
        this.meal_id = meal_id;
    }

    public String getMeal_name() {
        return meal_name;
    }

    public void setMeal_name(String meal_name) {
        this.meal_name = meal_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
