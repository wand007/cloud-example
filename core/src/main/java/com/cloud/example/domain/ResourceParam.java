package com.cloud.example.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: cuking_work
 * @date: 2019-07-17
 */
@Data
public class ResourceParam extends BaseParam implements Serializable {


    private String id;

    /**
     * 资源ID
     */
    private String resourceId;
    /**
     * 资源名称
     */
    private String resourceName;

    private Integer channelType;

    private BigDecimal price;

    private BigDecimal score;

    private String logoUrl;

    private String resourceDesc;

    private String resourcePicUrl;

    /**
     * 顶级类目ID
     */
    private Integer topClassId;
    /**
     * 二级类目ID
     */
    private Integer secondClassId;
    /**
     * 三级类目ID
     */
    private Integer categoryId;
    /**
     * 三级类目ID集合
     */
    private List<Integer> categoryIds;

    private List<Integer> resourceStatuss = new ArrayList<>(0);


}
