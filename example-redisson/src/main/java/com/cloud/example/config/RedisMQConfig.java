package com.cloud.example.config;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-08-29
 */
public class RedisMQConfig {

    ///////////////////////////////redis消息订阅发布\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    /**
     * 消息推送新增京东资源
     */
    public static final String ADD_CHANNEL_KEY = "ADD_CHANNEL_KEY";
    /**
     * 消息推送更新协议价格
     */
    public static final String UPDATE_PRICE_CHANNEL_KEY = "UPDATE_PRICE_CHANNEL_KEY";
    /**
     * 消息推送更新资源详情属性
     */
    public static final String UPDATE_DETAIL_CHANNEL_KEY = "UPDATE_DETAIL_CHANNEL_KEY";
}
