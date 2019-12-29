package com.example.easyorder.User.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyorder.classes.Menu;
import com.example.easyorder.R;
import com.example.easyorder.User.Activity.MealDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuRecyclerAdapter extends RecyclerView.Adapter<MenuRecyclerAdapter.MyViewHolder>{

    Context context;
    List<Menu> menus;
     int id_food,id_reservation,reservation_type;
    private String imageBaseUrl="http://192.168.43.73:8080/easy%20order/images/";
    public MenuRecyclerAdapter(List<Menu>menus,Context context,int id_reservation,int reservation_type){
        this.menus=menus;
        this.context=context;
        this.reservation_type=reservation_type;
        this.id_reservation=id_reservation;
    }
    @NonNull
    @Override
    public MenuRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_row,viewGroup,false);


        return new MenuRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuRecyclerAdapter.MyViewHolder viewHolder, final int i) {
        viewHolder.food_name.setText(menus.get(i).getMealName());
        viewHolder.price.setText(String.valueOf(menus.get(i).getPrice()));

        Picasso.with(context).load(imageBaseUrl+menus.get(i).getImage()).into(viewHolder.food_image);


        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, MealDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Menu_Object",menus.get(i));
                intent.putExtra("reservation_type",Integer.toString(reservation_type));
                intent.putExtra("id_reservation",Integer.toString(id_reservation));
                context.startActivity(intent);            }
        });

    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView food_name,price;
        android.support.constraint.ConstraintLayout constraintLayout;
        ImageView food_image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout=(ConstraintLayout) itemView.findViewById(R.id.con_layout);
            food_image=(ImageView)itemView.findViewById(R.id.food_image_iv);
            food_name=(TextView) itemView.findViewById(R.id.food_name_tv);
            price=(TextView) itemView.findViewById(R.id.food_price_tv);
            constraintLayout=itemView.findViewById(R.id.con_layout);

        }

    }
}
