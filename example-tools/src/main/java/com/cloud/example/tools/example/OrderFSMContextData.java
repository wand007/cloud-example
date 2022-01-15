package com.cloud.example.tools.example;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-05-08
 */
public class OrderFSMContextData {
    private boolean isPayed;//是否已经支付成功
    private boolean isDistribute;//是否为分销
    private boolean isSelfSupport;//是否自营
    private boolean isInquiry;//是否询单

    public OrderFSMContextData(boolean isPayed, boolean isDistribute, boolean isSelfSupport, boolean isInquiry) {
        this.isPayed = isPayed;
        this.isDistribute = isDistribute;
        this.isSelfSupport = isSelfSupport;
        this.isInquiry = isInquiry;
    }

    public boolean isPayed() {
        return isPayed;
    }

    public boolean isDistribute() {
        return isDistribute;
    }

    public boolean isSelfSupport() {
        return isSelfSupport;
    }

    public boolean isInquiry() {
        return isInquiry;
    }
}
