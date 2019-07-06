package com.bawei.uploadingpicture.bean;


public class Data<T> {
    public String status = "-1";
    public String message = "请求失败";
    public T result;

     public  Data(String status,String message){
        this.status =status;
        this.message = message;
    }
}
