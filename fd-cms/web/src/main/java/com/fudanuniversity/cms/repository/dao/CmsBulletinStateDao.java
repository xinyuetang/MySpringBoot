package com.fudanuniversity.cms.repository.dao;

import com.fudanuniversity.cms.repository.entity.CmsBulletinState;
import com.fudanuniversity.cms.repository.query.CmsBulletinStateQuery;

import java.util.List;

/**
 * CmsBulletinStateDao
 * <p>
 * Created by tidu at 2021-05-02
 */
public interface CmsBulletinStateDao {

    /**
     * 保存处理
     */
    int insert(CmsBulletinState cmsBulletinState);

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

}
