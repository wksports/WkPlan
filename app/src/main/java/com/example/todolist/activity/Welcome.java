package com.example.todolist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.todolist.R;

public class Welcome extends AppCompatActivity {
    ImageView imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        imageview=(ImageView) findViewById(R.id.welcomefriend);
       //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//隐藏顶部标题栏
        //getSupportActionBar().hide();
        handler.sendEmptyMessageDelayed(0, 3000);
        loadimage(imageview);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //实现页面的跳转
            Intent intent=new Intent(Welcome.this, Main.class);
            startActivity(intent);
            finish();
            super.handleMessage(msg);
        }
    };
    public void loadimage(View view){
        String url="http://p1.pstatp.com/large/166200019850062839d3";
        int res= R.drawable.zwt;
        Glide.with(this).
                load(url).placeholder(res).
                error(R.drawable.cw).
                diskCacheStrategy(DiskCacheStrategy.NONE).
                into(imageview);
    }

}
