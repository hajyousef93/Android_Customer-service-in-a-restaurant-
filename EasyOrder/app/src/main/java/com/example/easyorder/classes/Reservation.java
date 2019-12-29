package com.example.easyorder.classes;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Reservation {
    @SerializedName("id_table")
    private int id_table;
    @SerializedName("type_table")
    private int type_table;
    @SerializedName("num_table")
    private int num_table;
    @SerializedName("id_resturant_table")
    private int id_resturant_table;
    @SerializedName("reservation_date")
    private String reservation_date;
    @SerializedName("timeOfReservation")
    private String timeOfReservation;
    @SerializedName("count")
    private int count;
    @SerializedName("id_customer")
    private int id_customer;
    @SerializedName("numOfVisites")
    private int numOfVisites;
    @SerializedName("id_reservation_type")
    private int id_reservation_type;
    @SerializedName("id_reservation")
    private int id_reservation;
    @SerializedName("id_resturant")
    private int id_resturant;
    @SerializedName("hours")
    private String hours;
    @SerializedName("min")
    private String min;

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public int getId_resturant() {
        return id_resturant;
    }

    public void setId_resturant(int id_resturant) {
        this.id_resturant = id_resturant;
    }

    public String getTimeOfReservation() {
        return timeOfReservation;
    }

    public void setTimeOfReservation(String timeOfReservation) {
        this.timeOfReservation = timeOfReservation;
    }

    public int getId_reservation() {
        return id_reservation;
    }

    public void setId_reservation(int id_reservation) {
        this.id_reservation = id_reservation;
    }

    public int getId_loyalty() {
        return id_loyalty;
    }

    public void setId_loyalty(int id_loyalty) {
        this.id_loyalty = id_loyalty;
    }

    @SerializedName("id_loyalty")
    private int id_loyalty;

    public int getId_reservation_type() {
        return id_reservation_type;
    }

    public void setId_reservation_type(int id_reservation_type) {
        this.id_reservation_type = id_reservation_type;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getNumOfVisites() {
        return numOfVisites;
    }

    public void setNumOfVisites(int numOfVisites) {
        this.numOfVisites = numOfVisites;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getReservation_date() {
        return reservation_date;
    }

    public void setReservation_date(String reservation_date) {
        this.reservation_date = reservation_date;
    }

    public int getId_resturant_table() {
        return id_resturant_table;
    }

    public void setId_resturant_table(int id_resturant_table) {
        this.id_resturant_table = id_resturant_table;
    }

    public int getId_table() {
        return id_table;
    }

    public void setId_table(int id_table) {
        this.id_table = id_table;
    }

    public int getType_table() {
        return type_table;
    }

    public void setType_table(int type_table) {
        this.type_table = type_table;
    }

    public int getNum_table() {
        return num_table;
    }

    public void setNum_table(int num_table) {
        this.num_table = num_table;
    }
}
