package com.example.easyorder.User.Activity;

import android.content.Intent;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyorder.DataBase.*;
import com.example.easyorder.classes.DetailsModel;
import com.example.easyorder.R;
import com.example.easyorder.classes.Resturant;
import com.example.easyorder.User.Adapter.DetailAdapter;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
public class ResturantDetailsActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageView resturantImageView;
    private TextView resturantNameTextView;
    private Toolbar toolbar;
    int id_account;
    private  int id_resturant;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private Resturant resturant;
    private DetailAdapter rcAdapter;
    private String imageBaseUrl="http://192.168.43.73:8080/easy%20order/images/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_details);
        new CartDataBase(getApplicationContext()).cleanCart();
        resturant = (Resturant) getIntent().getSerializableExtra("RESTURANT_OBJECT");


        bindViews();
        setUpTheToolbar();
        getAndSetUis();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  showChooseActionDialog();
                Intent intent= new Intent(ResturantDetailsActivity.this, Reservation_Activity.class);
                id_account=Integer.parseInt(getIntent().getStringExtra("id_account"));
                intent.putExtra("id_account",Integer.toString(id_account));
                intent.putExtra("id_resturant",Integer.toString(resturant.getId_resturant()));
                startActivity(intent);}
        });
        Picasso.with(this).load(imageBaseUrl+resturant.getResturant_image())
                .into(resturantImageView);
        resturantNameTextView.setText(resturant.getResturant_name());
    }


    private void bindViews() {
        collapsingToolbarLayout = findViewById(R.id.resturant_details_collapsing_tool_bar);
        resturantImageView = findViewById(R.id.resturant_cover_image_view);
        resturantNameTextView = findViewById(R.id.details_resturant_name_text_view);
        toolbar = findViewById(R.id.resturant_details_toolbar);
        recyclerView = findViewById(R.id.resturant_details_recycler_view);
        floatingActionButton = findViewById(R.id.resturant_floating_action_button);
    }

    private void setUpTheToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(resturant.getResturant_name());
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setTitle(" ");
    }

    private void getAndSetUis() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
//                super.onDraw(c, parent, state);
            }
        });
        ArrayList<DetailsModel> models = getItems();
        rcAdapter = new DetailAdapter(models);
        recyclerView.setAdapter(rcAdapter);
    }

    private ArrayList<DetailsModel> getItems() {
        ArrayList<DetailsModel> models = new ArrayList<>();

        DetailsModel model = new DetailsModel();
        model.setTitle(" اسم المطعم");
        model.setValue(resturant.getResturant_name());
        model.setIconResId(R.drawable.ic_local_dining_black_24dp);
        models.add(model);

        model = new DetailsModel();
        model.setTitle(" عنوان المطعم");
        model.setValue(resturant.getResturant_address());
        model.setIconResId(R.drawable.ic_location_on_black_24dp);
        models.add(model);

        model = new DetailsModel();
        model.setTitle(" اسم مدير المطعم");
        model.setValue(resturant.getManager_name());
        model.setIconResId(R.drawable.ic_person_black_24dp);
        models.add(model);

        model = new DetailsModel();
        model.setTitle(" اسم مدير الطهاة");
        model.setValue(resturant.getCooker_name());
        model.setIconResId(R.drawable.ic_perm_identity_black_24dp);
        models.add(model);
        return models;
    }


}
