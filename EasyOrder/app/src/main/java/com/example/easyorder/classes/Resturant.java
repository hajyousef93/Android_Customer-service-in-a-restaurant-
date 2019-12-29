package com.example.easyorder.classes;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Resturant implements Serializable {
    @SerializedName("id_resturant")
    int id_resturant;
    @SerializedName("resturant_name")
    String  resturant_name;
    @SerializedName("resturant_address")
    String resturant_address;
    @SerializedName("resturant_image")
    String resturant_image;
    @SerializedName("manger_name")
    String manager_name;
    @SerializedName("cooker_name")
    String cooker_name;
    @SerializedName("id_cooker")
    int id_cooker;

    public int getId_cooker() {
        return id_cooker;
    }

    public void setId_cooker(int id_cooker) {
        this.id_cooker = id_cooker;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public String getCooker_name() {
        return cooker_name;
    }

    public void setCooker_name(String cooker_name) {
        this.cooker_name = cooker_name;
    }

    public String getResturant_image() {
        return resturant_image;
    }

    public void setResturant_image(String resturant_image) {
        this.resturant_image = resturant_image;
    }


    public int getId_resturant() {
        return id_resturant;
    }

    public void setId_resturant(int id_resturant) {
        this.id_resturant = id_resturant;
    }

    public String getResturant_name() {
        return resturant_name;
    }

    public void setResturant_name(String resturant_name) {
        this.resturant_name = resturant_name;
    }

    public String getResturant_address() {
        return resturant_address;
    }

    public void setResturant_address(String resturant_address) {
        this.resturant_address = resturant_address;
    }
}
