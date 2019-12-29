package com.example.easyorder.ResturantManager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.easyorder.R;
import com.example.easyorder.classes.meal_type;
import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.connector.ResturantApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuFragment extends Fragment {


    private View rootView;
    private EditText foodNameet, foodPriceet, foodPriceAfterOffer;
    private Spinner mealTypeSpinner;
    private CheckBox foodOffer;

    ResturantApiInterface resturantApiInterface;
    private FloatingActionButton floatingActionButton;
    private ArrayList<meal_type> meal_types1;


    public MenuFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_menu_, container, false);
        resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
        getAndSetUis();
        populateSpinner();
        foodPriceAfterOffer.setVisibility(View.INVISIBLE);

        foodOffer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {

                    foodPriceAfterOffer.setVisibility(View.VISIBLE);
                } else {

                    foodPriceAfterOffer.setVisibility(View.INVISIBLE);


                }
            }

        });

    //    mealTypeSpinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   Call<resturant_food>call=resturantApiInterface.
            }
        });
        return rootView;
    }

    private void getAndSetUis() {

        foodNameet = rootView.findViewById(R.id.foodnameet);
        foodPriceet = rootView.findViewById(R.id.foodpriceet);
        foodPriceAfterOffer = rootView.findViewById(R.id.foodpriceafterofferet);
        mealTypeSpinner = rootView.findViewById(R.id.mealtypespinner);
        foodOffer = rootView.findViewById(R.id.offercheckbox);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,
                false);
        floatingActionButton = rootView.findViewById(R.id.menu_floating_action_button);



    }



    private void populateSpinner() {
        Call<List<meal_type>>call=resturantApiInterface.getMealType(3);
        call.enqueue(new Callback<List<meal_type>>() {
            @Override
            public void onResponse(Call<List<meal_type>> call, Response<List<meal_type>> response) {
              List<meal_type>  meal_types=response.body();
                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                for(int i=0;i<meal_types.size();i++)
                {aa.add(meal_types.get(i).getMeal_type());}
                mealTypeSpinner.setAdapter(aa);
            }
            @Override
            public void onFailure(Call<List<meal_type>> call, Throwable t) {

            }
        }); }
}