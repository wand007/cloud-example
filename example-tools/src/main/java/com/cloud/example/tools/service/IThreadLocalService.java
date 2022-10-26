package com.cloud.example.tools.service;

import com.cloud.example.tools.vo.BatchParam;

/**
 * @author 咚咚锵
 * @date 2021/4/3 18:44
 * @description ThreadLocal测试
 */
public interface IThreadLocalService {

    /**
     * 设置参数
     *
     * @param sum
     */
    void initParam(int sum);

    /**
     * 计算
     */
    void compute();

    /**
     * 汇总计算结果
     *
     * @return
     */
    BatchParam summaryResults();
}
