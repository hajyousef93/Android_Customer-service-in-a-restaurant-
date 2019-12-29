package com.example.easyorder.User.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.easyorder.User.Adapter.MealTypeAdapter;
import com.example.easyorder.R;
import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.connector.ResturantApiInterface;
import com.example.easyorder.classes.meal_type;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class meal_typeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MealTypeAdapter mealTypeAdapter;
    private List<meal_type> meal_types;
    private ResturantApiInterface resturantApiInterface;
    private int id_resturant,id_reservation,reservation_type,id_account;
    private String user_name;
    private FloatingActionButton order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_type);
        recyclerView = findViewById(R.id.meal_type_rc);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        order=findViewById(R.id.Order_floating_action_button);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id_account=Integer.parseInt(getIntent().getStringExtra("id_account"));
                user_name=getIntent().getStringExtra("user_name");
                reservation_type=Integer.parseInt(getIntent().getStringExtra("reservation_type"));
                Intent cartIntent = new Intent(meal_typeActivity.this, CartActivity.class);
                cartIntent.putExtra("id_reservation",Integer.toString(id_reservation));
                cartIntent.putExtra("reservation_type",Integer.toString(reservation_type));
                cartIntent.putExtra("id_account",Integer.toString(id_account));
                cartIntent.putExtra("user_name",user_name);
                startActivity(cartIntent);
            }
        });
        getIncommingData();
    }
    private void getIncommingData(){
        id_resturant=Integer.parseInt(getIntent().getStringExtra("id_resturant"));
        id_reservation=Integer.parseInt(getIntent().getStringExtra("id_reservation"));
        reservation_type=Integer.parseInt(getIntent().getStringExtra("reservation_type"));

        fetchInformation();}

            public void fetchInformation()
            { if (reservation_type==1){
                order.hide();
            }
                resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
                Call<List<meal_type>> call = resturantApiInterface.getMealType(3);
                call.enqueue(new Callback<List<meal_type>>() {
                    @Override
                    public void onResponse(Call<List<meal_type>> call, Response<List<meal_type>> response) {
                        meal_types = response.body();
                        mealTypeAdapter = new MealTypeAdapter(meal_types, meal_typeActivity.this,id_resturant,id_reservation,reservation_type);
                        recyclerView.setAdapter(mealTypeAdapter);
                    }
                    @Override
                    public void onFailure(Call<List<meal_type>> call, Throwable t) {
                        Toast.makeText(meal_typeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }



