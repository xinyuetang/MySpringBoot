package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsUserAccountService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.user.CmsUserMngVo;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.enums.DeletedEnum;
import com.fudanuniversity.cms.commons.enums.UserRoleEnum;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.commons.util.JsonUtils;
import com.fudanuniversity.cms.repository.dao.CmsUserDao;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.entity.CmsUserAccount;
import com.fudanuniversity.cms.repository.query.CmsUserQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
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

    @Resource
    private CmsUserComponent cmsUserComponent;

    @Resource
    private CmsUserAccountService cmsUserAccountService;

    @Override
    public PagingResult<CmsUser> getAllUsers(Paging paging) {
        PagingResult<CmsUser> pagingResult = PagingResult.create(paging);

        CmsUserQuery query = new CmsUserQuery();
        query.setOffset(paging.getOffset());
        query.setLimit(paging.getLimit());
        query.setGtId(0L);
        query.setSorts(SortColumn.create(CmsConstants.IdColumn, SortMode.ASC));

        List<CmsUser> users = cmsUserDao.selectListByParam(query);
        pagingResult.setRows(users);

        return pagingResult;
    }

    @Override
    public void confirmUserPrivilege(String stuId, UserRoleEnum privilege) {
        CmsUser cmsUser = cmsUserComponent.queryUser(stuId);
        if (cmsUser == null) {
            throw new BusinessException("用户[" + stuId + "]不存在");
        }
        if (!Objects.equals(cmsUser.getRoleId(), UserRoleEnum.Administrator.getCode())
                && !Objects.equals(cmsUser.getRoleId(), privilege.getCode())) {
            throw new BusinessException("用户没有[" + privilege.getDesc() + "]权限");
        }
    }

    /**
     * 新增用户
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveCmsUser(CmsUserMngVo userAddVo) {
        String stuId = userAddVo.getStuId();
        if (StringUtils.isNotEmpty(stuId)) {
            CmsUserQuery query = new CmsUserQuery();
            query.setStuId(stuId);
            query.setDeleted(DeletedEnum.Normal.getCode());
            query.setLimit(1);
            List<CmsUser> cmsUsers = cmsUserDao.selectListByParam(query);
            if (CollectionUtils.isNotEmpty(cmsUsers)) {
                throw new BusinessException("学号为[" + stuId + "]用户已存在");
            }
        }

        CmsUser cmsUser = new CmsUser();
        cmsUser.setStuId(stuId);
        cmsUser.setRoleId(userAddVo.getRoleId());
        cmsUser.setName(userAddVo.getName());
        cmsUser.setTelephone(userAddVo.getTelephone());
        cmsUser.setEmail(userAddVo.getEmail());
        cmsUser.setMentor(userAddVo.getMentor());
        cmsUser.setLeader(userAddVo.getLeader());
        cmsUser.setStudyType(userAddVo.getStudyType());
        cmsUser.setKeshuo(userAddVo.getKeshuo());
        cmsUser.setEnrollDate(userAddVo.getEnrollDate());
        cmsUser.setPapers(userAddVo.getPapers());
        cmsUser.setPatents(userAddVo.getPatents());
        cmsUser.setServices(userAddVo.getServices());
        cmsUser.setProjects(userAddVo.getProjects());
        cmsUser.setStatus(0);
        cmsUser.setDeleted(DeletedEnum.Normal.getCode());
        cmsUser.setCreateTime(new Date());

        int affect = cmsUserDao.replace(cmsUser);
        AssertUtils.state(affect == 1);
        logger.info("保存CmsUser affect:{}, cmsUser: {}", affect, cmsUser);

        CmsUserAccount cmsUserAccount = new CmsUserAccount();
        cmsUserAccount.setStuId(stuId);
        cmsUserAccount.setSalt(stuId);
        cmsUserAccount.setPassword("123456");
        cmsUserAccountService.saveCmsUserAccount(cmsUserAccount);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsUserById(CmsUserMngVo userUpdateVo) {
        Long userId = userUpdateVo.getId();
        String stuId = userUpdateVo.getStuId();
        CmsUserQuery existsQuery = new CmsUserQuery();
        existsQuery.setId(userId);
        existsQuery.setDeleted(DeletedEnum.Normal.getCode());
        List<CmsUser> updateUsers = cmsUserDao.selectListByParam(existsQuery);
        CmsUser updateUser = updateUsers.get(0);
        if (CollectionUtils.isEmpty(updateUsers)) {
            throw new BusinessException("更新的用户不存在");
        }
        CmsUserQuery stuIdQuery = new CmsUserQuery();
        stuIdQuery.setStuId(stuId);
        stuIdQuery.setDeleted(DeletedEnum.Normal.getCode());
        List<CmsUser> stuIdUsers = cmsUserDao.selectListByParam(stuIdQuery);
        if (CollectionUtils.isNotEmpty(stuIdUsers)) {
            CmsUser stuIdUser = stuIdUsers.get(0);
            if (!Objects.equals(userUpdateVo.getId(), stuIdUser.getId())) {
                throw new BusinessException("学号为[" + stuId + "]用户已存在");
            }
        }

        CmsUser updater = new CmsUser();
        updater.setId(userId);
        updater.setStuId(stuId);
        updater.setRoleId(userUpdateVo.getRoleId());
        updater.setName(userUpdateVo.getName());
        updater.setTelephone(userUpdateVo.getTelephone());
        updater.setEmail(userUpdateVo.getEmail());
        updater.setMentor(userUpdateVo.getMentor());
        updater.setLeader(userUpdateVo.getLeader());
        updater.setStudyType(userUpdateVo.getStudyType());
        updater.setKeshuo(userUpdateVo.getKeshuo());
        updater.setEnrollDate(userUpdateVo.getEnrollDate());
        updater.setPapers(userUpdateVo.getPapers());
        updater.setPatents(userUpdateVo.getPatents());
        updater.setServices(userUpdateVo.getServices());
        updater.setProjects(userUpdateVo.getProjects());
        updater.setStatus(0);
        updater.setDeleted(DeletedEnum.Normal.getCode());
        updater.setCreateTime(updateUser.getCreateTime());
        updater.setModifyTime(new Date());

        int affect = cmsUserDao.replace(updater);
        AssertUtils.state(affect == 1);
        logger.info("更新CmsUser affect:{}, updater: {}", affect, updater);
    }

    /**
     * 根据id删除处理
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void deleteCmsUserById(Long userId) {
        CmsUserQuery query = new CmsUserQuery();
        query.setId(userId);
        query.setDeleted(DeletedEnum.Normal.getCode());
        query.setLimit(1);
        List<CmsUser> cmsUsers = cmsUserDao.selectListByParam(query);
        if (CollectionUtils.isEmpty(cmsUsers)) {
            throw new BusinessException("删除的用户不存在");
        }
        CmsUser cmsUser = cmsUsers.get(0);
        int affect = cmsUserDao.deleteById(userId);
        AssertUtils.state(affect == 1);
        logger.info("删除CmsUser affect:{}, id: {}", affect, JsonUtils.toJsonString(userId));
        cmsUserAccountService.deleteCmsUserAccountByStuId(cmsUser.getStuId());
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsUser> queryPagingResult(CmsUserQuery query) {
        PagingResult<CmsUser> pagingResult = PagingResult.create(query);

        //TODO 设置参数（分页参数除外）

        Long count = cmsUserDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            //query.setSorts(SortColumn.create("create_at", SortMode.DESC));
            List<CmsUser> cmsUserList = cmsUserDao.selectListByParam(query);
            pagingResult.setRows(cmsUserList);
        }

        return pagingResult;
    }
}