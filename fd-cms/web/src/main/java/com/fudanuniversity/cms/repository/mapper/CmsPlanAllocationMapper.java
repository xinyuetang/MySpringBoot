package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsPlanAllocation;
import com.fudanuniversity.cms.repository.query.CmsPlanAllocationQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsPlanAllocationMapper接口
 * <p>
 * Created by tidu at 2021-05-02
 */
@Mapper
public interface CmsPlanAllocationMapper {

    /**
     * 保存处理
     */
    int insert(CmsPlanAllocation cmsPlanAllocation);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsPlanAllocation> cmsPlanAllocationList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     * 更新处理
     */
    int updateById(CmsPlanAllocation cmsPlanAllocation);

    /**
     * 根据条件查询信息列表
     */
    List<CmsPlanAllocation> selectListByParam(CmsPlanAllocationQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsPlanAllocationQuery query);

}
