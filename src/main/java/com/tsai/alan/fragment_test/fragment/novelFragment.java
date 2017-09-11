package com.tsai.alan.fragment_test.fragment;


import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tsai.alan.fragment_test.R;
import com.tsai.alan.fragment_test.Setting;
import com.tsai.alan.fragment_test.Broadcast.SettingReceiver;
import com.tsai.alan.fragment_test.data.homeData;
import com.tsai.alan.fragment_test.message.readNovelMesage;

/**
 * A simple {@link Fragment} subclass.
 */
public class novelFragment extends BasePagerFragment {
    private View view;
    private TextView novel;
    private int position;
    private homeData data;
    private readNovelMesage rNovelMesage;
    private ProgressBar progressBar;
    private ScrollView scrollSize;
    private Toolbar toolbar;
    private View settingView;
    private LinearLayout bgLayout;

    private novelFragment(int position, homeData data, Toolbar toolbar, View settingView) {
        this.data = data;
        this.position = position;
        this.toolbar = toolbar;
        this.settingView = settingView;
    }
    public static novelFragment newInstance(int position, homeData data, Toolbar toolbar, View settingView){
        return new novelFragment(position,data,toolbar,settingView );
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_novel, container, false);

        bgLayout = (LinearLayout)view.findViewById(R.id.read_bg_id);
        novel = (TextView)view.findViewById(R.id.read_id);

        initSetting();

        scrollSize = (ScrollView)view.findViewById(R.id.scrollView2);
        scrollSize.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            int startY = 0;
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if((startY+1000)<scrollY||(startY-1000)>scrollY){
                    Log.i("onScrollChange","x="+startY+" oldx="+scrollY);
                    startY=scrollY;
                    Intent intentt = new Intent();
                    intentt.putExtra("scrollY",scrollY);
                    intentt.setAction("SCROLLY");
                    LocalBroadcastManager.getInstance(getContext()).registerReceiver(SettingReceiver.newInstance(),new IntentFilter("SCROLLY"));
                    LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intentt);
                }


            }
        });

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar2);
        rNovelMesage = new readNovelMesage(data,novel,progressBar,scrollSize,getActivity());

        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.openSet);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(settingView.getVisibility()==view.GONE){
                    settingView.setVisibility(view.VISIBLE);
                }else {
                    settingView.setVisibility(view.GONE);
                }
            }
        });
        return view;
    }

    private void initSetting() {
        switch(Setting.bgm.getbgModel()){
            case 1:
                Drawable blackDrawable = getResources().getDrawable(R.color.black);
                bgLayout.setBackground(blackDrawable);
                novel.setTextColor(getResources().getColor(R.color.white));
                break;
            case 2:
                Drawable whiteDrawable = getResources().getDrawable(R.color.white);
                bgLayout.setBackground(whiteDrawable);
                novel.setTextColor(getResources().getColor(R.color.black));
        }
        novel.setTextSize(Setting.textSize);
        SettingReceiver.newInstance().setSettingView(novel,bgLayout);
    }


    public void saveScrollView() {
        if(scrollSize.getScrollY()!=0){
            data.setBookmarks( scrollSize.getScrollY());
        }

    }

    @Override
    protected void prepareFetchData(boolean forceUpdate) {
        super.prepareFetchData(true);
    }
    @Override
    public void loadData() {
        rNovelMesage.fetchData(position);
        SettingReceiver.newInstance().setScrollView(scrollSize);
    }
}
