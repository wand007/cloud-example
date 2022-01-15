package com.cloud.example.jpa.service;

import com.cloud.example.jpa.jpa.domain.ResourceDAO;

import java.util.Optional;

/**
 * @author 咚咚锵
 * @date 2022/1/15 下午1:56
 * @description TODO
 */
public interface ResourceService {
    Optional<ResourceDAO> findById(String resourceId);
}
