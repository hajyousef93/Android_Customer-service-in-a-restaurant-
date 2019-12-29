package com.example.easyorder.ResturantCooker.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.example.easyorder.ResturantCooker.OrderDetails;
import com.example.easyorder.connector.ApiClient;
import com.example.easyorder.R;
import com.example.easyorder.connector.ResturantApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CookerRecyclerViewAdapter extends RecyclerView.Adapter<CookerRecyclerViewAdapter.MyViewHolder> {
       public Context context;
       List<OrderDetails>orderDetails;
    private ResturantApiInterface resturantApiInterface;
public CookerRecyclerViewAdapter(Context context, List<OrderDetails>orderDetails){
        this.context=context;
        this.orderDetails=orderDetails;
    resturantApiInterface = ApiClient.getApiClient().create(ResturantApiInterface.class);
        }

@NonNull
@Override
public CookerRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_row,viewGroup,false);
        return new CookerRecyclerViewAdapter.MyViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull CookerRecyclerViewAdapter.MyViewHolder viewHolder, final int i) {
       TextDrawable drawable=TextDrawable.builder().buildRound(""+orderDetails.get(i).getQuantity(), Color.BLACK);
        viewHolder.Meal_Name.setText(orderDetails.get(i).getName_food());
        viewHolder.Order_id.setText(Integer.toString(orderDetails.get(i).getId_order()));
        viewHolder.quantity.setImageDrawable(drawable);
        viewHolder.Done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Call<OrderDetails> call = resturantApiInterface.RemoveOrder(orderDetails.get(i).getId_order_resturant_food(),22);
                call.enqueue(new Callback<OrderDetails>() {
                    @Override
                    public void onResponse(Call<OrderDetails> call, Response<OrderDetails> response) {
                        OrderDetails ordersDetails=response.body();
removeItem(i);
                    }

                    @Override
                    public void onFailure(Call<OrderDetails> call, Throwable t) {

                    }
                });
            }
        });

        }



@Override
public int getItemCount() {
        return orderDetails.size();
        }
    public void removeItem(int position) {
        orderDetails.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, orderDetails.size());
    }


public class MyViewHolder extends RecyclerView.ViewHolder  {
    public ImageView quantity;
    public TextView Meal_Name, Order_id;
    public RelativeLayout Cooker_layout;
    public CheckBox Done;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        quantity = itemView.findViewById(R.id.quantity);
        Meal_Name = itemView.findViewById(R.id.Meal_name);
        Order_id = itemView.findViewById(R.id.Order_id);
        Done = itemView.findViewById(R.id.done);
        Cooker_layout = itemView.findViewById(R.id.Cooker_layout);

    }
}
}

