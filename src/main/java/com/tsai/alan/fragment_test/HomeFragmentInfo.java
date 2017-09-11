package com.tsai.alan.fragment_test;

import android.support.v4.app.Fragment;

/**
 * Created by Alan on 2017/8/31.
 */

public class HomeFragmentInfo {
    private  String title;

    private Fragment fragment;

    public HomeFragmentInfo(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

}
