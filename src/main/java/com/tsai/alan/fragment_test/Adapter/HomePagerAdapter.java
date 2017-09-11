package com.tsai.alan.fragment_test.Adapter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tsai.alan.fragment_test.HomeFragmentInfo;

import java.util.List;

/**
 * Created by Alan on 2017/7/22.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private List<HomeFragmentInfo> mFragments;
    private Context context;

    public HomePagerAdapter(FragmentManager fm,List<HomeFragmentInfo> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return  mFragments.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).getTitle();
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO Auto-generated method stub
        return POSITION_NONE;
    }
}
