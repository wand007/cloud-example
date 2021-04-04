package com.cloud.example.vo;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 咚咚锵
 * @date 2021/4/3 19:49
 * @description 批次详情
 */
@Data
public class BatchDetailsParam {

    private String id;

    @NotBlank(message = "批次名称")
    private String credentialNo;

    @NotNull(message = "批次下发金额不能为空")
    @DecimalMin(value = "0", message = "批次下发金额不能小于0")
    private BigDecimal amount;
}
