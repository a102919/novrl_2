package com.tsai.alan.fragment_test.fragment;


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

import com.tsai.alan.fragment_test.Adapter.HomeAdapter;
import com.tsai.alan.fragment_test.Broadcast.MaekBroadcastReceiver;
import com.tsai.alan.fragment_test.R;
import com.tsai.alan.fragment_test.data.homeData;
import com.tsai.alan.fragment_test.message.readMarkMessage;
import com.tsai.alan.fragment_test.model.readModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarkFragment extends Fragment {
private FragmentControl control;

    public MarkFragment(FragmentControl control) {
       this.control = control;
    }
    private View view;
    private HomeAdapter homeAdapter;
    private RecyclerView mRecyclerView;
    private readMarkMessage rMarkMessage;
    private Intent intentt;
    public static final String BROADCAST_ACTION = "HomeFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mark, container, false);
        homeAdapter = new HomeAdapter(R.layout.home_layout,getContext());
        homeAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                homeData data =(homeData)homeAdapter.getItem(position);
                ReadFragment readFragment = new ReadFragment(data);
                control.replaceFragment(readFragment);
            }
        });
        mRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setAdapter(homeAdapter);
        mRecyclerView.setLayoutManager(layoutManager);

        rMarkMessage = new readMarkMessage(getContext(),homeAdapter,getActivity());
        rMarkMessage.fetchData(readModel.initRead);

        MaekBroadcastReceiver.newInstance().registMessage(rMarkMessage);
        return view;
    }
}
