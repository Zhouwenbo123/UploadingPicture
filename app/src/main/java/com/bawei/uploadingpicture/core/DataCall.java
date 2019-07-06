package com.bawei.uploadingpicture.core;


import com.bawei.uploadingpicture.bean.Data;
import com.bawei.uploadingpicture.bean.Uploging;

/**
 * Copyright (C), 2015-2019, 八维集团
 * Author: Machenike
 * Date: 2019/5/14 17:15,周文博
 * Description:
 */
public interface DataCall<T> {
    void  success(Data data, Object... args);
    void  fail(Data data, Object... args);
}
