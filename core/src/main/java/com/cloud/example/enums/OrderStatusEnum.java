package com.cloud.example.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author ; lidongdong
 * @Description 订单状态
 * @Date 2019/4/2
 */
public enum OrderStatusEnum {

    DEFAULT(0, "默认", "默认"),
    WAIT_PAY(1, "待支付", "待付款"),
    PAYING(2, "支付中", "支付中"),
    PAYED(3, "已支付", "已支付"),
    WAITING_SEND(4, "待发货", "待发货"),
    WAITING_RECEIVE(5, "待收货", "待收货"),
    FINISHED(6, "交易完成", "交易完成"),
    ;

    private int status;

    private String des;

    private String showName;

    OrderStatusEnum(int status, String des, String showName) {
        this.status = status;
        this.des = des;
        this.showName = showName;
    }

    public int getStatus() {
        return status;
    }

    public String getDes() {
        return des;
    }

    public String getShowName() {
        return showName;
    }

    private static Map<Integer, OrderStatusEnum> enumMap = new HashMap<>();

    static {
        Stream.of(OrderStatusEnum.values()).parallel().forEach(obj -> {
            enumMap.put(obj.getStatus(), obj);
        });
    }

    public static OrderStatusEnum getEnumByCode(Integer code) {
        return code == null ? DEFAULT : enumMap.get(code);
    }
}
