package com.example.consumer_client.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.consumer_client.R;
import com.example.consumer_client.review.ReviewActivity;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

interface OrderDetailMdService{
    @POST("orderDetailMd")
    Call<ResponseBody> orderDetailMd(@Body JsonObject body);
}

public class OrderDetailActivity extends AppCompatActivity {
    String TAG = OrderDetailActivity.class.getSimpleName();

    JsonObject body;
    OrderDetailMdService service;
    JsonParser jsonParser;
    Context mContext;
    String store_loc, store_my, store_name, md_name, md_qty, md_price, order_id, pu_date, store_lat, store_long, md_status, md_fin_price;
//    int md_fin_price;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.baseurl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonParser = new JsonParser();
        service = retrofit.create(OrderDetailMdService.class);

        mContext = this;

        ImageView MdImgThumbnail = (ImageView) findViewById(R.id.ClientOrderProdIMG);
        TextView MdName = (TextView) findViewById(R.id.JP_ProdName);
        TextView OrderCount = (TextView) findViewById(R.id.ClientOrderCount);
        TextView OrderPrice = (TextView) findViewById(R.id.ClientOrderPrice);
        TextView StoreName = (TextView) findViewById(R.id.OrderStoreName);
        TextView StoreAddr = (TextView) findViewById(R.id.OrderStoreAddr);
        TextView PuDate = (TextView) findViewById(R.id.OrderPickUpDate);
        TextView ProdStatus = (TextView) findViewById(R.id.ProdStatus);
        Button reviewBtn = findViewById(R.id.ReviewBtn);

        Intent intent = getIntent(); //intent 값 받기
        store_loc=intent.getStringExtra("store_loc");
        store_my = intent.getStringExtra("store_my");
        store_name = intent.getStringExtra("store_name");
        md_name = intent.getStringExtra("md_name");
        md_qty = intent.getStringExtra("md_qty");
        md_price = intent.getStringExtra("md_price");
        order_id = intent.getStringExtra("order_id");
        store_lat = intent.getStringExtra("store_lat");
        store_long = intent.getStringExtra("store_long");
        md_status = intent.getStringExtra("md_status");

        body = new JsonObject();
        body.addProperty("order_id", order_id);

        Call<ResponseBody> call = service.orderDetailMd(body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JsonObject res =  (JsonObject) jsonParser.parse(response.body().string());
                        pu_date = res.get("pu_date").getAsString();

                        //지도
                        MapView mapView = new MapView(mContext);
                        // 중심점 변경
                        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(store_lat), Double.parseDouble(store_long)), true);

                        // 줌 레벨 변경
                        mapView.setZoomLevel(1, true);
                        // 줌 인
                        mapView.zoomIn(true);
                        // 줌 아웃
                        mapView.zoomOut(true);

                        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.farm_map_view);
                        mapViewContainer.addView(mapView);

                        //농가위치 마커 아이콘 띄우기
                        MapPoint f_MarkPoint = MapPoint.mapPointWithGeoCoord(Double.parseDouble(store_lat), Double.parseDouble(store_long));  //마커찍기

                        MapPOIItem farm_marker=new MapPOIItem();
                        farm_marker.setItemName(store_name); //클릭했을때 농가이름 나오기
                        farm_marker.setTag(0);
                        farm_marker.setMapPoint(f_MarkPoint);   //좌표입력받아 현위치로 출력

                        //  (클릭 전)기본으로 제공하는 BluePin 마커 모양의 색.
                        farm_marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                        // (클릭 후) 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                        farm_marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
                        // 지도화면 위에 추가되는 아이콘을 추가하기 위한 호출(말풍선 모양)
                        mapView.addPOIItem(farm_marker);

                        //나중에 농가위치 마커 커스텀 이미지로 바꾸기
                        //farm_marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
                        //farm_marker.setCustomImageResourceId(R.drawable.homeshape);

                        //img 아직 안함
                        MdName.setText(md_name);
                        OrderCount.setText(md_qty);
                        md_fin_price = String.valueOf(Integer.parseInt(md_price) * Integer.parseInt(md_qty));
                        OrderPrice.setText(md_fin_price);
                        StoreName.setText(store_name);
                        StoreAddr.setText(store_loc);
                        PuDate.setText(pu_date);
                        ProdStatus.setText(md_status);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        Log.d(TAG, "Fail " + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: e " + t.getMessage());
            }
        });

        reviewBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    Intent intent = new Intent(OrderDetailActivity.this, ReviewActivity.class);
                    intent.putExtra("md_name", md_name);
                    intent.putExtra("md_qty", md_qty);
                    intent.putExtra("md_fin_price", md_fin_price);
                    startActivity(intent);
                }
            });
    }
}
