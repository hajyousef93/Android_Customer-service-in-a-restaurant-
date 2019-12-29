package com.example.easyorder.User.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.example.easyorder.DataBase.CartDataBase;
import com.example.easyorder.DataBase.CartModel;
import com.example.easyorder.R;


import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    CartDataBase orderDataBase;
   List<CartModel> data=new ArrayList<>();
    public CartAdapter(Context context,List<CartModel> data){

        this.context=context;
        this.data=data;
    }
    public void removeItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, data.size());
    }
    public void restoreItem(CartModel mode, int position) {
        data.add(position, mode);
        // notify item added by position


        notifyItemInserted(position);
    }
    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_row,viewGroup,false);
        return new CartAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder viewHolder, final int i) {
      TextDrawable drawable=TextDrawable.builder().buildRound(""+data.get(i).getQuantity(), Color.BLACK);
        viewHolder.name.setText(data.get(i).getMeal_name());
        int totalprice=0;
        totalprice=((data.get(i).getPrice()))*((data.get(i).getQuantity()));
        viewHolder.price.setText(Integer.toString(totalprice));
        viewHolder.imageView.setImageDrawable(drawable);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public void onItemDismiss(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }



    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView,deleteItem;
        public TextView name,price;
        public RelativeLayout ViewForward;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
           imageView=itemView.findViewById(R.id.cart_item_count);
           name=itemView.findViewById(R.id.cart_item_name);
           price=itemView.findViewById(R.id.cart_item_Price);
            deleteItem=itemView.findViewById(R.id.deleteItem);
            ViewForward=itemView.findViewById(R.id.view_foreground);

        }


        @Override
        public void onClick(View v) {

        }
    }
}
