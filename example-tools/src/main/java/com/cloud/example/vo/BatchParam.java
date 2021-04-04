package com.cloud.example.vo;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ; lidongdong
 * @Description 批次
 * @Date 2019-09-10
 */
@Data
public class BatchParam implements Serializable {


    private String id;

    @NotBlank(message = "批次名称")
    private String batchName;

    @NotNull(message = "数量")
    private Integer number;

    @NotNull(message = "批次总价不能为空")
    @DecimalMin(value = "0", message = "批次总价不能小于0")
    private BigDecimal amountTotal;

    @NotNull(message = "批次详情不能为空")
    @Valid
    private List<BatchDetailsParam> batchDetailsParams;


}
