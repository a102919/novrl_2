package com.tsai.alan.fragment_test.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.tsai.alan.fragment_test.Adapter.SmartViewHolder;
import com.tsai.alan.fragment_test.R;
import com.tsai.alan.fragment_test.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alan on 2017/8/10.
 */

// 繼承自BroadcastReceiver的廣播接收元件
public class SettingReceiver extends BroadcastReceiver {
    private static SettingReceiver myBroadcastReceiver = null;
    private SeekBar seekBar;

    private SettingReceiver(){}
    public static SettingReceiver newInstance(){
        if(null == myBroadcastReceiver){
            myBroadcastReceiver = new SettingReceiver();
        }
        return myBroadcastReceiver;
    }
    // 接收廣播後執行這個方法
    // 第一個參數Context物件，用來顯示訊息框、啟動服務
    // 第二個參數是發出廣播事件的Intent物件，可以包含資料
    private List<TextView> textViewList = new ArrayList<>();
    private List<LinearLayout> bgLayoutList = new ArrayList<>();
    private SmartRefreshLayout smartRefreshLayout;
    private Toolbar toolbar;
    private TabLayout tablayout;
    private SmartViewHolder smartViewHolder;
    private ScrollView scrollView;
    public void setSettingView(TextView text,LinearLayout Layout){
        this.textViewList.add(text);
        this.bgLayoutList.add(Layout);
    }
    public void setScrollView(ScrollView scrollView){
        this.scrollView = scrollView;
    }
    public void setSettingView(SmartRefreshLayout smartRefreshLayout){
        this.smartRefreshLayout = smartRefreshLayout;
    }
    public  void setSettingView(Toolbar toolbar){
        this.toolbar = toolbar;
    }
    public void setSettingView(TabLayout tablayout) {
        this.tablayout = tablayout;
    }
    public void setSettingView(SmartViewHolder smartViewHolder) {
        this.smartViewHolder = smartViewHolder;
    }

    public void deleteText(){
        textViewList.clear();
        bgLayoutList.clear();
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // 讀取包含在Intent物件中的資料
        // 因為這不是Activity元件，需要使用Context物件的時候，
        // 不可以使用「this」，要使用參數提供的Context物件
        if(intent.getAction().equals("SCROLLY")){
            final int scrollY = intent.getIntExtra("scrollY", 0);
            double Yscroll = (double)scrollY / scrollView.getChildAt(0).getMeasuredHeight() *100;
            Log.i("barr","scrollX= "+Yscroll);
            seekBar.setProgress((int)Yscroll);
        }

        if(intent.getAction().equals("SORCE")){
            Log.i("barr","BarX= ");
            final int textbar = intent.getIntExtra("textbar", 0);
            double Yscroll = scrollView.getChildAt(0).getMeasuredHeight() * ((double)textbar / 100);
            scrollView.scrollTo(0, (int) Yscroll);
        }

        if(intent.getAction().equals(Setting.settingType.TEXTSIZE_ACTION.toString())){
            int age = intent.getIntExtra("textsia", 20);
            for(TextView textView:textViewList){
                textView.setTextSize(age);
            }

        }else if(intent.getAction().equals(Setting.settingType.Bg_ACTION.toString())){
            Boolean isChecked = intent.getBooleanExtra("bg", false);
            Drawable bg,toolbarcolor;
            int bgColor;
            if(isChecked){
                bg = context.getResources().getDrawable(R.color.black);
                bgColor = context.getResources().getColor(R.color.white);
                toolbarcolor = context.getResources().getDrawable(R.color.toolerColor);
            }else {
                bg = context.getResources().getDrawable(R.color.white);
                bgColor = context.getResources().getColor(R.color.black);
                toolbarcolor = context.getResources().getDrawable(R.color.light_blue);
            }

            for(LinearLayout Layout:bgLayoutList){
                Layout.setBackground(bg);
            }
            for(TextView textView:textViewList){
                textView.setTextColor(bgColor);
            }
            toolbar.setBackground(toolbarcolor);
            tablayout.setBackground(toolbarcolor);
            //smartViewHolder.textColorId(R.id.title_id,bgColor);
        }

    }


    public void setSeekBar(SeekBar seekBar) {
        this.seekBar = seekBar;
    }
}
