package com.jpa.example.jpa.dao;

import com.cloud.example.domain.ResourceParam;
import com.jpa.example.jpa.domain.ResourceDAO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author ; lidongdong
 * @Description
 * @Date 2019/4/2
 */
@Repository
public interface ResourceRepository extends JpaRepository<ResourceDAO, String>, JpaSpecificationExecutor<ResourceDAO> {

    @Query(value = "SELECT new com.cloud.example.domain.ResourceDAO(r.id, r.resourceId, " +
            "r.logoUrl, r.resourceName,r.categoryId,r.resourceStatus, r.channelType, r.protocolPrice, r.price," +
            "r.saleNum,r.score,r.createDate) FROM ChannelCardDO r, ChannelCardDetailDO rd WHERE r.id = rd.id " +
            "and ( r.id=:#{#param.getId()} or :#{#param.getId()} is null)  " +
            "and ( r.channelType=:#{#param.getChannelType()} or :#{#param.getChannelType()} is null)  " +
            "and ( r.resourceId=:#{#param.getResourceId()} or :#{#param.getResourceId()} is null)  " +
            "and ( rd.topClassId=:#{#param.getTopClassId()} or :#{#param.getTopClassId()} is null) " +
            "and ( r.categoryId in :#{#param.getCategoryIds()} or :#{#param.getLogoUrl()} is null) " +
            "and ( r.resourceStatus in :#{#param.getResourceStatuss()} or :#{#param.getResourceStatuss().size()} = 0) " +
            "and ( r.resourceName like CONCAT('%',:#{#param.getResourceName()},'%')  or :#{#param.getResourceName()} is null) ")
    Page<ResourceDAO> findPage(@Param("param") ResourceParam param, Pageable pageable);
}
