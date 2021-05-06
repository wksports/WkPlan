package com.example.todolist.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.todolist.activity.ItemView;
import com.example.todolist.R;


public class ThreeFragment extends Fragment {




    @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.three, container, false);
        //view.findViewById(R.id.).setOnClickListener(new View.OnClickListener() {
         //  @Override
         //   public void onClick(View arg0) {
                               // TODO Auto-generated method stub
            //                 Toast.makeText(getActivity(), "调用aboutThis()函数，然后启动一个新界面，【软件】", Toast.LENGTH_SHORT).show();
             //                  Intent intent = new Intent(getActivity().getApplicationContext(),AboutActivity.class);
             //                   startActivity(intent);
               //            }
     //  });


        return view;

        }


    }

