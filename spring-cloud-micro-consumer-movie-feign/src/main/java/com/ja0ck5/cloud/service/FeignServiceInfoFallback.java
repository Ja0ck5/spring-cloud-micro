package com.ja0ck5.cloud.service;

/**
 * Created by Jack on 2017/10/16.
 */
public class FeignServiceInfoFallback implements ServiceInfoFeignClient{
    @Override
    public String getAppInfo(String serviceName) {
        return "an error result";
    }
}
