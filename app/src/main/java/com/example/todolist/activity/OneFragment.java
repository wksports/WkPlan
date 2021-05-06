package com.example.todolist.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.todolist.R;

import com.example.todolist.adapter.ListAdapter;
import com.example.todolist.bean.ListItem;
import com.example.todolist.db.ListItemDao;
import com.example.todolist.listener.OnBackPressListener;
import com.example.todolist.listener.OnClickListener;
import com.example.todolist.listener.OnNextListener;
import com.example.todolist.listener.OnTextChangeListener;
import com.example.todolist.utils.DateUtil;
import com.example.todolist.utils.LogUtil;
import com.example.todolist.utils.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OneFragment extends Fragment {
    private Context context;
    private View mainLayout;
    private TextView dateTextView;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private List<ListItem> dataList=new ArrayList<>();
    private Date date;
    private String time;
    private long preTime;
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
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
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
        mainLayout= LayoutInflater.from(context).inflate(R.layout.one,container,false);
        initParams();
        initViews();

        banner = mainLayout.findViewById(R.id.banner);
        initData();
        initView();
       // Button button=view.findViewById(R.id.button1);
      //  button.setOnClickListener(new View.OnClickListener() {
            //@Override
           // public void onClick(View v) {
             //   Toast.makeText(getContext(),"跳转到任务清单", Toast.LENGTH_SHORT).show();
             //   Intent intent=new Intent(getContext(), com.example.todolist.activity.MainActivity.class);
            //    getActivity().startActivity(intent);//当然也可以写成getContext()
         //   }
     //   });

      //  Button button1=view.findViewById(R.id.button2);
      //  button1.setOnClickListener(new View.OnClickListener() {      //  点击跳转时
        //    @Override
        //    public void onClick(View v) {
        //        Toast.makeText(getContext(),"跳转到我的日程", Toast.LENGTH_SHORT).show();
          //  }
      //  });


        return mainLayout;
    }
    private void initParams(){
        Calendar calendar=Calendar.getInstance();
        date=calendar.getTime();
    }
    private void initViews(){
        dateTextView=mainLayout.findViewById(R.id.list_date);
        recyclerView=mainLayout.findViewById(R.id.list_recycler_view);

        initDate();
        initRecyclerView();
    }

    private void initDate(){
        time= DateUtil.getYearMonthDayNumberic(date);
        dateTextView.setText(DateUtil.getYearMonthDay(date));
    }
    private void initRecyclerView(){
        layoutManager=new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        initDataList();
        adapter=new ListAdapter(dataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        layoutManager.scrollToPosition(dataList.size()-1);
        adapter.setOnFinishListener(new OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                markStatus(position, ListItem.FINISH);
                addEmptyItem(position);
            }
        });
        adapter.setOnUnFinishListener(new OnClickListener() {
            @Override
            public void onClick(View view, int position) {
                markStatus(position, ListItem.UNFINISH);
                addEmptyItem(position);
            }
        });
        adapter.setOnTextChangeListener(new OnTextChangeListener() {
            @Override
            public void onTextChange(Editable s, int pos) {
                ListAdapter.ViewHolder viewHolder=(ListAdapter.ViewHolder)recyclerView.findViewHolderForAdapterPosition(pos);
                if(dataList.get(pos).getStatus()==ListItem.NO_RECORD||
                        dataList.get(pos).getStatus()==ListItem.NO_CONTENT){
                    if(!TextUtils.isEmpty(s.toString())){
                        if(viewHolder!=null){
                            viewHolder.finish.setVisibility(View.VISIBLE);
                            viewHolder.unFinish.setVisibility(View.VISIBLE);
                        }
                        dataList.get(pos).setStatus(ListItem.NO_RECORD);
                        dataList.get(pos).setContent(s.toString());
                        ListItemDao.updateItem(dataList.get(pos).getId(),ListItem.NO_RECORD,s.toString());
                    }else{
                        if(viewHolder!=null){
                            viewHolder.finish.setVisibility(View.INVISIBLE);
                            viewHolder.unFinish.setVisibility(View.INVISIBLE);
                        }
                        dataList.get(pos).setStatus(ListItem.NO_CONTENT);
                        dataList.get(pos).setContent("");
                        ListItemDao.updateItem(dataList.get(pos).getId(),ListItem.NO_CONTENT,"");
                    }
                }
            }
        });
        adapter.setOnNextListener(new OnNextListener() {
            @Override
            public void onNext(int pos) {
                if(isLastItem(pos)){
                    if (!TextUtils.isEmpty(dataList.get(pos).getContent())){
                        addEmptyItem(pos);
                        adapter.setNewItem(true);
                    }
                }else{
                    int firstVisibleItemPos=((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                    int nextPos=getNextFocusPos(pos);
                    if(nextPos!=pos){
                        String content=dataList.get(nextPos).getContent();
                        adapter.setNewItem(false);
                        View itemView=recyclerView.getChildAt(nextPos-firstVisibleItemPos);
                        ListAdapter.ViewHolder viewHolder=(ListAdapter.ViewHolder)recyclerView.getChildViewHolder(itemView);
                        layoutManager.scrollToPositionWithOffset(nextPos,50);
                        viewHolder.content_edit.requestFocus();
                        viewHolder.content_edit.setSelection(content.length());
                    }
                }
            }
        });
        adapter.setOnBackPressListener(new OnBackPressListener() {
            @Override
            public void onBackPress(EditText editText, int pos) {
                if(pos!=dataList.size()-1){
                    int startSelection=editText.getSelectionStart();
                    if(startSelection==0){
                        long currentTime=System.currentTimeMillis();
                        if(currentTime-preTime>2000){
                            ToastUtil.showToast("再按一次删除当前项");
                            preTime=currentTime;
                        }else{
                            if(pos-1>=0){
                                mergeText(pos);
                            }
                            LogUtil.e("pos="+pos);
                            ListItemDao.deleteItem(dataList.get(pos).getId());
                            dataList.remove(pos);
                            adapter.notifyItemRemoved(pos);
                        }
                    }
                }
            }
        });
    }
    private void initDataList(){
        dataList= ListItemDao.queryAllItems(time);
        LogUtil.e("dataList.size():"+dataList.size());
        if(dataList.size()==0){
            ListItem item=createEmptyItem();
            dataList.add(item);
        }
    }
    private ListItem createEmptyItem(){
        ListItem item=new ListItem("",ListItem.NO_CONTENT,time);
        long itemId=ListItemDao.insertListItem(item);
        item.setId(itemId);
        return item;
    }

    /**
     * 判断当前是不是最后一项
     * @param pos
     */
    private boolean isLastItem(int pos){
        return pos==dataList.size()-1;
    }
    /**
     * 如果当前是最后一项，就增加一个item
     * @param pos
     */
    private void addEmptyItem(int pos){
        if(isLastItem(pos)){
            ListItem listItem=createEmptyItem();
            dataList.add(listItem);
            adapter.notifyItemInserted(dataList.size()-1);
            layoutManager.scrollToPosition(dataList.size()-1);
        }
    }

    /**
     * 标记当前项的状态，完成/未完成
     * @param pos
     * @param status
     */
    private void markStatus(int pos, int status){
        dataList.get(pos).setStatus(status);
        adapter.notifyItemChanged(pos);
        ListItemDao.updateItem(dataList.get(pos).getId(),status);
    }

    /**
     * 获取当前位置开始向下数，下一个可以focus的editText的位置
     * @param pos adapterPosition
     * @return 返回的是在datalist中的pos
     */
    private int getNextFocusPos(int pos){
        int firstPos=((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        int childCount=recyclerView.getChildCount();
        LogUtil.e("firstVisibleItem:"+firstPos+"\tchildCount:"+childCount);
        int returnPos=pos;
        for(int i=pos-firstPos+1;i<childCount;i++){
            if(dataList.get(i+firstPos).getStatus()==ListItem.NO_RECORD||
                    dataList.get(i+firstPos).getStatus()==ListItem.NO_CONTENT){
                returnPos=i+firstPos;
                break;
            }
        }
        return returnPos;
    }

    /**
     * 合并当前项和上一项的文本内容
     * @param pos
     */
    private void mergeText(int pos){
        String curContent=dataList.get(pos).getContent();
        int preStatus=dataList.get(pos-1).getStatus();
        String preContent=dataList.get(pos-1).getContent();
        if(preStatus== ListItem.NO_CONTENT||
                preStatus== ListItem.NO_RECORD){
            String content=preContent+curContent;
            dataList.get(pos-1).setContent(content);
            dataList.get(pos-1).setStatus(ListItem.NO_RECORD);
            adapter.notifyItemChanged(pos-1);
            ListItemDao.updateItem(dataList.get(pos-1).getId(),ListItem.NO_RECORD,content);
        }
    }



}
