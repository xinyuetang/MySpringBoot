package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.repository.dao.CmsPlanDao;
import com.fudanuniversity.cms.repository.entity.CmsPlan;
import com.fudanuniversity.cms.repository.query.CmsPlanQuery;
import com.fudanuniversity.cms.business.service.CmsPlanService;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsPlanService 实现类
 * <p>
 * Created by tidu at 2021-05-02
 */
@Service
public class CmsPlanServiceImpl implements CmsPlanService {

    private static final Logger logger = LoggerFactory.getLogger(CmsPlanServiceImpl.class);

    @Resource
    private CmsPlanDao cmsPlanDao;

    /**
     * 保存处理
     */
    @Override
    public void saveCmsPlan(CmsPlan cmsPlan) {
        //TODO 校验与赋值映射

        int affect = cmsPlanDao.insert(cmsPlan);
        logger.info("保存CmsPlan affect:{}, cmsPlan: {}", affect, cmsPlan);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsPlanById(CmsPlan cmsPlan) {
        CmsPlan updater = new CmsPlan();
        //TODO 值映射校验与赋值映射

        int affect = cmsPlanDao.updateById(updater);
        logger.info("更新CmsPlan affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsPlanById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsPlanDao.deleteById(id);
        logger.info("删除CmsPlan affect:{}, id: {}", affect, id);
        AssertUtils.state(affect == 1);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsPlan> queryPagingResultByParam(CmsPlanQuery query) {
        PagingResult<CmsPlan> pagingResult = PagingResult.create(query);

        //TODO 设置参数（分页参数除外）

        Long count = cmsPlanDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            //query.setSorts(new SortColumn("create_at", SortMode.DESC));
            List<CmsPlan> cmsPlanList = cmsPlanDao.selectListByParam(query);
            pagingResult.setRows(cmsPlanList);
        }

        return pagingResult;
    }
}