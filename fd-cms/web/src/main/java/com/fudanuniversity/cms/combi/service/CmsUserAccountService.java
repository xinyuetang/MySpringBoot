package com.fudanuniversity.cms.combi.service;

import com.fudanuniversity.cms.combi.vo.CmsUserAccountVo;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.inner.entity.CmsUserAccount;
import com.fudanuniversity.cms.inner.query.CmsUserAccountQuery;

/**
 * CmsUserAccountService
 * <p>
 * Created by tidu at 2021-05-01
 */
public interface CmsUserAccountService {

    /**
     * 登录账户，出错则抛出错误
     */
    void loginAccount(CmsUserAccountVo userAccountVo);

    /**
     * 重置密码
     */
    void changeAccountPassword(CmsUserAccountVo userAccountVo);

    /**
     * 保存处理
     */
    void saveCmsUserAccount(CmsUserAccount cmsUserAccount);

    /**
     * 根据id更新处理
     */
    void updateCmsUserAccountById(CmsUserAccount cmsUserAccount);

    /**
     * 根据id删除处理
     */
    void deleteCmsUserAccountById(Long id);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsUserAccount> queryPagingResultByParam(CmsUserAccountQuery query);
}