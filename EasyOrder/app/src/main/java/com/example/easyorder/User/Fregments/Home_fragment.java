package com.example.easyorder.User.Fregments;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.easyorder.R;
import com.example.easyorder.User.Adapter.RestaurantRecyclerAdapter;
import com.example.easyorder.classes.Resturant;
import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.connector.ResturantApiInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_fragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private
    RestaurantRecyclerAdapter rcAdapter;
    private ResturantApiInterface resturantApiInterface;
    private int id_account;

    public Home_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        id_account=getArguments().getInt("id_account");
        getAndSetUi();
        return rootView;
    }

    private void getAndSetUi() {
        recyclerView = rootView.findViewById(R.id.Recycler_View);
        setUpTheRecyclerAdapter();
        fetchInformation();
    }

    private void fetchInformation() {
        resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
        Call<List<Resturant>> call = resturantApiInterface.getResturant(0);
        call.enqueue(new Callback<List<Resturant>>() {
            @Override
            public void onResponse(Call<List<Resturant>> call, Response<List<Resturant>> response) {
                if(response.code()==200){
                    rcAdapter.addAll(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Resturant>> call, Throwable t) {
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

    private void setUpTheRecyclerAdapter() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                super.onDraw(c, parent, state);
            }
        });
        rcAdapter = new RestaurantRecyclerAdapter(new ArrayList<Resturant>(),getActivity(),id_account);
        recyclerView.setAdapter(rcAdapter);
    }

}