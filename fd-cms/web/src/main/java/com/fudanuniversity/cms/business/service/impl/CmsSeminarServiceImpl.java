package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsSeminarService;
import com.fudanuniversity.cms.business.vo.seminar.CmsSeminarAddVo;
import com.fudanuniversity.cms.business.vo.seminar.CmsSeminarUpdateVo;
import com.fudanuniversity.cms.business.vo.seminar.CmsSeminarVo;
import com.fudanuniversity.cms.commons.constant.Constants;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsSeminarDao;
import com.fudanuniversity.cms.repository.entity.CmsSeminar;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsSeminarQuery;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * CmsSeminarService 实现类
 * <p>
 * Created by tidu at 2021-05-02
 */
@Service
public class CmsSeminarServiceImpl implements CmsSeminarService {

    private static final Logger logger = LoggerFactory.getLogger(CmsSeminarServiceImpl.class);

    @Resource
    private CmsSeminarDao cmsSeminarDao;

    @Resource
    private CmsUserComponent cmsUserComponent;

    @Override
    public void addNewSeminar(CmsSeminarAddVo seminarVo) {
        String seminarStuId = seminarVo.getStuId();
        CmsUser seminarUser = cmsUserComponent.queryUser(seminarStuId);
        CmsSeminar cmsSeminar = new CmsSeminar();
        cmsSeminar.setUserId(seminarUser.getId());
        cmsSeminar.setTheme(seminarVo.getTheme());
        cmsSeminar.setLink(seminarVo.getLink());
        cmsSeminar.setDate(seminarVo.getDate());
        cmsSeminar.setCreateTime(new Date());
        int affect = cmsSeminarDao.insert(cmsSeminar);
        logger.info("保存CmsSeminar affect:{}, cmsSeminar: {}", affect, seminarVo);
    }

    @Override
    public void updateSeminarById(CmsSeminarUpdateVo seminarUpdateVo) {
        Long seminarId = seminarUpdateVo.getId();
        checkCmsSeminarExists(seminarId);

        CmsSeminar updater = new CmsSeminar();
        updater.setId(seminarId);

        String seminarStuId = seminarUpdateVo.getStuId();
        if (StringUtils.isNotEmpty(seminarStuId)) {
            CmsUser seminarUser = cmsUserComponent.queryUser(seminarStuId);
            updater.setUserId(seminarUser.getId());
        }

        updater.setTheme(seminarUpdateVo.getTheme());
        updater.setLink(seminarUpdateVo.getLink());
        updater.setDate(seminarUpdateVo.getDate());
        int affect = cmsSeminarDao.updateById(updater);
        logger.info("更新CmsSeminar affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    @Override
    public PagingResult<CmsSeminarVo> queryRecentSeminars(Paging paging) {
        PagingResult<CmsSeminarVo> pagingResult = PagingResult.create(paging);
        CmsSeminarQuery query = new CmsSeminarQuery();
        Long total = cmsSeminarDao.selectCountByParam(query);

        if (total > 0L) {
            query.setPaging(paging);
            query.setSorts(SortColumn.create(Constants.CreatedTimeColumn, SortMode.DESC));
            List<CmsSeminar> seminars = cmsSeminarDao.selectListByParam(query);

            List<Long> userIds = Lists.transform(seminars, CmsSeminar::getUserId);
            Map<Long, CmsUser> userMap = cmsUserComponent.queryUsersMap(userIds);

            pagingResult.setRows(seminars, seminar -> {
                CmsSeminarVo seminarVo = new CmsSeminarVo();
                seminarVo.setId(seminar.getId());
                Long userId = seminar.getUserId();
                seminarVo.setSpeakerId(userId);
                CmsUser speakerUser = userMap.get(userId);
                if (speakerUser != null) {
                    seminarVo.setSpeakerName(speakerUser.getName());
                }
                seminarVo.setTheme(seminar.getTheme());
                seminarVo.setLink(seminar.getLink());
                seminarVo.setDate(seminar.getDate());
                seminarVo.setCreateTime(seminar.getCreateTime());
                seminarVo.setModifyTime(seminar.getModifyTime());
                return seminarVo;
            });
        }

        return pagingResult;
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsSeminarById(Long seminarId) {
        checkCmsSeminarExists(seminarId);

        int affect = cmsSeminarDao.deleteById(seminarId);
        logger.info("删除CmsSeminar affect:{}, id: {}", affect, seminarId);
    }

    private void checkCmsSeminarExists(Long seminarId) {
        CmsSeminarQuery query = new CmsSeminarQuery();
        query.setId(seminarId);
        List<CmsSeminar> seminars = cmsSeminarDao.selectListByParam(query);
        if (CollectionUtils.isEmpty(seminars)) {
            throw new BusinessException("演讲记录不存在");
        }
    }
}