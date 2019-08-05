package com.cloud.example.domain;

import org.springframework.data.domain.Sort;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019-08-05
 */
public class BaseParam {
    private Integer pageNum = 0;
    private Integer pageSize = 10;
    public Sort.Direction sortType = Sort.Direction.DESC;
    public String sortColumn = "id";
}
