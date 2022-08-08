package com.example.consumer_client.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.consumer_client.R;
import com.example.consumer_client.farm.FarmActivity;
import com.example.consumer_client.shopping_info.ShoppingInfo2Activity;
import com.example.consumer_client.shopping_info.ShoppingInfoActivity;


public class MyPage extends Fragment {
    private View view;
    Activity mActivity;
    String userid;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        Intent intent = mActivity.getIntent(); //intent 값 받기
        userid=intent.getStringExtra("userid");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_my_page, container, false);

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my_page, container, false);

        TextView totalFarmTextView = (TextView) view.findViewById(R.id.MyPage_MS_ProdReview);
//        totalFarmTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), FarmActivity.class);
//                startActivity(intent);
//            }
//        });

        //나의 쇼핑정보
        LinearLayout shoppingInfo = (LinearLayout) view.findViewById(R.id.MyPage_MyShopping);
        shoppingInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShoppingInfoActivity.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
            }
        });
        //상세 주문 내역
        TextView orderlist = (TextView) view.findViewById(R.id.MyPage_MS_OrderDetail);
        orderlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShoppingInfoActivity.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
            }
        });
        //상품리뷰
        TextView reviewlist = (TextView) view.findViewById(R.id.MyPage_MS_ProdReview);
        reviewlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShoppingInfo2Activity.class);
                intent.putExtra("userid", userid);
                startActivity(intent);
            }
        });
        return view;
    }
}