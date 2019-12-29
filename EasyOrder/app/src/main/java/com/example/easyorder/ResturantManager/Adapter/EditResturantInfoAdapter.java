package com.example.easyorder.ResturantManager.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyorder.R;
import com.example.easyorder.classes.DetailsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.easyorder.ResturantManager.Manager_Home_Activity.resturant;

public class EditResturantInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    Context context;
    private ArrayList<DetailsModel> data;
    private DetailsClickListener clickListener;
    public static   ArrayList<String> info= new ArrayList<String>();
    private String imageBaseUrl="http://192.168.43.73:8080/easy%20order/images/";
    public EditResturantInfoAdapter(ArrayList<DetailsModel> data,ArrayList<String> info) {
        this.data = data;
        this.info=info;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resturant_info_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DetailsViewHolder viewHolder = (DetailsViewHolder) holder;
        viewHolder.detailsTitleTextView.setText(data.get(position).getTitle());
        viewHolder.detailsValueEditText.setText(data.get(position).getValue());
        Picasso.with(context).load(data.get(position).getIconResId())
                .into(viewHolder.detailsImageView);

        info.add(viewHolder.detailsValueEditText.getText().toString());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setClickListener(DetailsClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void add(DetailsModel model, int position) {
        data.add(position, model);
        notifyItemInserted(position);
    }

    public ArrayList<DetailsModel> getData() {
        return data;
    }

    public class DetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        
        private ImageView detailsImageView;
        private TextView detailsTitleTextView;
        private EditText detailsValueEditText;
        public DetailsViewHolder(View itemView) {
            super(itemView);
            detailsImageView = itemView.findViewById(R.id.info_icon_image_view);
            detailsTitleTextView = itemView.findViewById(R.id.info_title_text_view);
            detailsValueEditText = itemView.findViewById(R.id.info_value_edit_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(clickListener!=null)
                clickListener.onItemClicked(getAdapterPosition(), v);
        }
    }

    public interface DetailsClickListener {
        void onItemClicked(int position, View view);
    }
}