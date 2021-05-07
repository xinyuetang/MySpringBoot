package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsStudyPlanItem;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanItemQuery;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanInfo;

import java.util.List;

/**
 * CmsStudyPlanAllocationDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:50:00
 */
public interface CmsStudyPlanItemDao {

    /**
     * 保存处理
     */
    int insert(CmsStudyPlanItem cmsStudyPlanItem);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsStudyPlanItem> cmsStudyPlanItemList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsStudyPlanItem cmsStudyPlanItem);

    /**
     * 根据id列表软删除
     */
    int markAsDeletedByIds(List<Long> idList);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 查询数据
     */
    List<CmsStudyPlanItem> selectListByParam(CmsStudyPlanItemQuery query);

    /**
     * 查询数据
     */
    List<CmsStudyPlanInfo> selectInfoByParam(CmsStudyPlanItemQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsStudyPlanItemQuery query);
}
