package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.service.CmsStudyPlanWorkService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkAddVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkUpdateVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanWorkVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanWorkDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanWork;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanWorkQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsStudyPlanWorkService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:50:00
 */
@Service
public class CmsStudyPlanWorkServiceImpl implements CmsStudyPlanWorkService {

    private static final Logger logger = LoggerFactory.getLogger(CmsStudyPlanWorkServiceImpl.class);

    @Resource
    private CmsStudyPlanWorkDao cmsStudyPlanWorkDao;

    /**
     * 保存处理
     *
     * @param addVo
     */
    @Override
    public void saveCmsStudyPlanWork(CmsStudyPlanWorkAddVo addVo) {
        //TODO 校验与赋值映射
        CmsStudyPlanWork studyPlanWork = new CmsStudyPlanWork();
        int affect = cmsStudyPlanWorkDao.insert(studyPlanWork);
        logger.info("保存CmsStudyPlanWork affect:{}, cmsStudyPlanWork: {}", affect, addVo);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id更新处理
     *
     * @param updateVo
     */
    @Override
    public void updateCmsStudyPlanWorkById(CmsStudyPlanWorkUpdateVo updateVo) {
        CmsStudyPlanWork updater = new CmsStudyPlanWork();
        //TODO 值映射校验与赋值映射

        int affect = cmsStudyPlanWorkDao.updateById(updater);
        logger.info("更新CmsStudyPlanWork affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsStudyPlanWorkById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsStudyPlanWorkDao.deleteById(id);
        logger.info("删除CmsStudyPlanWork affect:{}, id: {}", affect, id);
        AssertUtils.state(affect == 1);
    }

    /**
     * 分页查询数据列表
     *
     * @return
     */
    @Override
    public PagingResult<CmsStudyPlanWorkVo> queryPagingResult(CmsStudyPlanWorkQueryVo queryVo, Paging paging) {
        PagingResult<CmsStudyPlanWorkVo> pagingResult = PagingResult.create(paging);

        CmsStudyPlanWorkQuery query = CmsStudyPlanWorkQuery.listQuery();
        Long count = cmsStudyPlanWorkDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            //query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsStudyPlanWork> cmsStudyPlanWorkList = cmsStudyPlanWorkDao.selectListByParam(query);
            pagingResult.setRows(cmsStudyPlanWorkList, work -> {
                return null;
            });
        }

        return pagingResult;
    }
}