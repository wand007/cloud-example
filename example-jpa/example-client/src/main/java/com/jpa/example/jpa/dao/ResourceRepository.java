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

    /**
     * 多表分页查询
     *
     * @param param
     * @param pageable
     * @return
     */
    @Query(value = "SELECT new com.jpa.example.jpa.domain.ResourceDAO(r.id,r.resourceName, r.resourceLogo,r.categoryId,r.createDate)" +
            " FROM ResourceDAO r, ResourceDetailDAO rd WHERE r.id = rd.id " +
            "and ( r.id=:#{#param.getId()} or :#{#param.getId()} is null) " +
            "and ( rd.topClassId=:#{#param.getTopClassId()} or :#{#param.getTopClassId()} is null) " +
            "and ( r.categoryId in :#{#param.getCategoryIds()} or :#{#param.getLogoUrl()} is null) " +
            "and ( r.resourceStatus in :#{#param.getResourceStatuss()} or :#{#param.getResourceStatuss().size()} = 0) " +
            "and ( r.createDate >=:#{#param.getStartTime()} or :#{#param.getStartTime} is null) " +
            "and ( r.createDate <=:#{#param.getEndTime()} or :#{#param.getEndTime} is null) " +
            "and ( r.resourceName like CONCAT('%',:#{#param.getResourceName()},'%')  or :#{#param.getResourceName()} is null) ")
    Page<ResourceDAO> findPage(@Param("param") ResourceParam param, Pageable pageable);
}
