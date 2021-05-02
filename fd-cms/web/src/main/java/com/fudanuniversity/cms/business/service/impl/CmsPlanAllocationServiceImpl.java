package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.repository.dao.CmsPlanAllocationDao;
import com.fudanuniversity.cms.repository.entity.CmsPlanAllocation;
import com.fudanuniversity.cms.repository.query.CmsPlanAllocationQuery;
import com.fudanuniversity.cms.business.service.CmsPlanAllocationService;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsPlanAllocationService 实现类
 * <p>
 * Created by tidu at 2021-05-02
 */
@Service
public class CmsPlanAllocationServiceImpl implements CmsPlanAllocationService {

    private static final Logger logger = LoggerFactory.getLogger(CmsPlanAllocationServiceImpl.class);

    @Resource
    private CmsPlanAllocationDao cmsPlanAllocationDao;

    /**
     * 保存处理
     */
    @Override
    public void saveCmsPlanAllocation(CmsPlanAllocation cmsPlanAllocation) {
        //TODO 校验与赋值映射

        int affect = cmsPlanAllocationDao.insert(cmsPlanAllocation);
        logger.info("保存CmsPlanAllocation affect:{}, cmsPlanAllocation: {}", affect, cmsPlanAllocation);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsPlanAllocationById(CmsPlanAllocation cmsPlanAllocation) {
        CmsPlanAllocation updater = new CmsPlanAllocation();
        //TODO 值映射校验与赋值映射

        int affect = cmsPlanAllocationDao.updateById(updater);
        logger.info("更新CmsPlanAllocation affect:{}, updater: {}", affect, updater);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsPlanAllocationById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsPlanAllocationDao.deleteById(id);
        logger.info("删除CmsPlanAllocation affect:{}, id: {}", affect, id);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsPlanAllocation> queryPagingResultByParam(CmsPlanAllocationQuery query) {
        PagingResult<CmsPlanAllocation> pagingResult = PagingResult.create(query);

        //TODO 设置参数（分页参数除外）

        Long count = cmsPlanAllocationDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            //query.setSorts(new SortColumn("create_at", SortMode.DESC));
            List<CmsPlanAllocation> cmsPlanAllocationList = cmsPlanAllocationDao.selectListByParam(query);
            pagingResult.setRows(cmsPlanAllocationList);
        }

        return pagingResult;
    }
}