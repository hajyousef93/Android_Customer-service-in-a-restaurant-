package com.example.easyorder.User.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.easyorder.R;
import com.example.easyorder.User.Fregments.Top_meals_fragment;
import com.example.easyorder.User.Fregments.Home_fragment;
import com.example.easyorder.User.Adapter.ViewPagerAdapter;

public class HomeActivity extends AppCompatActivity {
    private int id_account;
    private String user_name;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        id_account=Integer.parseInt(getIntent().getStringExtra("id_account"));
        viewPager=findViewById(R.id.viewPager);
        tabLayout=findViewById(R.id.tabLayout);
        ViewPagerAdapter viewPagerAdapter= new ViewPagerAdapter(getSupportFragmentManager());
        Home_fragment m = new Home_fragment();
        Bundle bundle= new Bundle();
        bundle.putInt("id_account",id_account);
        m.setArguments(bundle);
        viewPagerAdapter.AddFragment(m,"المطاعم");
        viewPagerAdapter.AddFragment(new Top_meals_fragment(),"الوجبات الأكثر طلباً");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
