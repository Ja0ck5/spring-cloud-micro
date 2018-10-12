package com.ja0ck5.cloud.model.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by Jack on 2017/12/25.
 */
public class JSONUtil {

    private JSONUtil() {
    }

    public static JSONObject isJson(String value) {
        try {
            return JSON.parseObject(value);
        } catch (Exception e) {
            return null;
        }
    }

}
