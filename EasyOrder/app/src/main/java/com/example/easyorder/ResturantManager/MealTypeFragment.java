package com.example.easyorder.ResturantManager;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;

import com.example.easyorder.R;
import com.example.easyorder.classes.meal_type;
import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.connector.ResturantApiInterface;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealTypeFragment extends Fragment {


    List<meal_type> meal_types;
    private View rootView;
    public CheckBox addMealType, deleteMealType;
    public EditText meal_type_edittxt;
    public Spinner mealTypeSpinner;
    private TextView add,delete;
    public FloatingActionButton addFloatingActionButton, deleteFloatingActionButton;
    private String newMealType;
    ResturantApiInterface resturantApiInterface;

    public MealTypeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\

        rootView = inflater.inflate(R.layout.fragment_meal__type_, container, false);
        meal_type_edittxt = rootView.findViewById(R.id.meal_type_edittxt);
        addMealType=rootView.findViewById(R.id.add_checkbox);
        add=rootView.findViewById(R.id.mealtypetv);
        delete=rootView.findViewById(R.id.ii);
        deleteMealType=rootView.findViewById(R.id.delete_checkbox);
        mealTypeSpinner=rootView.findViewById(R.id.edit_meal_type_spinner);
        addFloatingActionButton=rootView.findViewById(R.id.add_meal_type_floating_action_button);
        deleteFloatingActionButton=rootView.findViewById(R.id.delete_meal_type_floating_action_button);
        resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
        fillSpinner();
        addFloatingActionButton.hide();
        deleteFloatingActionButton.hide();
        meal_type_edittxt.setVisibility(View.INVISIBLE);
        mealTypeSpinner.setVisibility(View.INVISIBLE);
        add.setVisibility(View.INVISIBLE);
        delete.setVisibility(View.INVISIBLE);
        addMealType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                meal_type_edittxt.setVisibility(View.VISIBLE);
                addFloatingActionButton.show();
                deleteFloatingActionButton.hide();
                add.setVisibility(View.VISIBLE);
                delete.setVisibility(View.INVISIBLE);
                mealTypeSpinner.setVisibility(View.INVISIBLE);}
                else {
                    meal_type_edittxt.setVisibility(View.INVISIBLE);
                    addFloatingActionButton.hide();
                    deleteFloatingActionButton.hide();
                    add.setVisibility(View.INVISIBLE);
                    delete.setVisibility(View.INVISIBLE);
                    mealTypeSpinner.setVisibility(View.INVISIBLE);
                }
            }
        });

        deleteMealType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                meal_type_edittxt.setVisibility(View.INVISIBLE);
                addFloatingActionButton.hide();
                deleteFloatingActionButton.show();
                    add.setVisibility(View.INVISIBLE);
                    delete.setVisibility(View.VISIBLE);
                mealTypeSpinner.setVisibility(View.VISIBLE);}
                else {
                    meal_type_edittxt.setVisibility(View.INVISIBLE);
                    addFloatingActionButton.hide();
                    deleteFloatingActionButton.hide();
                    add.setVisibility(View.INVISIBLE);
                    delete.setVisibility(View.INVISIBLE);
                    mealTypeSpinner.setVisibility(View.INVISIBLE);
                }

            }
        });

        addFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final String val=meal_type_edittxt.getText().toString();
                Call<meal_type>call =resturantApiInterface.addMealType(val,2);
                call.enqueue(new Callback<meal_type>() {
                    @Override
                    public void onResponse(Call<meal_type> call, Response<meal_type> response) {

                            meal_type meal_type = response.body();
                            Toast.makeText(getActivity(), "تمت الإضافة بنجاح " + val, Toast.LENGTH_LONG);
                        }

                    @Override
                    public void onFailure(Call<meal_type> call, Throwable t) {
                        Toast.makeText(getActivity(),"حدث خطأفي الإضافة "+val,Toast.LENGTH_SHORT);

                    }
                });
            }
        });


        deleteFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }

    private void fillSpinner(){

        Call<List<meal_type>>call=resturantApiInterface.getMealType(3);
        call.enqueue(new Callback<List<meal_type>>() {
            @Override
            public void onResponse(Call<List<meal_type>> call, Response<List<meal_type>> response) {
                meal_types=response.body();
                ArrayAdapter aa = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item);
                aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                for(int i=0;i<meal_types.size();i++)
                {aa.add(meal_types.get(i).getMeal_type());}
                mealTypeSpinner.setAdapter(aa);
            }

            @Override
            public void onFailure(Call<List<meal_type>> call, Throwable t) {

            }
        });



    }

}