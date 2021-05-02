package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.repository.dao.CmsDeviceDao;
import com.fudanuniversity.cms.repository.entity.CmsDevice;
import com.fudanuniversity.cms.repository.query.CmsDeviceQuery;
import com.fudanuniversity.cms.business.service.CmsDeviceService;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsDeviceService 实现类
 * <p>
 * Created by tidu at 2021-05-02
 */
@Service
public class CmsDeviceServiceImpl implements CmsDeviceService {

    private static final Logger logger = LoggerFactory.getLogger(CmsDeviceServiceImpl.class);

    @Resource
    private CmsDeviceDao cmsDeviceDao;

    /**
     * 保存处理
     */
    @Override
    public void saveCmsDevice(CmsDevice cmsDevice) {
        //TODO 校验与赋值映射

        int affect = cmsDeviceDao.insert(cmsDevice);
        logger.info("保存CmsDevice affect:{}, cmsDevice: {}", affect, cmsDevice);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsDeviceById(CmsDevice cmsDevice) {
        CmsDevice updater = new CmsDevice();
        //TODO 值映射校验与赋值映射

        int affect = cmsDeviceDao.updateById(updater);
        logger.info("更新CmsDevice affect:{}, updater: {}", affect, updater);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsDeviceById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsDeviceDao.deleteById(id);
        logger.info("删除CmsDevice affect:{}, id: {}", affect, id);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsDevice> queryPagingResultByParam(CmsDeviceQuery query) {
        PagingResult<CmsDevice> pagingResult = PagingResult.create(query);

        //TODO 设置参数（分页参数除外）

        Long count = cmsDeviceDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            //query.setSorts(new SortColumn("create_at", SortMode.DESC));
            List<CmsDevice> cmsDeviceList = cmsDeviceDao.selectListByParam(query);
            pagingResult.setRows(cmsDeviceList);
        }

        return pagingResult;
    }
}