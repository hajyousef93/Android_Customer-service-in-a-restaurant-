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
import com.example.easyorder.classes.Resturant;
import com.example.easyorder.User.Activity.ResturantDetailsActivity;
import com.squareup.picasso.Picasso;
import java.util.List;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
 Context context;
    List<Resturant> resturants;
    private String imageBaseUrl="http://192.168.137.1:8080/easy%20order/images/";
    int id_account;
    public RecyclerAdapter(List<Resturant>resturants,Context context,int id_account){
        this.resturants=resturants;
        this.context=context;
        this.id_account=id_account;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.restaurant_row,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, final int i) {
        viewHolder.Resturant_name.setText(resturants.get(i).getResturant_name());
        viewHolder.Resturant_address.setText(resturants.get(i).getResturant_address());

        Picasso.with(context).load(imageBaseUrl+resturants.get(i).getResturant_image()).into(viewHolder.imageView);
             viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, ResturantDetailsActivity.class);
                intent.putExtra("id_resturant",Integer.toString(resturants.get(i).getId_resturant()));
                intent.putExtra("id_account",Integer.toString(id_account));
                intent.putExtra("RESTURANT_OBJECT",resturants.get(i));
                context.startActivity(intent);            }
        });
    }
    public void addAll(List<Resturant> resturants) {
        for (Resturant model : resturants) {
            add(model);
        }
    }

    private void add(Resturant model) {
        resturants.add(model);
        notifyItemInserted(resturants.size()-1);
    }
    @Override
    public int getItemCount() {
        return resturants.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
       TextView Resturant_name,Resturant_address;
       ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.ImageView);
            Resturant_name= itemView.findViewById(R.id.resturant_name);
            Resturant_address= itemView.findViewById(R.id.resturant_address);

        }


    }
}

