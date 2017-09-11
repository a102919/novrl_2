package com.tsai.alan.fragment_test.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.tsai.alan.fragment_test.Broadcast.MaekBroadcastReceiver;
import com.tsai.alan.fragment_test.R;
import com.tsai.alan.fragment_test.SQLite.SqlAdapter;
import com.tsai.alan.fragment_test.data.homeData;

import java.util.List;
/**
 * Created by Alan on 2017/8/31.
 */

public class CardAdapter extends BaseRecyclerAdapter {
    private Context context;
    private boolean mark = false;
    private SqlAdapter sqlAdapter;
    public static final String BROADCAST_ACTION = "MARK";
    private List<String> markList;
    private String DataId;

    public CardAdapter(@LayoutRes int layoutId, Context context) {
        super(layoutId);
        this.context = context;
        sqlAdapter = new SqlAdapter(context);
        markList = sqlAdapter.readMark();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onBindViewHolder(SmartViewHolder holder, Object model, final int position) {
        holder.text(R.id.title_id, ((homeData) getmList().get(position)).getTitle());
        holder.text(R.id.author_id, ((homeData) getmList().get(position)).getAuthor());
        holder.text(R.id.classification_id, ((homeData) getmList().get(position)).getClassification());
        holder.text(R.id.page_id, ((homeData) getmList().get(position)).getPage() + "章");
        //holder.image(R.id.mark_button,R.mipmap.mark_yes);
        final View view = holder.findViewById(R.id.mark_button);
        if (view instanceof ImageView) {
            DataId = ((homeData) getmList().get(position)).getId() ;
            if (sqlAdapter.searchMark(DataId)) {
                mark = true;
                setMarkIcon((ImageView)view, R.drawable.mark_yes_icon);
            } else {
                mark = false;
                setMarkIcon((ImageView)view, R.drawable.mark_no_icon);
            }
            ((ImageView) view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mark) {
                        setMarkIcon((ImageView)view, R.drawable.mark_no_icon);
                        mark = false;
                        ((homeData) getmList().get(position)).setMark(mark);
                        sqlAdapter.deletMark(DataId);
                        coMarkBroadcast();
                    } else {
                        setMarkIcon((ImageView)view, R.drawable.mark_yes_icon);
                        mark = true;
                        ((homeData) getmList().get(position)).setMark(mark);
                        //((homeData) getmList().get(position)).saveNovel();
                        sqlAdapter.insertMark(DataId);
                        //Toast.makeText(view.getContext(), "新增"+new_id, Toast.LENGTH_LONG).show();
                        coMarkBroadcast();
                    }
                }
            });
        }
        //SettingReceiver.newInstance().setSettingView(holder);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setMarkIcon(ImageView view, int mark_yes_icon) {
        Resources resources = context.getResources();
        Drawable btnDrawable = resources.getDrawable(mark_yes_icon);
        view.setBackground(btnDrawable);
    }

    private void coMarkBroadcast() {
        Intent intentt = new Intent();
        intentt.setAction(BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(context).registerReceiver(MaekBroadcastReceiver.newInstance(), new IntentFilter(BROADCAST_ACTION));
        LocalBroadcastManager.getInstance(context).sendBroadcast(intentt);
    }
}