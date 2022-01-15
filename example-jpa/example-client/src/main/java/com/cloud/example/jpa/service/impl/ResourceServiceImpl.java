package com.cloud.example.jpa.service.impl;

import com.cloud.example.jpa.jpa.dao.ResourceRepository;
import com.cloud.example.jpa.jpa.domain.ResourceDAO;
import com.cloud.example.jpa.service.ResourceService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author 咚咚锵
 * @date 2022/1/15 下午1:56
 * @description TODO
 */
@Slf4j
@Setter
@Service
public class ResourceServiceImpl implements ResourceService {
    @Resource
    ResourceRepository resourceRepository;


    @Override
    public Optional<ResourceDAO> findById(String resourceId) {
        return resourceRepository.findById(resourceId);
    }
}
