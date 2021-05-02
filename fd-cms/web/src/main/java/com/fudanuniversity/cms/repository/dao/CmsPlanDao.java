package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsPlan;
import com.fudanuniversity.cms.repository.query.CmsPlanQuery;

import java.util.List;

/**
 * CmsPlanDao
 * <p>
 * Created by tidu at 2021-05-02
 */
public interface CmsPlanDao {

    /**
     * 保存处理
     */
    int insert(CmsPlan cmsPlan);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsPlan> cmsPlanList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsPlan cmsPlan);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 查询数据
     */
    List<CmsPlan> selectListByParam(CmsPlanQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsPlanQuery query);

}
