package com.example.consumer_client.user.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
   private final static String BASE_URL = "http://각자ip추가하기:3000/"; //나중에 각자 ip로 바꿔주세요
   private static Retrofit retrofit = null;

   private RetrofitClient(){
   }

   public static Retrofit getClient(){
      if(retrofit == null){
         retrofit = new Retrofit.Builder()
                 .baseUrl(BASE_URL)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
      }
      return retrofit;
   }

}
