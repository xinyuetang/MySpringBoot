package com.fudanuniversity.cms.repository.mapper;

import com.fudanuniversity.cms.repository.entity.CmsPlan;
import com.fudanuniversity.cms.repository.query.CmsPlanQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * CmsPlanMapper接口
 * <p>
 * Created by tidu at 2021-05-02
 */
@Mapper
public interface CmsPlanMapper {

    /**
     * 保存处理
     */
    int insert(CmsPlan cmsPlan);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsPlan> cmsPlanList);

    /**
     * 删除处理
     */
    int deleteById(Long id);

    /**
     * 更新处理
     */
    int updateById(CmsPlan cmsPlan);

    /**
     * 根据条件查询信息列表
     */
    List<CmsPlan> selectListByParam(CmsPlanQuery query);

    /**
     * 根据条件查询信息总数目
     */
    Long selectCountByParam(CmsPlanQuery query);

}
