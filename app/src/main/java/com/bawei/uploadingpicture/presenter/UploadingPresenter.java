package com.bawei.uploadingpicture.presenter;

import com.bawei.uploadingpicture.core.BasePresenter;
import com.bawei.uploadingpicture.core.DataCall;
import com.bawei.uploadingpicture.model.IRequest;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

/**
 * Copyright (C), 2015-2019, 八维集团
 * Author: Machenike
 * Date: 2019/7/6 11:32,周文博
 * Description:
 */
public class UploadingPresenter extends BasePresenter {
    public UploadingPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable getModel(IRequest iRequest, Object... args) {
        return iRequest.modifyHeadPic((String)args[0],(String) args[1],(MultipartBody.Part)args[2]);
    }
}
