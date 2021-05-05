package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.service.CmsStudyPlanAllocationService;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationAddVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationQueryVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationUpdateVo;
import com.fudanuniversity.cms.business.vo.study.plan.CmsStudyPlanAllocationVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsStudyPlanAllocationDao;
import com.fudanuniversity.cms.repository.entity.CmsStudyPlanAllocation;
import com.fudanuniversity.cms.repository.query.CmsStudyPlanAllocationQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsStudyPlanAllocationService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:50:00
 */
@Service
public class CmsStudyPlanAllocationServiceImpl implements CmsStudyPlanAllocationService {

    private static final Logger logger = LoggerFactory.getLogger(CmsStudyPlanAllocationServiceImpl.class);

    @Resource
    private CmsStudyPlanAllocationDao cmsStudyPlanAllocationDao;

    /**
     * 保存处理
     *
     * @param addVo
     */
    @Override
    public void saveCmsStudyPlanAllocation(CmsStudyPlanAllocationAddVo addVo) {
        //TODO 校验与赋值映射
        CmsStudyPlanAllocation studyPlanAllocation = new CmsStudyPlanAllocation();
        int affect = cmsStudyPlanAllocationDao.insert(studyPlanAllocation);
        logger.info("保存CmsStudyPlanAllocation affect:{}, cmsStudyPlanAllocation: {}", affect, addVo);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id更新处理
     *
     * @param updateVo
     */
    @Override
    public void updateCmsStudyPlanAllocationById(CmsStudyPlanAllocationUpdateVo updateVo) {
        CmsStudyPlanAllocation updater = new CmsStudyPlanAllocation();
        //TODO 值映射校验与赋值映射

        int affect = cmsStudyPlanAllocationDao.updateById(updater);
        logger.info("更新CmsStudyPlanAllocation affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsStudyPlanAllocationById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsStudyPlanAllocationDao.deleteById(id);
        logger.info("删除CmsStudyPlanAllocation affect:{}, id: {}", affect, id);
        AssertUtils.state(affect == 1);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsStudyPlanAllocationVo> queryPagingResult(CmsStudyPlanAllocationQueryVo queryVo, Paging paging) {
        PagingResult<CmsStudyPlanAllocationVo> pagingResult = PagingResult.create(paging);

        CmsStudyPlanAllocationQuery query = new CmsStudyPlanAllocationQuery();
        Long count = cmsStudyPlanAllocationDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            //query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsStudyPlanAllocation> cmsStudyPlanAllocationList = cmsStudyPlanAllocationDao.selectListByParam(query);
            pagingResult.setRows(cmsStudyPlanAllocationList, allocation -> {
                return null;
            });
        }

        return pagingResult;
    }
}