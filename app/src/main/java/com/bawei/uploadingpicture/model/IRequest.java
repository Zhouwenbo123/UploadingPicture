package com.bawei.uploadingpicture.model;

import com.bawei.uploadingpicture.bean.Uploging;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Copyright (C), 2015-2019, 八维集团
 * Author: Machenike
 * Date: 2019/7/6 11:33,周文博
 * Description:
 */
public interface IRequest {
    @Multipart
    @POST("user/verify/v1/modifyHeadPic")
    Observable<Uploging> modifyHeadPic(@Header("userId")String userId,
                                       @Header("sessionId")String sessionId,
                                       @Part MultipartBody.Part filepart
                                       );
}
