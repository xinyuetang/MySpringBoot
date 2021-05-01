package com.fudanuniversity.cms.combi.service.impl;

import com.fudanuniversity.cms.combi.service.CmsUserAccountService;
import com.fudanuniversity.cms.combi.vo.CmsUserAccountVo;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.util.JsonUtils;
import com.fudanuniversity.cms.inner.dao.CmsUserAccountDao;
import com.fudanuniversity.cms.inner.entity.CmsUserAccount;
import com.fudanuniversity.cms.inner.query.CmsUserAccountQuery;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * CmsUserAccountService 实现类
 * <p>
 * Created by tidu at 2021-05-01
 */
@Service
public class CmsUserAccountServiceImpl implements CmsUserAccountService {

    private static final Logger logger = LoggerFactory.getLogger(CmsUserAccountServiceImpl.class);

    @Resource
    private CmsUserAccountDao cmsUserAccountDao;

    @Override
    public void loginAccount(CmsUserAccountVo userAccountVo) {
        CmsUserAccount userAccount = queryUserAccount(userAccountVo.getStuId());
        String saltString = userAccount.getSalt() + userAccountVo.getPassword();
        String digestPassword = DigestUtils.md5Hex(saltString);
        if (!Objects.equals(userAccount.getPassword(), digestPassword)) {
            throw new BusinessException("密码不正确");
        }

    }

    @Override
    public void changeAccountPassword(CmsUserAccountVo userAccountVo) {

    }

    private CmsUserAccount queryUserAccount(String stuId) {
        CmsUserAccountQuery accountQuery = new CmsUserAccountQuery();
        accountQuery.setStuId(stuId);
        accountQuery.setLimit(1);
        List<CmsUserAccount> userAccounts = cmsUserAccountDao.selectListByParam(accountQuery);
        if (CollectionUtils.isEmpty(userAccounts)) {
            throw new BusinessException("账号[" + stuId + "]不存在");
        }
        return userAccounts.get(0);
    }

    /**
     * 保存处理
     */
    @Override
    public void saveCmsUserAccount(CmsUserAccount cmsUserAccount) {
        //TODO 校验与赋值映射

        int affect = cmsUserAccountDao.insert(cmsUserAccount);
        logger.info("保存CmsUserAccount affect:{}, cmsUserAccount: {}", affect, JsonUtils.toJsonString(cmsUserAccount));
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsUserAccountById(CmsUserAccount cmsUserAccount) {
        CmsUserAccount updater = new CmsUserAccount();
        //TODO 值映射校验与赋值映射

        int affect = cmsUserAccountDao.updateById(updater);
        logger.info("更新CmsUserAccount affect:{}, updater: {}", affect, JsonUtils.toJsonString(updater));
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsUserAccountById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsUserAccountDao.deleteById(id);
        logger.info("删除CmsUserAccount affect:{}, id: {}", affect, JsonUtils.toJsonString(id));
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsUserAccount> queryPagingResultByParam(CmsUserAccountQuery query) {
        PagingResult<CmsUserAccount> pagingResult = PagingResult.create(query);

        //TODO 设置参数（分页参数除外）

        Long count = cmsUserAccountDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            //query.setSorts(new SortColumn("create_at", SortMode.DESC));
            List<CmsUserAccount> cmsUserAccountList = cmsUserAccountDao.selectListByParam(query);
            pagingResult.setRows(cmsUserAccountList);
        }

        return pagingResult;
    }
}