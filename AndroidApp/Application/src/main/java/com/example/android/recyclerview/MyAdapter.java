package com.example.android.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                RecyclerViewFragment homeFragment = new RecyclerViewFragment();
                Bundle args = new Bundle();
                args.putString("Type", "all");
                homeFragment.setArguments(args);
                return homeFragment;
            case 1:
                RecyclerViewFragment sportFragment = new RecyclerViewFragment();
                Bundle args2 = new Bundle();
                args2.putString("Type", "progress");
                sportFragment.setArguments(args2);
                return sportFragment;
            case 2:
                RecyclerViewFragment movieFragment = new RecyclerViewFragment();
                Bundle args3 = new Bundle();
                args3.putString("Type", "finished");
                movieFragment.setArguments(args3);
                return movieFragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}