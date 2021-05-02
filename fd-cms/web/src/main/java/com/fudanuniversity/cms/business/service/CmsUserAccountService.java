package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.user.CmsUserAccountResetPasswordVo;
import com.fudanuniversity.cms.business.vo.user.CmsUserAccountLoginVo;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.repository.entity.CmsUserAccount;
import com.fudanuniversity.cms.repository.query.CmsUserAccountQuery;

/**
 * CmsUserAccountService
 * <p>
 * Created by tidu at 2021-05-01
 */
public interface CmsUserAccountService {

    /**
     * 登录帐户，出错则抛出错误
     */
    CmsUserAccount loginAccount(CmsUserAccountLoginVo accountLoginVo);

    /**
     * 重置密码
     */
    void resetAccountPassword(String stuId, CmsUserAccountResetPasswordVo resetPasswordVo);

    /**
     * 保存处理
     */
    void saveCmsUserAccount(CmsUserAccount cmsUserAccount);

    /**
     * 根据id更新处理
     */
    void updateCmsUserAccountById(CmsUserAccount cmsUserAccount);

    /**
     * 根据stuId删除处理
     */
    void deleteCmsUserAccountByStuId(String stuId);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsUserAccount> queryPagingResult(CmsUserAccountQuery query);
}