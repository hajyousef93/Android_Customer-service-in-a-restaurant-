package com.example.easyorder.User.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyorder.User.Activity.MenuActivity;
import com.example.easyorder.R;
import com.example.easyorder.classes.meal_type;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MealTypeAdapter extends RecyclerView.Adapter<MealTypeAdapter.MyViewHolder>{
    Context context;
      int id_resturant,id_reservation,reservation_type;
    List<meal_type>meal_types ;
    private String imageBaseUrl="http://192.168.43.73:8080/easy%20order/images/";
    public MealTypeAdapter(List<meal_type>meal_types,Context context,int id_resturant,int id_reservation,int reservation_type){
        this.meal_types=meal_types;
        this.context=context;
        this.id_resturant=id_resturant;
        this.id_reservation=id_reservation;
        this.reservation_type=reservation_type;
    }
    @NonNull
    @Override
    public MealTypeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mealtype_row,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealTypeAdapter.MyViewHolder viewHolder, final int i) {
        viewHolder.meal_type.setText(meal_types.get(i).getMeal_type());
       if(meal_types.get(i).getId_meal()==1){
           viewHolder.imageVieww.setImageResource(R.drawable.photoss);
       }
       else if(meal_types.get(i).getId_meal()==2){
           viewHolder.imageVieww.setImageResource(R.drawable.dinner);
       } else if(meal_types.get(i).getId_meal()==3){
           viewHolder.imageVieww.setImageResource(R.drawable.drinks);
       } else if(meal_types.get(i).getId_meal()==4){
           viewHolder.imageVieww.setImageResource(R.drawable.disert);
       } else if(meal_types.get(i).getId_meal()==5){
           viewHolder.imageVieww.setImageResource(R.drawable.uptizers);
       } else if(meal_types.get(i).getId_meal()==6){
           viewHolder.imageVieww.setImageResource(R.drawable.snaks);
       } else if(meal_types.get(i).getId_meal()==7){
           viewHolder.imageVieww.setImageResource(R.drawable.hot);
       } else if(meal_types.get(i).getId_meal()==8){
           viewHolder.imageVieww.setImageResource(R.drawable.sand);
       }
       // Picasso.with(context).load(imageBaseUrl+meal_types.get(i).getMeal_image()).into(viewHolder.imageVieww);
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, MenuActivity.class);
                intent.putExtra("id_meal",Integer.toString(meal_types.get(i).getId_meal()));
                intent.putExtra("id_resturant",Integer.toString(id_resturant));
                intent.putExtra("id_reservation",Integer.toString(id_reservation));
                intent.putExtra("reservation_type",Integer.toString(reservation_type));
                context.startActivity(intent);            }
        });
    }

    @Override
    public int getItemCount() {
        return meal_types.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView meal_type;
        CardView linearLayout;
        ImageView imageVieww;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout= (CardView) itemView.findViewById(R.id.meal_card);
            meal_type=(TextView) itemView.findViewById(R.id.meal_type_tv);
            imageVieww=(ImageView)itemView.findViewById(R.id.meal_type_image);

        }



    }
        }
