package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.service.CmsStudyPlanStageService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanStageAddVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanStageQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanStageUpdateVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanStageVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanStageDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanStage;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanStageQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsStudyPlanStageService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:50:00
 */
@Service
public class CmsStudyPlanStageServiceImpl implements CmsStudyPlanStageService {

    private static final Logger logger = LoggerFactory.getLogger(CmsStudyPlanStageServiceImpl.class);

    @Resource
    private CmsStudyPlanStageDao cmsStudyPlanStageDao;

    /**
     * 保存处理
     */
    @Override
    public void saveCmsStudyPlanStage(CmsStudyPlanStageAddVo addVo) {
        //TODO 校验与赋值映射
        CmsStudyPlanStage studyPlanStage = new CmsStudyPlanStage();
        int affect = cmsStudyPlanStageDao.insert(studyPlanStage);
        logger.info("保存CmsStudyPlanStage affect:{}, cmsStudyPlanStage: {}", affect, addVo);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id更新处理
     *
     * @param updateVo
     */
    @Override
    public void updateCmsStudyPlanStageById(CmsStudyPlanStageUpdateVo updateVo) {
        CmsStudyPlanStage updater = new CmsStudyPlanStage();
        //TODO 值映射校验与赋值映射

        int affect = cmsStudyPlanStageDao.updateById(updater);
        logger.info("更新CmsStudyPlanStage affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsStudyPlanStageById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsStudyPlanStageDao.deleteById(id);
        logger.info("删除CmsStudyPlanStage affect:{}, id: {}", affect, id);
        AssertUtils.state(affect == 1);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsStudyPlanStageVo> queryPagingResult(CmsStudyPlanStageQueryVo queryVo, Paging paging) {
        PagingResult<CmsStudyPlanStageVo> pagingResult = PagingResult.create(paging);

        CmsStudyPlanStageQuery query = new CmsStudyPlanStageQuery();
        Long count = cmsStudyPlanStageDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            //query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsStudyPlanStage> cmsStudyPlanStageList = cmsStudyPlanStageDao.selectListByParam(query);
            pagingResult.setRows(cmsStudyPlanStageList, stage -> {
                return null;
            });
        }

        return pagingResult;
    }
}