package com.fudanuniversity.cms.inner.dao;

import com.fudanuniversity.cms.inner.entity.CmsUser;
import com.fudanuniversity.cms.inner.query.CmsUserQuery;

import java.util.List;

/**
 * CmsUserDao
 * <p>
 * Created by tidu at 2021-05-01
 */
public interface CmsUserDao {

    /**
     * 保存/更新处理
     */
    int replace(CmsUser cmsUser);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsUser> cmsUserList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsUser cmsUser);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 查询数据
     */
    List<CmsUser> selectListByParam(CmsUserQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsUserQuery query);

}
