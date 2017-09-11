package com.tsai.alan.fragment_test.fragment;



import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tsai.alan.fragment_test.Adapter.HomePagerAdapter;
import com.tsai.alan.fragment_test.HomeFragmentInfo;
import com.tsai.alan.fragment_test.R;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private View view;
    private FragmentControl control;

    public MainFragment(FragmentControl control) {
        this.control = control;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        initTablayout(view);
        return view;
    }
    private List<HomeFragmentInfo> initFragments(){
        List<HomeFragmentInfo> mFragments = new ArrayList<>(3);
        mFragments.add(new HomeFragmentInfo("首頁",new HomeFragment(control)));
        mFragments.add(new HomeFragmentInfo ("書籤",new MarkFragment(control)));
        mFragments.add(new HomeFragmentInfo ("設定",new SetFragment()));
        return  mFragments;
    }
    private void initTablayout(View view) {
        tabLayout = (TabLayout)view.findViewById(R.id.tablayout);
        viewPager = (ViewPager)view.findViewById(R.id.viewpager);
        PagerAdapter adapter = new
                HomePagerAdapter(getActivity().getSupportFragmentManager(),initFragments());
        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);
        //viewPager.notifySubtreeAccessibilityStateChanged();
        // Tablayout 关联 viewPager
        tabLayout.setupWithViewPager(viewPager);
        //SettingReceiver.newInstance().setSettingView(tabLayout);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("string","anAngryAnt");
        super.onSaveInstanceState(outState);
    }
}
