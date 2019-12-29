package com.example.easyorder.User.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.easyorder.classes.Menu;
import com.example.easyorder.User.Adapter.MenuRecyclerAdapter;
import com.example.easyorder.R;
import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.connector.ResturantApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private  int id_resturant,id_meal,id_reservation,reservation_type;
    private ResturantApiInterface resturantApiInterface;
    private List<Menu> menus;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MenuRecyclerAdapter menuRecyclerAdapter;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        id_resturant=Integer.parseInt(getIntent().getStringExtra("id_resturant"));
        id_meal=Integer.parseInt(getIntent().getStringExtra("id_meal"));
        id_reservation=Integer.parseInt(getIntent().getStringExtra("id_reservation"));
        reservation_type=Integer.parseInt(getIntent().getStringExtra("reservation_type"));
        recyclerView = findViewById(R.id.Recycler_View_menu);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetchinformation();
    }

    private void fetchinformation(){
        resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
        Call<List<Menu>> call = resturantApiInterface.getMenu(id_resturant,id_meal,12);
        call.enqueue(new Callback<List<Menu>>() {
            @Override
            public void onResponse(Call<List<Menu>> call, Response<List<Menu>> response) {
                menus=response.body();
                menuRecyclerAdapter= new MenuRecyclerAdapter(menus,getApplicationContext(),id_reservation,reservation_type);
                recyclerView.setAdapter(menuRecyclerAdapter);                            }
            @Override
            public void onFailure(Call<List<Menu>> call, Throwable t) {
                //  Toast.makeText(BrowsforResturant.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }
    }

