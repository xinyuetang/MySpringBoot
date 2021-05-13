package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsSeminarService;
import com.fudanuniversity.cms.business.vo.seminar.CmsSeminarAddVo;
import com.fudanuniversity.cms.business.vo.seminar.CmsSeminarQueryVo;
import com.fudanuniversity.cms.business.vo.seminar.CmsSeminarUpdateVo;
import com.fudanuniversity.cms.business.vo.seminar.CmsSeminarVo;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.commons.util.ValueUtils;
import com.fudanuniversity.cms.repository.dao.CmsSeminarDao;
import com.fudanuniversity.cms.repository.entity.CmsSeminar;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsSeminarQuery;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
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
 * Created by Xinyue.Tang at 2021-05-02
 */
@Service
public class CmsSeminarServiceImpl implements CmsSeminarService {

    private static final Logger logger = LoggerFactory.getLogger(CmsSeminarServiceImpl.class);

    @Resource
    private CmsSeminarDao cmsSeminarDao;

    @Resource
    private CmsUserComponent cmsUserComponent;

    @Override
    public void addNewSeminar(CmsSeminarAddVo addVo) {
        Long speakerId = addVo.getSpeakerId();
        CmsUser seminarUser = cmsUserComponent.queryUser(speakerId);
        AssertUtils.notNull(seminarUser, "用户[" + speakerId + "]不存在");
        CmsSeminar cmsSeminar = new CmsSeminar();
        cmsSeminar.setSpeakerId(seminarUser.getId());
        cmsSeminar.setDate(addVo.getDate());
        cmsSeminar.setTheme(addVo.getTheme());
        cmsSeminar.setLink(ValueUtils.defaultString(addVo.getLink()));
        cmsSeminar.setDescription(addVo.getDescription());
        cmsSeminar.setCreateTime(new Date());
        int affect = cmsSeminarDao.insert(cmsSeminar);
        logger.info("保存CmsSeminar affect:{}, cmsSeminar: {}", affect, addVo);
    }

    @Override
    public void updateSeminarById(CmsSeminarUpdateVo updateVo) {
        Long seminarId = updateVo.getId();
        checkCmsSeminarExists(seminarId);

        CmsSeminar updater = new CmsSeminar();
        updater.setId(seminarId);

        Long speakerId = updateVo.getSpeakerId();
        if (speakerId != null) {
            CmsUser seminarUser = cmsUserComponent.queryUser(speakerId);
            AssertUtils.notNull(seminarUser, "用户[" + speakerId + "]不存在");
            updater.setSpeakerId(seminarUser.getId());
        }

        updater.setDate(updateVo.getDate());
        updater.setTheme(updateVo.getTheme());
        updater.setLink(updateVo.getLink());
        updater.setDescription(updateVo.getDescription());
        updater.setModifyTime(new Date());
        int affect = cmsSeminarDao.updateById(updater);
        logger.info("更新CmsSeminar affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    @Override
    public PagingResult<CmsSeminarVo> queryPagingResult(CmsSeminarQueryVo queryVo, Paging paging) {
        PagingResult<CmsSeminarVo> pagingResult = PagingResult.create(paging);

        CmsSeminarQuery query = new CmsSeminarQuery();
        query.setId(queryVo.getId());
        query.setSpeakerId(queryVo.getUserId());
        query.setTheme(queryVo.getTheme());
        query.setDate(queryVo.getDate());
        Long total = cmsSeminarDao.selectCountByParam(query);
        pagingResult.setTotal(total);

        if (total > 0L) {
            query.setPaging(paging);
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsSeminar> seminars = cmsSeminarDao.selectListByParam(query);

            List<Long> userIds = Lists.transform(seminars, CmsSeminar::getSpeakerId);
            Map<Long, CmsUser> userMap = cmsUserComponent.queryUserMap(userIds);

            pagingResult.setRows(seminars, seminar -> {
                CmsSeminarVo seminarVo = new CmsSeminarVo();
                seminarVo.setId(seminar.getId());
                Long userId = seminar.getSpeakerId();
                seminarVo.setSpeakerId(userId);
                CmsUser speakerUser = userMap.get(userId);
                if (speakerUser != null) {
                    seminarVo.setSpeakerStuId(speakerUser.getStuId());
                    seminarVo.setSpeakerName(speakerUser.getName());
                }
                seminarVo.setDate(seminar.getDate());
                seminarVo.setTheme(seminar.getTheme());
                seminarVo.setLink(seminar.getLink());
                seminarVo.setDescription(seminar.getDescription());
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