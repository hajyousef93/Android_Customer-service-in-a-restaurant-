package com.example.easyorder.User.Activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import com.example.easyorder.DataBase.CartDataBase;
import com.example.easyorder.classes.MealDetail;
import com.example.easyorder.classes.Menu;
import com.example.easyorder.classes.Order;
import com.example.easyorder.R;
import com.example.easyorder.classes.Resturant;
import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.connector.ResturantApiInterface;
import com.squareup.picasso.Picasso;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealDetailActivity extends AppCompatActivity {
    private int id_food,quantity,id_reservation,reservation_type;
    private LinearLayout price_offer_layout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private TextView meal_name,meal,meal_ingredent,meal_price,meal_offer_price;
    private Toolbar toolbar;
    private FloatingActionButton cart;
    private ElegantNumberButton amount;
    private Resturant resturant;
    private ImageView resturantImageView;
    private List<MealDetail> Ingredient;
    private String ing="";
    private Menu mealDetail;
    int id_res_table;
    Order order;
    private String imageBaseUrl="http://192.168.43.73:8080/easy%20order/images/";
    ResturantApiInterface resturantApiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_detail);
        mealDetail= (Menu) getIntent().getSerializableExtra("Menu_Object");
        id_reservation=Integer.parseInt(getIntent().getStringExtra("id_reservation"));
        reservation_type=Integer.parseInt(getIntent().getStringExtra("reservation_type"));
        resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
        id_food=mealDetail.getId_food();
        meal_ingredent=findViewById(R.id.food_description);
        final CartDataBase orderDataBase=new CartDataBase(getApplicationContext());
        final Call<List<MealDetail>> call = resturantApiInterface.getIngredient(id_food,16);
        call.enqueue(new Callback<List<MealDetail>>() {
            @Override
            public void onResponse(Call<List<MealDetail>> call, Response<List<MealDetail>> response) {
                Ingredient = response.body();
                for(int i=0;i<Ingredient.size();i++){
                    ing+=Ingredient.get(i).getName_ingredient()+" ";
                    meal_ingredent.setText(ing);
                }
            }

            @Override
            public void onFailure(Call<List<MealDetail>> call, Throwable t) {

            }
        } );

        bindViews();
        setUpTheToolbar();
        getAndSetUis();
        Picasso.with(this).load(imageBaseUrl+mealDetail.getImage())
                .into(resturantImageView);
        meal.setText(mealDetail.getMealName());
        quantity=Integer.parseInt(amount.getNumber());
        if(reservation_type==1){
            cart.hide();
        }
        else {
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity=Integer.parseInt(amount.getNumber());
                if (mealDetail.getOffer() == 1) {
                 orderDataBase.addToCart(mealDetail.getId_food(),mealDetail.getId_res_food(),mealDetail.getMealName(),mealDetail.getPrice_offer(),quantity);
                    Toast.makeText(MealDetailActivity.this, "تمت إضافة الوجبة إلى عربة التسوق", Toast.LENGTH_SHORT).show();
                }
                else if(mealDetail.getOffer()==0){
                    orderDataBase.addToCart(mealDetail.getId_food(),mealDetail.getId_res_food(),mealDetail.getMealName(),mealDetail.getPrice(),quantity);

                    Toast.makeText(MealDetailActivity.this, "تمت إضافة الوجبة إلى عربة التسوق", Toast.LENGTH_SHORT).show();

                }


            }}); } }

private void bindViews() {
        collapsingToolbarLayout = findViewById(R.id.meal_details_collapsing_tool_bar);
        resturantImageView = findViewById(R.id.img_food);
       meal_name=findViewById(R.id.food_name);
        meal=findViewById(R.id.tool_name_tv);
        meal_price=findViewById(R.id.food_price);
        meal_offer_price=findViewById(R.id.food_offerprice);
        price_offer_layout=findViewById(R.id.layout_Offerprice);
        cart=findViewById(R.id.btnCart);
        amount=findViewById(R.id.number_button);
        toolbar = findViewById(R.id.meal_details_toolbar);

    }
    private void setUpTheToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mealDetail.getMealName());
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setTitle(" ");
    }

    private void getAndSetUis() {
        if(mealDetail.getOffer()==1){
    price_offer_layout.setVisibility(View.INVISIBLE);
    meal_name.setText(mealDetail.getMealName());
    meal_price.setText(Integer.toString(mealDetail.getPrice()));
}
else {
     meal_name.setText(mealDetail.getMealName());
        meal_price.setText(Integer.toString(mealDetail.getPrice()));
        meal_offer_price.setText(Integer.toString(mealDetail.getPrice_offer()));

    } }


}
