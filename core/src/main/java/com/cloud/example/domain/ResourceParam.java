package com.cloud.example.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: cuking_work
 * @date: 2019-07-17
 */
@Data
public class ResourceParam extends BaseParam implements Serializable {

    /**
     * 资源ID
     */
    private String id;

    /**
     * 资源名称
     */
    private String resourceName;

    private String logoUrl;

    /**
     * 顶级类目ID
     */
    private Integer topClassId;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 三级类目ID集合
     */
    private List<Integer> categoryIds;

    private List<Integer> resourceStatuss = new ArrayList<>(0);


}
