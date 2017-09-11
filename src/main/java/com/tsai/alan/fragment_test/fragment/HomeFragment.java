package com.tsai.alan.fragment_test.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.scwang.smartrefresh.header.FunGameBattleCityHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tsai.alan.fragment_test.Adapter.HomeAdapter;
import com.tsai.alan.fragment_test.Broadcast.MaekBroadcastReceiver;
import com.tsai.alan.fragment_test.R;
import com.tsai.alan.fragment_test.data.homeData;
import com.tsai.alan.fragment_test.message.readHomeMesage;
import com.tsai.alan.fragment_test.model.readModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener{
    public static final String BROADCAST_ACTION = "HomeFragment";
    private HomeAdapter homeAdapter;
    private RecyclerView mRecyclerView;
    private readHomeMesage rHomeMesage;
    private ProgressBar progressBar;
    private View view;
    private FragmentControl control;
    public HomeFragment(FragmentControl control) {
       this.control = control;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return view;
    }
    private void initView() {

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        homeAdapter = new HomeAdapter(R.layout.home_layout,getContext());
        homeAdapter.setOnItemClickListener(this);

        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setAdapter(homeAdapter);
        mRecyclerView.setLayoutManager(layoutManager);

        SmartRefreshLayout refreshLayout = (SmartRefreshLayout)view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                ((View) refreshlayout).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rHomeMesage.fetchData(readModel.initRead);
                        refreshlayout.finishRefresh();
                    }
                }, 2000);
            }
        });
        refreshLayout.setEnableAutoLoadmore(true);
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                ((View) refreshlayout).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rHomeMesage.fetchData(readModel.secondRead);
                        refreshlayout.finishLoadmore();
                        if (rHomeMesage.getListSize() == 0) {
                            Toast.makeText(getActivity().getApplication(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                            refreshlayout.setLoadmoreFinished(true);//将不会再次触发加载更多事件
                        }
                    }
                }, 2000);
            }
        });

        //SettingReceiver.newInstance().setSettingView(refreshLayout);

        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new FunGameBattleCityHeader(context);//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
//设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context);//指定为经典Footer，默认是 BallPulseFooter
            }
        });

        rHomeMesage = new readHomeMesage(homeAdapter,progressBar,getActivity());

        MaekBroadcastReceiver.newInstance().registMessage(rHomeMesage);



    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        homeData data =(homeData)homeAdapter.getItem(position);
        ReadFragment readFragment = new ReadFragment(data);
        control.replaceFragment(readFragment);
    }
}
