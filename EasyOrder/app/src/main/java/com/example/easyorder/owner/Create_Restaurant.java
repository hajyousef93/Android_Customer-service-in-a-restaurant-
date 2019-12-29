package com.example.easyorder.owner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.R;
import com.example.easyorder.classes.Resturant;
import com.example.easyorder.connector.ResturantApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Create_Restaurant extends AppCompatActivity {
private int id_account;
private String name,address;
private ResturantApiInterface resturantApiInterface;
private EditText resturant_name,Resturant_address;
private Button createResturant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create__restaurant);
        id_account= Integer.parseInt(getIntent().getStringExtra("id_account"));
        resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
       resturant_name=findViewById(R.id.resturant_name1);
        Resturant_address=findViewById(R.id.Resturant_address1);
        createResturant=findViewById(R.id.CreateRestaurant_btn);
        createResturant.setOnClickListener(new View.OnClickListener() {
        @Override
     public void onClick(View v) {
            name=resturant_name.getText().toString();
            address=Resturant_address.getText().toString();
            Log.d("res_name",name);
            Log.d("es_add",address);
            Log.d("id",id_account+"");
            retrofit2.Call <Resturant> call=resturantApiInterface.postNewRestaurant(name,address,id_account,24);
           call.enqueue(new Callback<Resturant>() {
               @Override
               public void onResponse(Call<Resturant> call, Response<Resturant> response) {
                   Resturant res=response.body();
                   Toast.makeText(Create_Restaurant.this, "تم إنشاء المطعم بنجاح", Toast.LENGTH_SHORT).show();

               }

               @Override
               public void onFailure(Call<Resturant> call, Throwable t) {

               }
           });

        }
                                           }
                );
    }
}
