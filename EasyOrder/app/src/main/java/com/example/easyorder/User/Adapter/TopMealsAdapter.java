package com.example.easyorder.User.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyorder.R;
import com.example.easyorder.classes.Restaurant_food;
import com.example.easyorder.User.Activity.MealDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TopMealsAdapter extends RecyclerView.Adapter<TopMealsAdapter.MyViewHolder> {

    Context context;
    List<Restaurant_food> dataList;
    private String imageBaseUrl="http://192.168.43.73:8080/easy%20order/images/";

    public TopMealsAdapter(List<Restaurant_food>dataList, Context context){
        this.dataList = dataList;
        this.context=context;
    }
    @NonNull
    @Override
    public TopMealsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.menu_raw,viewGroup,false);

        return new TopMealsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopMealsAdapter.MyViewHolder viewHolder, final int i) {
        viewHolder.mealNameTextView.setText(dataList.get(i).getMealName());
        viewHolder.mealPriceTextView.setText(String.valueOf(dataList.get(i).getPrice()));
        Picasso.with(context).load(imageBaseUrl+dataList.get(i).getImage()).into(viewHolder.mealImageView);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MealDetailActivity.class);
                intent.putExtra("id_resturant",dataList.get(i).getId_resturant());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void addAll(List<Restaurant_food> resturant_foods) {
        for (Restaurant_food model : resturant_foods) {
            add(model);
        }
    }

    private void add(Restaurant_food model) {
        dataList.add(model);
        notifyItemInserted(dataList.size()-1);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImageView;
        TextView mealNameTextView;
        TextView mealPriceTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImageView = itemView.findViewById(R.id.food_image_iv);
            mealNameTextView = itemView.findViewById(R.id.food_name_tv);
            mealPriceTextView = itemView.findViewById(R.id.food_price_tv);
        }

    }
}
