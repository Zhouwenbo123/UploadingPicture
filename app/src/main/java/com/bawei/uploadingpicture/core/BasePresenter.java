package com.bawei.uploadingpicture.core;


import com.bawei.uploadingpicture.bean.Data;
import com.bawei.uploadingpicture.model.IRequest;
import com.bawei.uploadingpicture.util.RetrofitUtil;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * Copyright (C), 2015-2019, 八维集团
 * Author: Machenike
 * Date: 2019/5/14 17:17,周文博
 * Description:
 */
public abstract class BasePresenter {
    private DataCall dataCall;
    private  boolean isRunning;//是否正在进行

    public  BasePresenter(DataCall dataCall){
        this.dataCall = dataCall;
    }
    public  void requestData(final Object...args){
        if (isRunning){
            return;
        }
        isRunning = true;
        IRequest iRequest = RetrofitUtil.getInstance().create(IRequest.class);
        getModel(iRequest,args)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn(new Function<Throwable,Data>() {
                    @Override
                    public Data apply(Throwable o){
                        o.printStackTrace();//打印 消息
                        return handleException(o);
                    }
                })
                .subscribe(new Consumer<Data>() {
                    @Override
                    public void accept(Data data) throws Exception {
                        isRunning = false;
                        if (data.status.equals("0000")){
                            dataCall.success(data,args);
                        }else{
                            dataCall.fail(data,args);
                        }
                    }
                });
    }
    //处理异常进行凤爪昂
    public  static  Data handleException(Throwable e){
        e.printStackTrace();

        Data ex;
        if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException
                ){
            //解析错误
            ex = new Data("1001","解析异常"+e.getMessage());
            return  ex;
        }else if (e instanceof ConnectException || e instanceof UnknownHostException){
            //网络错误
            ex = new Data("1002","网络异常"+e.getMessage());
            return  ex;
        }else {
            //未知错误
            ex = new Data("1003",e.getMessage());
            return  ex;
        }
    }

    protected abstract Observable getModel(IRequest iRequest, Object... args);
}
