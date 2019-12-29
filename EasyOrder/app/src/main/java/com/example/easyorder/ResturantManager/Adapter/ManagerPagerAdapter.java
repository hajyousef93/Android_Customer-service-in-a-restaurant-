package com.example.easyorder.ResturantManager.Adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.example.easyorder.ResturantManager.EditFragment;
import com.example.easyorder.ResturantManager.Main_Fragment;
import com.example.easyorder.ResturantManager.MealTypeFragment;
import com.example.easyorder.ResturantManager.MenuFragment;
import com.example.easyorder.classes.Resturant;


public class ManagerPagerAdapter extends FragmentStatePagerAdapter {

    private String [] titles;
    private int id_acc;
    private Resturant resturant;

    public ManagerPagerAdapter(FragmentManager fm, String [] titles, int id_acc , Resturant resturant) {
        super(fm);
        this.titles = titles;
        this.id_acc=id_acc;
        this.resturant=resturant;
    }


    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0: // Fragment # 0 - This will show FirstFragment
                Bundle bundle=new Bundle();
                bundle.putSerializable("RESTURANT_OBJECT",resturant);
                Main_Fragment main_fragment=new Main_Fragment();
                main_fragment.setArguments(bundle);
                return main_fragment;

            case 1: // Fragment # 1 - This will show second Fragment different title

                return new EditFragment();

            case 2: // Fragment # 2 - This will show Third Fragment different title
                return new MealTypeFragment();

                case 3: // Fragment # 0 - This will show fourth Fragment different title
                return new MenuFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return titles[0];
            case 1:
                return titles[1];
            case 2:
                return titles[2];
            case 3:
                return titles[3];
        }
        return "";
    }
}
