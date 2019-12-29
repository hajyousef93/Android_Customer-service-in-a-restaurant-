package com.example.easyorder.User.Fregments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easyorder.R;
import com.example.easyorder.classes.Restaurant_food;
import com.example.easyorder.User.Adapter.TopMealsAdapter;
import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.connector.ResturantApiInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Top_meals_fragment extends Fragment {
    private View view;
    private TopMealsAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private  List<Restaurant_food> restaurant_foods;
    private ResturantApiInterface resturantApiInterface;

    public Top_meals_fragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        recyclerView = view.findViewById(R.id.top_meals);
        recyclerView = view.findViewById(R.id.Recycler_View);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerAdapter = new TopMealsAdapter(new ArrayList<Restaurant_food>(), getActivity());
        recyclerView.setAdapter(recyclerAdapter);
        fetchInformation();
        return view;
    }
    private void fetchInformation() {
        resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
        Call<List<Restaurant_food>> call = resturantApiInterface.getTopMeals(13);
        call.enqueue(new Callback<List<Restaurant_food>>() {
            @Override
            public void onResponse(Call<List<Restaurant_food>> call, Response<List<Restaurant_food>> response) {
                if(response.code()==200){
                    restaurant_foods=response.body();
                    recyclerAdapter= new TopMealsAdapter(restaurant_foods,getContext());
                    recyclerView.setAdapter(recyclerAdapter);

                }
            }

            @Override
            public void onFailure(Call<List<Restaurant_food>> call, Throwable t) {
                if (t instanceof IOException) {
                    Toast.makeText(getActivity(), "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    // logging probably not necessary
                }
                else {
                    Toast.makeText(getActivity(), "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                }
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

    }


}