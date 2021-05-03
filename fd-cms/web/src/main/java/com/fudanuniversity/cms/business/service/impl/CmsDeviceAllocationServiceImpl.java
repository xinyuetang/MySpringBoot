package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationApplyVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationReturnVo;
import com.fudanuniversity.cms.business.vo.device.CmsDeviceAllocationVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.repository.dao.CmsDeviceAllocationDao;
import com.fudanuniversity.cms.repository.entity.CmsDeviceAllocation;
import com.fudanuniversity.cms.repository.query.CmsDeviceAllocationQuery;
import com.fudanuniversity.cms.business.service.CmsDeviceAllocationService;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsDeviceAllocationService 实现类
 * <p>
 * Created by tidu at 2021-05-03
 */
@Service
public class CmsDeviceAllocationServiceImpl implements CmsDeviceAllocationService {

    private static final Logger logger = LoggerFactory.getLogger(CmsDeviceAllocationServiceImpl.class);

    @Resource
    private CmsDeviceAllocationDao cmsDeviceAllocationDao;

    /**
     * 保存处理
     */
    @Override
    public void saveCmsDeviceAllocation(CmsDeviceAllocation cmsDeviceAllocation) {
        //TODO 校验与赋值映射

        int affect = cmsDeviceAllocationDao.insert(cmsDeviceAllocation);
        logger.info("保存CmsDeviceAllocation affect:{}, cmsDeviceAllocation: {}", affect, cmsDeviceAllocation);
        AssertUtils.state(affect == 1);
    }

    @Override
    public void applyDeviceAllocation(CmsDeviceAllocationApplyVo allocationApplyVo) {

    }

    @Override
    public void returnDeviceAllocation(CmsDeviceAllocationReturnVo allocationReturnVo) {

    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsDeviceAllocationById(CmsDeviceAllocation cmsDeviceAllocation) {
        CmsDeviceAllocation updater = new CmsDeviceAllocation();
        //TODO 值映射校验与赋值映射

        int affect = cmsDeviceAllocationDao.updateById(updater);
        logger.info("更新CmsDeviceAllocation affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsDeviceAllocationById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsDeviceAllocationDao.deleteById(id);
        logger.info("删除CmsDeviceAllocation affect:{}, id: {}", affect, id);
        AssertUtils.state(affect == 1);
    }

    @Override
    public PagingResult<CmsDeviceAllocationVo> queryPagingResult(Paging paging) {
        return null;
    }

    /**
     * 分页查询数据列表
     */
    public PagingResult<CmsDeviceAllocation> queryPagingResult(CmsDeviceAllocationQuery query) {
        PagingResult<CmsDeviceAllocation> pagingResult = PagingResult.create(query);

        //TODO 设置参数（分页参数除外）

        Long count = cmsDeviceAllocationDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            //query.setSorts(SortColumn.create("create_at", SortMode.DESC));
            List<CmsDeviceAllocation> cmsDeviceAllocationList = cmsDeviceAllocationDao.selectListByParam(query);
            pagingResult.setRows(cmsDeviceAllocationList);
        }

        return pagingResult;
    }
}