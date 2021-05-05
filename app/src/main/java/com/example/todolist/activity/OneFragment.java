package com.example.todolist.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.todolist.R;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class OneFragment extends Fragment {

    private Banner banner;
    private List<Integer> image=new ArrayList<>();
    private List<String> title=new ArrayList<>();
    private void initData() {

        image.add(R.drawable.wk_bill);
        image.add(R.drawable.wk_bill1);
        image.add(R.drawable.wk_bill2);
        image.add(R.drawable.wk_bill3);

        title.add("任务清单");
        title.add("把握现在");
        title.add("青春少年");
        title.add("规划未来");


    }
    private void initView() {

        banner.setIndicatorGravity(BannerConfig.CENTER);

        banner.setImageLoader(new MyImageLoader());

        banner.setImages(image);

        banner.setBannerAnimation(Transformer.Default);

        banner.isAutoPlay(true);

        banner.setBannerTitles(title);

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);

        banner.setDelayTime(3000);

        banner.setOnBannerListener(this::OnBannerClick);

        banner.start();
    }


    public void OnBannerClick(int position) {
        Toast.makeText(getActivity(), "你点了第" + (position + 1) + "张轮播图", Toast.LENGTH_SHORT).show();
    }


    private class MyImageLoader extends ImageLoader {

        public void displayImage(Context context, Object path, ImageView imageView) {

            Glide.with(context).load(path).into(imageView);

        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.one,container,false);
        banner = view.findViewById(R.id.banner);
        initData();
        initView();
        Button button=view.findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"跳转到任务清单", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(), com.example.todolist.activity.MainActivity.class);
                getActivity().startActivity(intent);//当然也可以写成getContext()
            }
        });

        Button button1=view.findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener() {      //  点击跳转时
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"跳转到我的日程", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }




}
