package com.example.easyorder.User.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyorder.classes.DetailsModel;
import com.example.easyorder.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DetailsModel> data;
    private DetailsClickListener clickListener;

    public DetailAdapter(ArrayList<DetailsModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailsViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.details_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DetailsViewHolder viewHolder = (DetailsViewHolder) holder;
        viewHolder.detailsTitleTextView.setText(data.get(position).getTitle());
        viewHolder.detailsValueTextView.setText(data.get(position).getValue());
        Picasso.with(viewHolder.detailsImageView.getContext()).load(data.get(position).getIconResId())
                .into(viewHolder.detailsImageView);

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
        private TextView detailsValueTextView;

        public DetailsViewHolder(View itemView) {
            super(itemView);
            detailsImageView = itemView.findViewById(R.id.details_icon_image_view);
            detailsTitleTextView = itemView.findViewById(R.id.details_title_text_view);
            detailsValueTextView = itemView.findViewById(R.id.details_value_text_view);
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

