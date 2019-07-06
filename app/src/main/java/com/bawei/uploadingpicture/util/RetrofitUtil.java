package com.bawei.uploadingpicture.util;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Copyright (C), 2015-2019, 八维集团
 * Author: Machenike
 * Date: 2019/7/6 11:05,周文博
 * Description:
 */
public class RetrofitUtil {
    private static  RetrofitUtil instance;
    private Retrofit retrofit;
    public  RetrofitUtil(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient =new  OkHttpClient.Builder()
                .writeTimeout(10,TimeUnit.MINUTES)
                .readTimeout(10,TimeUnit.MINUTES)
                .connectTimeout(10,TimeUnit.MINUTES)
                .build();
        retrofit = new  Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://172.17.8.100/small/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }
    public  static RetrofitUtil getInstance(){
        if (instance == null){
            instance = new RetrofitUtil();
        }
        return  instance;
    }
    public <T> T create(final Class<T> service) {
        return  retrofit.create(service);
    }
}
