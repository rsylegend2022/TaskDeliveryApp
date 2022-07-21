package com.example.android.recyclerview;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    private List<Fragment> mFragments = new ArrayList<>();
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        addFragments();
    }

    private void addFragments() {
        RecyclerViewFragment homeFragment = new RecyclerViewFragment();
        Bundle args = new Bundle();
        args.putString("Type", "all");
        homeFragment.setArguments(args);
        mFragments.add(homeFragment);
        //System.out.println("get Item 1111");
        RecyclerViewFragment sportFragment = new RecyclerViewFragment();
        Bundle args2 = new Bundle();
        args2.putString("Type", "progress");
        sportFragment.setArguments(args2);
        mFragments.add(sportFragment);
        RecyclerViewFragment movieFragment = new RecyclerViewFragment();
        Bundle args3 = new Bundle();
        args3.putString("Type", "finished");
        movieFragment.setArguments(args3);
        mFragments.add(movieFragment);
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mFragments.get(0);
            case 1:
                return mFragments.get(1);
            case 2:
                return mFragments.get(2);
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