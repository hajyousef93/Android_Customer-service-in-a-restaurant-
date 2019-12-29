package com.example.easyorder.connector;

import com.example.easyorder.classes.MealDetail;
import com.example.easyorder.classes.Menu;
import com.example.easyorder.classes.Order;
import com.example.easyorder.classes.Reservation;
import com.example.easyorder.classes.Restaurant_food;
import com.example.easyorder.classes.Resturant;
import com.example.easyorder.ResturantCooker.OrderDetails;
import com.example.easyorder.classes.account;
import com.example.easyorder.classes.meal_type;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ResturantApiInterface {


    @GET("get.php")
    Call<List<Resturant>>getResturant(@Query("re") int re);
    @GET("get.php")
    Call <List<account>> getAccount(@Query("user_name") String user_name, @Query("password") String password, @Query("re") int re);
    @GET("get.php")
    Call<List<Menu>>getMenu(@Query("id_resturant")int id_resturant, @Query("id_meal")int id_meal, @Query("re") int re);
    @GET("get.php")
    Call<List<meal_type>>getMealType(@Query("re") int re);
    @GET("get.php")
    Call<List<Reservation>>getTablesType(@Query("id_resturant")int id_resturant, @Query("re") int re);
    @GET("get.php")
    Call<List<Reservation>>getTable(@Query("id_resturant")int id_resturant,@Query("id_table")int id_table,@Query("re") int re);
    @GET("get.php")
    Call<List<Reservation>>getReservesdTables(@Query("id_resturant_table")int id_resturant_table,@Query("re") int re);
    @GET("get.php")
    Call<List<Reservation>> getIdReservation(@Query("id_customer")int id_customer,@Query("re") int re);
    @GET("get.php")
    Call<List<Reservation>> getNumberOfVisites(@Query("id_customer")int id_customer,@Query("re") int re);
    @GET("get.php")
    Call<List<Restaurant_food>>getTopMeals(@Query("re") int re);
    @GET("get.php")
    Call<List<Resturant>>getManagerResturant(@Query("id_manager") int id_manager,@Query("re") int re);
    @GET("get.php")
    Call<List<MealDetail>>getIngredient(@Query("id_food") int id_food, @Query("re") int re);
    @GET("get.php")
    Call<List<Order>>getOrderId(@Query("id_res_table") int id_res_table, @Query("re") int re);
    @GET("get.php")
    Call<List<Order>>getResturantReservationTable(@Query("id_reservation") int id_reservation,@Query("re") int re);
    @GET("get.php")
    Call<List<OrderDetails>>getOrdersForCooker(@Query("id_cooker") int id_cooker, @Query("re") int re);
    @GET("get.php")
    Call<List<Reservation>>getReservationTimeAndDate(@Query("id_customer") int id_customer, @Query("re") int re);

    @FormUrlEncoded
    @POST("set.php")
    Call<account> postAccount(@Field("id_acc_type") int id_acc_type,
                                   @Field("Full_name") String Full_name,
                                   @Field("user_name") String  user_name,
                                    @Field("password") String password,
                                    @Field("phonenumber") String phonenumber,
                                    @Field("re") int re);
    @FormUrlEncoded
    @POST("set.php")
    Call<Reservation> postReservation(@Field("id_customer")int id_customer,
                              @Field("id_reservation_type")int id_reservation_type,
                              @Field("id_loyalty")int id_loyalty,
                              @Field("re") int re);
    @FormUrlEncoded
    @POST("set.php")
    Call<Reservation> postReservation_table(@Field("id_reservation")int id_reservation,
                                      @Field("id_resturant_table")int id_resturant_table,
                                      @Field("timeOfReservation")String timeOfReservation,
                                      @Field("isBusy")int isBusy,
                                      @Field("reservation_date")String reservation_date,
                                      @Field("numOfVisits")int numOfVisits,
                                      @Field("hours")String currentHour,
                                      @Field("min")String CurrentMinuts,
                                      @Field("re") int re);

    @FormUrlEncoded
    @POST("set.php")
    Call<Order> postOrder(@Field("id_res_table")int id_res_table,
                                      @Field("re") int re);
    @FormUrlEncoded
    @POST("set.php")
    Call<Order> postFoodOrder(@Field("id_order")int id_order,
                                      @Field("id_resturant_food")int id_resturant_food,
                                      @Field("quantity")int quantity,
                                      @Field("re") int re);

    @FormUrlEncoded
    @POST("set.php")
    Call<OrderDetails> RemoveOrder(@Field("id_order_resturant_food")int id_order_resturant_food,
                              @Field("re") int re);
    @FormUrlEncoded
    @POST("set.php")
    Call<Resturant> postNewRestaurant(@Field("resturant_name")String resturant_name,
                              @Field("resturant_address")String resturant_address,
                              @Field("id_manger")int id_manger,
                              @Field("re") int re);
    @FormUrlEncoded
    @POST("setManager.php")
    Call<account> RenameManagerCooker (@Field("id_account") int id_account,
                                       @Field("Full_name") String Full_name,
                                       @Field("id_query") int id_query);
    @FormUrlEncoded
    @POST("setManager.php")
    Call<Resturant> updateResturantInfo (@Field("id_resturant") int id_resturant,
                                         @Field("resturant_name") String resturant_name,
                                         @Field("resturant_address") String resturant_address,
                                         @Field("id_query") int id_query);
    @FormUrlEncoded
    @POST("setManager.php")
    Call<meal_type> addMealType (@Field("meal_type") String meal_type,
                                 @Field("id_query") int id_query);

}
