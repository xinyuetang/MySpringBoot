package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.commons.model.wrapper.TripleTuple;
import com.fudanuniversity.cms.repository.entity.CmsBulletinState;
import com.fudanuniversity.cms.repository.query.CmsBulletinStateQuery;

import java.util.List;

/**
 * CmsBulletinStateDao
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsBulletinStateDao {

    /**
     * 保存处理
     */
    int insert(CmsBulletinState cmsBulletinState);

    /**
     * 如果存在主键或唯一键冲突的记录，删除再添加
     */
    int replace(CmsBulletinState cmsBulletinState);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsBulletinState> cmsBulletinStateList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsBulletinState cmsBulletinState);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 查询数据
     */
    List<CmsBulletinState> selectListByParam(CmsBulletinStateQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsBulletinStateQuery query);

    /**
     * 查询未读通知Id列表
     */
    List<Long> selectUnreadBulletinIdList(CmsBulletinStateQuery query);

    /**
     * 查询总数/已读/未读数量
     */
    TripleTuple<Long, Long, Long> queryCmsBulletinReadCount(CmsBulletinStateQuery stateQuery);
}
