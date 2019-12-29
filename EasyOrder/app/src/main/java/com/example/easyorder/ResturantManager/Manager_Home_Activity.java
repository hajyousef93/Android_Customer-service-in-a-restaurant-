package com.example.easyorder.ResturantManager;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.easyorder.R;
import com.example.easyorder.ResturantManager.Adapter.ManagerPagerAdapter;
import com.example.easyorder.classes.Resturant;


public class Manager_Home_Activity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public static int id_account;
    public static int id_Cooker;
    public static Resturant resturant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager__home_);

        id_account=getIntent().getIntExtra("id_account",2000);
        resturant= (Resturant) getIntent().getSerializableExtra("RESTURANT_OBJECT");
        id_Cooker=resturant.getId_cooker();
        getAndSetUi();
    }

    private void getAndSetUi() {
        toolbar = findViewById(R.id.home_manager_toolbar);
        tabLayout = findViewById(R.id.home_manager_tab_layout);
        viewPager = findViewById(R.id.home_manager_view_pager);
        setUpTheToolbar();
        setUpTheViewPager();
    }

    private void setUpTheViewPager() {
        String [] titles = new String[]{"الأساسية","تعديل معلومات المطعم"," أصناف الطعام","الوجبات"};
        ManagerPagerAdapter pagerAdapter = new ManagerPagerAdapter(getSupportFragmentManager(),
                titles,id_account,resturant);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpTheToolbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle("الأساسية");
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
