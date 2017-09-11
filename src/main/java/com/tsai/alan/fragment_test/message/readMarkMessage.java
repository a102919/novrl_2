package com.tsai.alan.fragment_test.message;

import android.app.Activity;
import android.content.Context;

import com.tsai.alan.fragment_test.Adapter.HomeAdapter;
import com.tsai.alan.fragment_test.SQLite.SqlAdapter;
import com.tsai.alan.fragment_test.data.homeData;
import com.tsai.alan.fragment_test.model.readModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan on 2017/7/22.
 */

public class readMarkMessage implements MakeData{
    private Context context;
    private HomeAdapter homeAdapter;
    private Activity activity;
    private List<homeData> datas;
    public readMarkMessage(Context context, HomeAdapter homeAdapter, Activity activity) {
        this.context = context;
        this.homeAdapter = homeAdapter;
        this.activity = activity;
    }
    public void fetchData(final readModel model ){
        SqlAdapter sqlAdapter = new SqlAdapter(context);
        datas =new ArrayList<>();
        List<String> idLis = sqlAdapter.readMark();
        for (String id:idLis){
            homeData data = new homeData(context);
            data.takeNovel(id);
            datas.add(data);
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //progressBar.setVisibility(View.VISIBLE);
                //更新列表
                parseDataFinish(datas,model);
            }
        });
    }
    private void parseDataFinish(List<homeData> data,readModel model ) {
        if (readModel.initRead.equals(model)) {
            homeAdapter.refresh(data);
        } else {
            homeAdapter.loadmore(data);
        }
            //progressBar.setVisibility(View.GONE);
    }

    @Override
    public void fetchData() {
        SqlAdapter sqlAdapter = new SqlAdapter(context);
        datas =new ArrayList<>();
        List<String> idLis = sqlAdapter.readMark();
        for (String id:idLis){
            homeData data = new homeData(context);
            data.takeNovel(id);
            datas.add(data);
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //progressBar.setVisibility(View.VISIBLE);
                //更新列表
                homeAdapter.refresh(datas);
            }
        });
    }
}
