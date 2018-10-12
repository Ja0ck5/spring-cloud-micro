package com.ja0ck5.cloud.service.i;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ja0ck5.cloud.model.BaseView;

/**
 * Created by Jack on 2017/12/25.
 */
public interface IJSONRender {

    default String renderData(Object obj) {
        return renderJSON(new BaseView(obj));
    }

    /**
     * 获得json渲染字符串
     */
    default String renderJSON(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
    }

}
