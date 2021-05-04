package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsUserAccountService;
import com.fudanuniversity.cms.business.service.CmsUserService;
import com.fudanuniversity.cms.business.vo.user.*;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.enums.DeletedEnum;
import com.fudanuniversity.cms.commons.enums.UserRoleEnum;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.commons.util.DateExUtils;
import com.fudanuniversity.cms.commons.util.JsonUtils;
import com.fudanuniversity.cms.commons.util.ValueUtils;
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
import java.util.stream.Collectors;

/**
 * CmsUserService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-01
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
    public void confirmUserPrivilege(String stuId, UserRoleEnum privilege) {
        CmsUser cmsUser = cmsUserComponent.queryUser(stuId);
        AssertUtils.notNull(cmsUser, "用户[" + stuId + "]不存在");
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
    public void saveCmsUser(CmsUserAddVo userAddVo) {
        String stuId = userAddVo.getStuId();
        if (StringUtils.isNotEmpty(stuId)) {
            CmsUserQuery query = new CmsUserQuery();
            query.setStuId(stuId);
            query.setLimit(1);
            List<CmsUser> cmsUsers = cmsUserDao.selectListByParam(query);
            if (CollectionUtils.isNotEmpty(cmsUsers)) {
                CmsUser existsUser = cmsUsers.get(0);
                if (Objects.equals(DeletedEnum.Normal.getCode(), existsUser.getDeleted())) {
                    throw new BusinessException("学号为[" + stuId + "]用户已存在");
                } else {
                    cmsUserDao.deleteById(existsUser.getId());
                }
            }
        }

        CmsUser cmsUser = new CmsUser();
        cmsUser.setType(ValueUtils.defaultInteger(userAddVo.getType()));
        cmsUser.setStuId(stuId);
        cmsUser.setRoleId(userAddVo.getRoleId());
        cmsUser.setName(userAddVo.getName());
        cmsUser.setTelephone(ValueUtils.defaultString(userAddVo.getTelephone()));
        cmsUser.setEmail(ValueUtils.defaultString(userAddVo.getEmail()));
        cmsUser.setMentor(ValueUtils.defaultString(userAddVo.getMentor()));
        cmsUser.setLeader(ValueUtils.defaultString(userAddVo.getLeader()));
        cmsUser.setStudyType(ValueUtils.defaultInteger(userAddVo.getStudyType()));
        cmsUser.setKeshuo(ValueUtils.defaultInteger(userAddVo.getKeshuo()));
        Date enrollDate = userAddVo.getEnrollDate();
        if (enrollDate != null) {
            cmsUser.setEnrollDate(enrollDate);
            cmsUser.setEnrollYear(DateExUtils.getYear(enrollDate));
        }
        cmsUser.setPapers(userAddVo.getPapers());
        cmsUser.setPatents(userAddVo.getPatents());
        cmsUser.setServices(userAddVo.getServices());
        cmsUser.setProjects(userAddVo.getProjects());
        cmsUser.setStatus(0);
        cmsUser.setDeleted(DeletedEnum.Normal.getCode());
        cmsUser.setCreateTime(new Date());

        int affect = cmsUserDao.insert(cmsUser);
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
    public void updateCmsUserById(CmsUserUpdateVo updateVo) {
        Long userId = updateVo.getId();
        CmsUserQuery existsQuery = CmsUserQuery.singletonQuery();
        existsQuery.setId(userId);
        existsQuery.setDeleted(DeletedEnum.Normal.getCode());
        List<CmsUser> updateUsers = cmsUserDao.selectListByParam(existsQuery);
        if (CollectionUtils.isEmpty(updateUsers)) {
            throw new BusinessException("更新的用户不存在");
        }
        CmsUser updateUser = updateUsers.get(0);

        CmsUser updater = new CmsUser();
        updater.setId(updateUser.getId());
        updater.setType(ValueUtils.defaultInteger(updateVo.getType()));
        updater.setRoleId(updateVo.getRoleId());
        updater.setName(updateVo.getName());
        updater.setTelephone(ValueUtils.defaultString(updateVo.getTelephone()));
        updater.setEmail(ValueUtils.defaultString(updateVo.getEmail()));
        updater.setMentor(ValueUtils.defaultString(updateVo.getMentor()));
        updater.setLeader(ValueUtils.defaultString(updateVo.getLeader()));
        updater.setStudyType(ValueUtils.defaultInteger(updateVo.getStudyType()));
        updater.setKeshuo(ValueUtils.defaultInteger(updateVo.getKeshuo()));
        Date enrollDate = updateVo.getEnrollDate();
        if (enrollDate != null) {
            updater.setEnrollDate(enrollDate);
            updater.setEnrollYear(DateExUtils.getYear(enrollDate));
        }
        updater.setPapers(updateVo.getPapers());
        updater.setPatents(updateVo.getPatents());
        updater.setServices(updateVo.getServices());
        updater.setProjects(updateVo.getProjects());
        updater.setStatus(0);
        updater.setDeleted(DeletedEnum.Normal.getCode());
        updater.setModifyTime(new Date());

        int affect = cmsUserDao.updateById(updater);
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
     * 查询数据列表
     */
    @Override
    public List<CmsUserVo> queryUserList(CmsUserQueryVo queryVo, Paging paging) {
        CmsUserQuery query = CmsUserQuery.listQuery();
        query.setId(queryVo.getId());
        query.setKw(queryVo.getKw());
        query.setType(queryVo.getType());
        query.setStuId(queryVo.getStuId());
        query.setRoleId(queryVo.getRoleId());
        query.setName(queryVo.getName());
        query.setTelephone(queryVo.getTelephone());
        query.setEmail(queryVo.getEmail());
        query.setMentor(queryVo.getMentor());
        query.setLeader(queryVo.getLeader());
        query.setStudyType(queryVo.getStudyType());
        query.setKeshuo(queryVo.getKeshuo());
        query.setEnrollYear(queryVo.getEnrollYear());
        query.setEnrollDate(queryVo.getEnrollDate());
        query.setStatus(queryVo.getStatus());
        query.setEltCreateTime(queryVo.getEltCreateTime());
        query.setEgtCreateTime(queryVo.getEgtCreateTime());
        query.setEltModifyTime(queryVo.getEltModifyTime());
        query.setEgtModifyTime(queryVo.getEgtModifyTime());
        query.setOffset(paging.getOffset());
        query.setLimit(paging.getLimit());
        query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
        List<CmsUser> cmsUserList = cmsUserDao.selectListByParam(query);

        return cmsUserList.stream()
                .map(this::convertCmsUserVo).collect(Collectors.toList());
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsUserVo> queryPagingResult(CmsUserQueryVo queryVo, Paging paging) {
        PagingResult<CmsUserVo> pagingResult = PagingResult.create(paging);

        CmsUserQuery query = CmsUserQuery.listQuery();
        query.setId(queryVo.getId());
        query.setType(queryVo.getType());
        query.setStuId(queryVo.getStuId());
        query.setRoleId(queryVo.getRoleId());
        query.setName(queryVo.getName());
        query.setTelephone(queryVo.getTelephone());
        query.setEmail(queryVo.getEmail());
        query.setMentor(queryVo.getMentor());
        query.setLeader(queryVo.getLeader());
        query.setStudyType(queryVo.getStudyType());
        query.setKeshuo(queryVo.getKeshuo());
        query.setEnrollYear(queryVo.getEnrollYear());
        query.setEnrollDate(queryVo.getEnrollDate());
        query.setStatus(queryVo.getStatus());
        query.setEltCreateTime(queryVo.getEltCreateTime());
        query.setEgtCreateTime(queryVo.getEgtCreateTime());
        query.setEltModifyTime(queryVo.getEltModifyTime());
        query.setEgtModifyTime(queryVo.getEgtModifyTime());
        Long count = cmsUserDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsUser> cmsUserList = cmsUserDao.selectListByParam(query);
            pagingResult.setRows(cmsUserList, this::convertCmsUserVo);
        }

        return pagingResult;
    }

    private CmsUserVo convertCmsUserVo(CmsUser cmsUser) {
        CmsUserVo userVo = new CmsUserVo();
        userVo.setId(cmsUser.getId());
        userVo.setType(cmsUser.getType());
        userVo.setStuId(cmsUser.getStuId());
        userVo.setRoleId(cmsUser.getRoleId());
        userVo.setName(cmsUser.getName());
        userVo.setTelephone(cmsUser.getTelephone());
        userVo.setEmail(cmsUser.getEmail());
        userVo.setMentor(cmsUser.getMentor());
        userVo.setLeader(cmsUser.getLeader());
        userVo.setStudyType(cmsUser.getStudyType());
        userVo.setKeshuo(cmsUser.getKeshuo());
        userVo.setEnrollYear(cmsUser.getEnrollYear());
        userVo.setEnrollDate(cmsUser.getEnrollDate());
        userVo.setStatus(cmsUser.getStatus());
        userVo.setCreateTime(cmsUser.getCreateTime());
        userVo.setModifyTime(cmsUser.getModifyTime());
        return userVo;
    }


    @Override
    public CmsUserDetailVo queryUserDetail(String stuId) {
        AssertUtils.hasText(stuId, "学号/工号不能为空");
        CmsUser cmsUser = cmsUserComponent.queryUser(stuId);
        AssertUtils.notNull(cmsUser, "用户[" + stuId + "]不存在");

        CmsUserDetailVo detailVo = new CmsUserDetailVo();
        detailVo.setId(cmsUser.getId());
        detailVo.setType(cmsUser.getType());
        detailVo.setStuId(cmsUser.getStuId());
        detailVo.setRoleId(cmsUser.getRoleId());
        detailVo.setName(cmsUser.getName());
        detailVo.setTelephone(cmsUser.getTelephone());
        detailVo.setEmail(cmsUser.getEmail());
        detailVo.setMentor(cmsUser.getMentor());
        detailVo.setLeader(cmsUser.getLeader());
        detailVo.setStudyType(cmsUser.getStudyType());
        detailVo.setKeshuo(cmsUser.getKeshuo());
        detailVo.setEnrollDate(cmsUser.getEnrollDate());
        detailVo.setPapers(cmsUser.getPapers());
        detailVo.setPatents(cmsUser.getPatents());
        detailVo.setServices(cmsUser.getServices());
        detailVo.setProjects(cmsUser.getProjects());
        detailVo.setStatus(cmsUser.getStatus());
        detailVo.setCreateTime(cmsUser.getCreateTime());
        detailVo.setModifyTime(cmsUser.getModifyTime());
        return detailVo;
    }

}