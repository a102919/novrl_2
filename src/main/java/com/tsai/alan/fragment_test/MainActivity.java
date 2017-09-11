package com.tsai.alan.fragment_test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tsai.alan.fragment_test.fragment.FragmentControl;
import com.tsai.alan.fragment_test.fragment.MainFragment;

public class MainActivity extends AppCompatActivity implements FragmentControl {
    private FragmentManager manager;
    private MainFragment mainFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        mainFragment = new MainFragment(this);
        transaction.add(R.id.home_fragmentLayout,mainFragment);
        transaction.commit();

    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        //transaction.replace(R.id.home_fragmentLayout,fragment);

        transaction.addToBackStack(fragment.getClass().getName());
        transaction.hide(mainFragment);
        transaction.add(R.id.home_fragmentLayout , fragment);

        transaction.commit();
    }
}
