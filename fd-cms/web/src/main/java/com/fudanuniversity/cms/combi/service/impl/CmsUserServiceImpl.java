package com.fudanuniversity.cms.combi.service.impl;

import com.fudanuniversity.cms.combi.service.CmsUserService;
import com.fudanuniversity.cms.commons.constant.Constants;
import com.fudanuniversity.cms.commons.enums.RoleEnum;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.JsonUtils;
import com.fudanuniversity.cms.inner.dao.CmsUserDao;
import com.fudanuniversity.cms.inner.entity.CmsUser;
import com.fudanuniversity.cms.inner.query.CmsUserQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * CmsUserService 实现类
 * <p>
 * Created by tidu at 2021-05-01
 */
@Service
public class CmsUserServiceImpl implements CmsUserService {

    private static final Logger logger = LoggerFactory.getLogger(CmsUserServiceImpl.class);

    @Resource
    private CmsUserDao cmsUserDao;

    @Override
    public PagingResult<CmsUser> getAllUsers(Paging paging) {
        PagingResult<CmsUser> pagingResult = PagingResult.create(paging);

        CmsUserQuery query = new CmsUserQuery();
        query.setOffset(paging.getOffset());
        query.setLimit(paging.getLimit());
        query.setGtId(0L);
        query.setSorts(SortColumn.create(Constants.IdColumn, SortMode.ASC));

        List<CmsUser> users = cmsUserDao.selectListByParam(query);
        pagingResult.setRows(users);

        return pagingResult;
    }

    @Override
    public void confirmUserPrivilege(String stuId, RoleEnum privilege) {
        CmsUserQuery query = new CmsUserQuery();
        query.setStuId(stuId);
        query.setLimit(1);
        List<CmsUser> users = cmsUserDao.selectListByParam(query);
        if (CollectionUtils.isEmpty(users)) {
            throw new BusinessException("用户[" + stuId + "]不存在");
        }
        CmsUser cmsUser = users.get(0);
        if (Objects.equals(cmsUser.getRoleId(), privilege.getCode())) {
            throw new BusinessException("用户[" + stuId + "]不存在");
        }
    }

    /**
     * 保存处理
     */
    @Override
    public void saveCmsUser(CmsUser cmsUser) {
        //TODO 校验与赋值映射

        int affect = cmsUserDao.insert(cmsUser);
        logger.info("保存CmsUser affect:{}, cmsUser: {}", affect, JsonUtils.toJsonString(cmsUser));
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsUserById(CmsUser cmsUser) {
        CmsUser updater = new CmsUser();
        //TODO 值映射校验与赋值映射

        int affect = cmsUserDao.updateById(updater);
        logger.info("更新CmsUser affect:{}, updater: {}", affect, JsonUtils.toJsonString(updater));
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsUserById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsUserDao.deleteById(id);
        logger.info("删除CmsUser affect:{}, id: {}", affect, JsonUtils.toJsonString(id));
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsUser> queryPagingResultByParam(CmsUserQuery query) {
        PagingResult<CmsUser> pagingResult = PagingResult.create(query);

        //TODO 设置参数（分页参数除外）

        Long count = cmsUserDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            //query.setSorts(new SortColumn("create_at", SortMode.DESC));
            List<CmsUser> cmsUserList = cmsUserDao.selectListByParam(query);
            pagingResult.setRows(cmsUserList);
        }

        return pagingResult;
    }
}