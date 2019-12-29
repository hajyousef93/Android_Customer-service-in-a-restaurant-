package com.example.easyorder.ResturantManager;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.easyorder.R;
import com.example.easyorder.User.Adapter.DetailAdapter;
import com.example.easyorder.classes.DetailsModel;
import com.example.easyorder.connector.ResturantApiInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.easyorder.ResturantManager.Manager_Home_Activity.resturant;

public class Main_Fragment extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private DetailAdapter rcAdapter;
    private ResturantApiInterface resturantApiInterface;
    private TextView resnametextView;
    private String imageBaseUrl="http://192.168.43.73:8080/easy%20order/images/";
    private ImageView resimageView;

    public Main_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\

        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        getAndSetUis();
        return rootView;
    }


    private void getAndSetUis() {
        recyclerView = rootView.findViewById(R.id.manger_recycler);
        resimageView=rootView.findViewById(R.id.resturant_cover_image_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL,
                false);

        Picasso.with(getActivity()).load(imageBaseUrl+resturant.getResturant_image()).into(resimageView);

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
        model.setTitle("Resturant Name");
        model.setValue(resturant.getResturant_name());
        model.setIconResId(R.drawable.ic_local_dining_black_24dp);
        models.add(model);

        model = new DetailsModel();
        model.setTitle("Resturant Address");
        model.setValue(resturant.getResturant_address());
        model.setIconResId(R.drawable.ic_location_on_black_24dp);
        models.add(model);

        model = new DetailsModel();
        model.setTitle("Resturant Manger");
        model.setValue(resturant.getManager_name());
        model.setIconResId(R.drawable.ic_person_black_24dp);
        models.add(model);

        model = new DetailsModel();
        model.setTitle("Resturant Cooker");
        model.setValue(resturant.getCooker_name());
        model.setIconResId(R.drawable.ic_perm_identity_black_24dp);
        models.add(model);
        return models;
    }
}
