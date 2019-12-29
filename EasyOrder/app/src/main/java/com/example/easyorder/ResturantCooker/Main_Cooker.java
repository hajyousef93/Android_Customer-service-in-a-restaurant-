package com.example.easyorder.ResturantCooker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.easyorder.ResturantCooker.Adapter.CookerRecyclerViewAdapter;
import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.R;
import com.example.easyorder.connector.ResturantApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_Cooker extends AppCompatActivity {
private int id_cooker;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CookerRecyclerViewAdapter cookerRecyclerViewAdapter;
private ResturantApiInterface resturantApiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__cooker);
        recyclerView = findViewById(R.id.Cooker_rc);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        id_cooker=Integer.parseInt(getIntent().getStringExtra("id_cooker"));
        resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
        Call<List<OrderDetails>> call = resturantApiInterface.getOrdersForCooker(id_cooker,21);
        call.enqueue(new Callback<List<OrderDetails>>() {
            @Override
            public void onResponse(Call<List<OrderDetails>> call, Response<List<OrderDetails>> response) {
                List<OrderDetails>orderDetails=response.body();
                cookerRecyclerViewAdapter =new CookerRecyclerViewAdapter(getApplicationContext(),orderDetails);
                recyclerView.setAdapter(cookerRecyclerViewAdapter);                            }



            @Override
            public void onFailure(Call<List<OrderDetails>> call, Throwable t) {

            }
        });
    }
}
