package com.cloud.example.vo;

import com.cloud.example.domain.BaseParam;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-09-10
 */
@Data
public class WashCarControlParam extends BaseParam implements Serializable {
    private static final long serialVersionUID = 8727283729371182951L;
    /**
     * 资源ID
     */
    @NotBlank(message = "资源ID")
    private String cityWashCarId;

    /**
     * 小车 协议价(在公象网的协议价)
     */
    @NotNull(message = "小车 协议价不能为空")
    @DecimalMin(value = "0", message = "小车 协议价不能小于0")
    private BigDecimal protocolPriceSmallCar;

    /**
     * 大车 协议价(在公象网的协议价)
     */
    @NotNull(message = "大车 协议价不能为空")
    @DecimalMin(value = "0", message = "大车 协议价不能小于0")
    private BigDecimal protocolPriceBigCar;


}
