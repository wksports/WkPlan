package com.example.todolist.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todolist.R;
import com.example.todolist.bean.DayStatus;
import com.example.todolist.db.DayStatusDao;
import com.example.todolist.db.ListItemDao;
import com.example.todolist.receiver.MyService;
import com.example.todolist.utils.DateUtil;
import com.example.todolist.utils.LogUtil;
import com.example.todolist.utils.ToastUtil;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private View homeTab;
    private ImageView homeImg;
    private TextView homeText;
    private View calendarTab;
    private ImageView calendarImg;
    private TextView calendarText;
    private View graphTab;
    private ImageView graphImg;
    private TextView graphText;
    private View alarmTab;
    private ImageView alarmImg;
    private TextView alarmText;
    private FragmentManager fragmentManager;
    private ListFragment homeFragment;
    private DateFragment calendarFragment;
    private GraphFragment graphFragment;
    private AlarmFragment alarmFragment;
    private static final String[] FRAGMENT_TAGS=
            new String[]{"homeFragment","calendarFragment"};
    private int savedIndex=0;
    private long preTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager=getSupportFragmentManager();
        initLastData();
        initViews();
        initToolbar();
        initListener();
        if(savedInstanceState!=null){
            savedIndex=savedInstanceState.getInt("savedIndex");
        }
        setTabSelection(savedIndex);
    }
    private void initLastData(){
        SharedPreferences preferences=getSharedPreferences("list",MODE_PRIVATE);
        String lastVisitTime=preferences.getString("lastVisitTime","");
        Calendar tempCalendar=Calendar.getInstance();
        String todayTime= DateUtil.getYearMonthDayNumberic(tempCalendar.getTime());
        if(!lastVisitTime.equals("")){
            if(!lastVisitTime.equals(todayTime)){
                DayStatus dayStatus=ListItemDao.updateNoRecord(lastVisitTime);
                if(dayStatus!=null){
                    DayStatusDao.insertDayStatus(dayStatus);
                }
            }
        }
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("lastVisitTime",todayTime);
        editor.apply();
    }
    private void initViews(){
        toolbar=findViewById(R.id.main_toolbar);
        homeTab=findViewById(R.id.home_tab);
        homeImg=findViewById(R.id.home_img);
        homeText=findViewById(R.id.home_text);
        calendarTab=findViewById(R.id.calendar_tab);
        calendarImg=findViewById(R.id.calendar_img);
        calendarText=findViewById(R.id.calendar_text);

    }
    private void initToolbar(){
        setSupportActionBar(toolbar);
    }

    private void initListener(){
        homeTab.setOnClickListener(this);
        calendarTab.setOnClickListener(this);

    }
    private void setTabSelection(int index){
        clearSelection();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        hideFragments(transaction);
        switch (index){
            case 0:
                savedIndex=0;
                toolbar.setTitle(getResources().getString(R.string.home));
                homeImg.setImageResource(R.drawable.home2);
                homeText.setTextColor(getResources().getColor(R.color.colorPrimary));
                if(homeFragment==null){
                    homeFragment=new ListFragment();
                    transaction.add(R.id.main_content,homeFragment,FRAGMENT_TAGS[0]);
                }else{
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                savedIndex=1;
                toolbar.setTitle(getResources().getString(R.string.calendar));
                calendarImg.setImageResource(R.drawable.calendar2);
                calendarText.setTextColor(getResources().getColor(R.color.colorPrimary));
                if(calendarFragment==null){
                    calendarFragment=new DateFragment();
                    transaction.add(R.id.main_content,calendarFragment,FRAGMENT_TAGS[1]);
                }else{
                    transaction.show(calendarFragment);
                }
                break;

        }
        transaction.commit();
    }
    private void clearSelection(){
        homeImg.setImageResource(R.drawable.home);
        homeText.setTextColor(getResources().getColor(R.color.day_text_color));
        calendarImg.setImageResource(R.drawable.calendar);
        calendarText.setTextColor(getResources().getColor(R.color.day_text_color));

    }
    private void hideFragments(FragmentTransaction transaction){
        if(homeFragment!=null){
            transaction.hide(homeFragment);
        }
        if(calendarFragment!=null){
            transaction.hide(calendarFragment);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_tab:
                setTabSelection(0);
                break;
            case R.id.calendar_tab:
                setTabSelection(1);
                break;

        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("savedIndex",savedIndex);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            long curTime=System.currentTimeMillis();
            if ((curTime - preTime) > 1000 * 2) {
                ToastUtil.showToast("再按一次返回上一级");
                preTime = curTime;
            }else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
