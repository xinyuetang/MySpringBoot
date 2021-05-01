package com.fudanuniversity.cms.inner.dao;

import com.fudanuniversity.cms.inner.entity.CmsUserAccount;
import com.fudanuniversity.cms.inner.query.CmsUserAccountQuery;

import java.util.List;

/**
 * CmsUserAccountDao
 * <p>
 * Created by tidu at 2021-05-01
 */
public interface CmsUserAccountDao {

    /**
     * 保存处理
     */
    int insert(CmsUserAccount cmsUserAccount);

    /**
     * 批量upsert
     */
    int bulkUpsert(List<CmsUserAccount> cmsUserAccountList);

    /**
     * 根据id更新处理
     */
    int updateById(CmsUserAccount cmsUserAccount);

    /**
     * 根据id删除
     */
    int deleteById(Long id);

    /**
     * 查询数据
     */
    List<CmsUserAccount> selectListByParam(CmsUserAccountQuery query);

    /**
     * 查询数量
     */
    Long selectCountByParam(CmsUserAccountQuery query);

}
