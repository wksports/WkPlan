package com.example.todolist.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.todolist.R;
import com.google.android.material.tabs.TabLayout;
import com.example.todolist.activity.OneFragment;
import com.example.todolist.activity.ThreeFragment;
import com.example.todolist.activity.TwoFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class Main extends AppCompatActivity {


    TabLayout tabLayout;
    ViewPager viewPager;
    List<Fragment> fgList = new ArrayList<>();
    List<String> list = new ArrayList<>();
    private ImageView blurImageView;
    private ImageView avatarImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewpager);
        fgList.add(new OneFragment());    //往我们的碎片list添加碎片
        fgList.add(new TwoFragment());
        fgList.add(new ThreeFragment());


        list.add("主页");            	//往我们tab底部文字的list里添加文字
        list.add("规划");
        list.add("我的");

        MyFragmentAdapter myFragmentAdapter = new MyFragmentAdapter(fgList, list, getSupportFragmentManager()); //实例化我们的适配器，并把我们的碎片list和文字list传进去，第三个参数固定这样写
        viewPager.setAdapter(myFragmentAdapter);    //为我们的ViewPger添加适配器
        tabLayout.setupWithViewPager(viewPager); 	//把我们的TabLayout与我们的ViewPager绑定起来

        tabLayout.getTabAt(0).setIcon(R.drawable.home_color);        //设置我们底部图片的是否被点击状态
        tabLayout.getTabAt(1).setIcon(R.drawable.issue_color);
        tabLayout.getTabAt(2).setIcon(R.drawable.platform_color);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {        //选中tab的处理逻辑
                if(tab.getPosition()==0)       tab.setIcon(R.drawable.home_color);    //如果当前点击第一个Tab,就把改tab的图片设置为点击状态的图片
                else if(tab.getPosition()==1)  tab.setIcon(R.drawable.issue_color);
                else if(tab.getPosition()==2)  tab.setIcon(R.drawable.platform_color);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {   //未选中的tab的处理逻辑
                if(tab.getPosition()==0)       tab.setIcon(R.drawable.home_color);  // 如果当前的tab未点击，就把改图片设置为未点击的图片
                else if(tab.getPosition()==1)  tab.setIcon(R.drawable.issue_color);
                else if(tab.getPosition()==2)  tab.setIcon(R.drawable.platform_color);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
