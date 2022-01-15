package com.cloud.example.tools.example;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-05-08
 */

import com.cloud.example.base.BusinessCode;
import com.cloud.example.base.BusinessException;

/**
 * 订单简明状态机
 *
 * @author <a href="dongjianxing@aliyun.com">jeff</a>
 * @version 2017/1/22 13:37
 */
public class OrderFSM {
    private SpaceOrderFSMState fsmSpaceState = SpaceOrderFSMState.NOPAY_NOORDER;

    private OrderFSMContextData contextData;

    public static OrderFSM init(OrderFSMContextData contextData) {
        return new OrderFSM(contextData);
    }

    public OrderFSM(OrderFSMContextData contextData) {
        this.contextData = contextData;
    }

    public OrderFSM fire(FSMEvent event) {
        OrderFSM fsm = null;

        switch (event) {
            case ORDER_CREATE:
                fsm = orderCreate(contextData);
                break;
            default:
                throw new BusinessException("订单FSM不支持的事件类型");
        }
        return fsm;
    }

    public SpaceOrderFSMState getFsmSpaceState() {
        return fsmSpaceState;
    }

    public enum SpaceOrderFSMState {

        NOPAY_NOORDER(1020, "待支付待下单"),
        NOPAY_ORDERFAILED(1023, "待支付下单失败"),
        NOPAY_NOCONFIRM(1010, "待支付待确认"),
        PAYED_NOCONFIRM(1011, "支付待确认"),
        PAYED_NOORDER(1012, "支付确认"),
        ;
        private Integer method;
        private String des;

        SpaceOrderFSMState(Integer method, String des) {
            this.method = method;
            this.des = des;
        }

        public Integer getMethod() {
            return method;
        }

        public String getDes() {
            return des;
        }
    }

    public enum FSMEvent {

        ORDER_CREATE, //订单创建
        BUSINOTIFY_ORDERFAILED,//业务结果通知，下单失败
    }

    //以订单创建为例
    private OrderFSM orderCreate(OrderFSMContextData contextData) {
        if (fsmSpaceState != SpaceOrderFSMState.NOPAY_NOORDER) {
            throw new BusinessException(BusinessCode.ALERT_MESSAGE.getCode(), "FSM:当前状态不允许 ORDER_CREATE 事件");
        }
        //分销
        if (contextData.isDistribute()) {
            if (contextData.isPayed()) {
                this.fsmSpaceState = contextData.isSelfSupport() ? SpaceOrderFSMState.PAYED_NOCONFIRM : SpaceOrderFSMState.PAYED_NOORDER;
            } else {
                this.fsmSpaceState = contextData.isSelfSupport() ? SpaceOrderFSMState.NOPAY_NOCONFIRM : SpaceOrderFSMState.NOPAY_NOORDER;
            }
        } else {
            //非分销
            this.fsmSpaceState = contextData.isSelfSupport() ? SpaceOrderFSMState.NOPAY_NOCONFIRM : SpaceOrderFSMState.NOPAY_NOORDER;
        }
        return this;
    }
}
