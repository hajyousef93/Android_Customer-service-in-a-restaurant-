package com.example.easyorder.classes;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class account implements Serializable {
    @SerializedName("id_account")
    public int id_account;
    @SerializedName("id_acc_type")
    int id_acc_type;
    @SerializedName("Full_name")
    String Full_name;
    @SerializedName("user_name")
    String user_name;
    @SerializedName("password")
    String password;
    @SerializedName("phonenumber")
    String phonenumber;


    public int getId_account() {
        return id_account;
    }

    public int getId_acc_type() {
        return id_acc_type;
    }

    public void setId_acc_type(int id_acc_type) {
        this.id_acc_type = id_acc_type;
    }

    public String getFull_name() {
        return Full_name;
    }

    public void setFull_name(String full_name) {
        Full_name = full_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
