package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsDeviceService;
import com.fudanuniversity.cms.business.vo.device.*;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.enums.DeletedEnum;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsDeviceAllocationDao;
import com.fudanuniversity.cms.repository.dao.CmsDeviceDao;
import com.fudanuniversity.cms.repository.entity.CmsDevice;
import com.fudanuniversity.cms.repository.entity.CmsDeviceAllocation;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsDeviceAllocationQuery;
import com.fudanuniversity.cms.repository.query.CmsDeviceQuery;
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
 * CmsDeviceService 实现类
 * <p>
 * Created by tidu at 2021-05-03
 */
@Service
public class CmsDeviceServiceImpl implements CmsDeviceService {

    private static final Logger logger = LoggerFactory.getLogger(CmsDeviceServiceImpl.class);

    @Resource
    private CmsDeviceDao cmsDeviceDao;

    @Resource
    private CmsDeviceAllocationDao cmsDeviceAllocationDao;

    @Resource
    private CmsUserComponent cmsUserComponent;

    /**
     * 保存处理
     */
    @Override
    public void saveCmsDevice(CmsDeviceAddVo addVo) {
        CmsDevice cmsDevice = new CmsDevice();
        cmsDevice.setType(addVo.getType());
        cmsDevice.setModel(addVo.getModel());
        cmsDevice.setName(addVo.getName());
        cmsDevice.setPrincipal(addVo.getPrincipal());
        cmsDevice.setInventory(addVo.getInventory());
        cmsDevice.setInventoryUnit(addVo.getInventoryUnit());
        cmsDevice.setDeleted(DeletedEnum.Normal.getCode());
        cmsDevice.setCreateTime(new Date());
        int affect = cmsDeviceDao.insert(cmsDevice);
        logger.info("保存CmsDevice affect:{}, cmsDevice: {}", affect, addVo);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsDeviceById(CmsDeviceUpdateVo updateVo) {
        CmsDevice updater = new CmsDevice();
        updater.setId(updateVo.getId());
        updater.setModel(updateVo.getModel());
        updater.setName(updateVo.getName());
        updater.setPrincipal(updateVo.getPrincipal());
        updater.setInventory(updateVo.getInventory());//TODO 校验已使用库存情况
        updater.setInventoryUnit(updateVo.getInventoryUnit());
        updater.setModifyTime(new Date());
        int affect = cmsDeviceDao.updateById(updater);
        logger.info("更新CmsDevice affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    private CmsDevice queryCmsDevice(Long deviceId) {
        AssertUtils.notNull(deviceId);
        CmsDeviceQuery query = new CmsDeviceQuery();
        query.setId(deviceId);
        query.setDeleted(DeletedEnum.Normal.getCode());
        List<CmsDevice> devices = cmsDeviceDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(devices)) {
            return devices.get(0);
        }
        return null;
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsDeviceById(Long id) {
        //TODO 校验已使用库存情况
        int affect = cmsDeviceDao.deleteById(id);
        logger.info("删除CmsDevice affect:{}, id: {}", affect, id);
        AssertUtils.state(affect == 1);
    }

    @Override
    public PagingResult<CmsDeviceVo> queryPagingResult(CmsDeviceQueryVo queryVo, Paging paging) {
        PagingResult<CmsDeviceVo> pagingResult = PagingResult.create(paging);

        CmsDeviceQuery query = CmsDeviceQuery.listQuery();
        query.setType(queryVo.getType());
        query.setModel(queryVo.getModel());
        query.setName(queryVo.getName());
        query.setPrincipal(queryVo.getPrincipal());
        query.setEltCreateTime(queryVo.getEltCreateTime());
        query.setEgtCreateTime(queryVo.getEgtCreateTime());
        Long count = cmsDeviceDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsDevice> cmsDeviceList = cmsDeviceDao.selectListByParam(query);
            pagingResult.setRows(cmsDeviceList, device -> {
                CmsDeviceVo deviceVo = new CmsDeviceVo();
                deviceVo.setId(device.getId());
                deviceVo.setType(device.getType());
                deviceVo.setModel(device.getModel());
                deviceVo.setPrincipal(device.getPrincipal());
                deviceVo.setId(device.getId());
                deviceVo.setName(device.getName());
                deviceVo.setInventory(device.getInventory());
                deviceVo.setInventoryUnit(device.getInventoryUnit());
                deviceVo.setCreateTime(device.getCreateTime());
                deviceVo.setModifyTime(device.getModifyTime());
                return deviceVo;
            });
        }

        return pagingResult;
    }

    /**
     * 分页查询设备使用情况数据列表
     */
    @Override
    public PagingResult<CmsDeviceUsageVo> queryUsagePagingResult(Long deviceId, Paging paging) {
        CmsDevice cmsDevice = queryCmsDevice(deviceId);
        AssertUtils.notNull(cmsDevice);

        PagingResult<CmsDeviceUsageVo> pagingResult = PagingResult.create(paging);
        CmsDeviceAllocationQuery allocationQuery = CmsDeviceAllocationQuery.listQuery();
        allocationQuery.setDeviceId(deviceId);
        Long count = cmsDeviceAllocationDao.selectCountByParam(allocationQuery);
        pagingResult.setTotal(count);

        if (count > 0L) {
            allocationQuery.setOffset(paging.getOffset());
            allocationQuery.setLimit(paging.getLimit());
            allocationQuery.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsDeviceAllocation> deviceAllocations = cmsDeviceAllocationDao.selectListByParam(allocationQuery);

            List<Long> allocationUserIds = Lists.transform(deviceAllocations, CmsDeviceAllocation::getUserId);
            Map<Long, CmsUser> userMap = cmsUserComponent.queryUsersMap(allocationUserIds);

            pagingResult.setRows(deviceAllocations, deviceAllocation -> {
                CmsDeviceUsageVo deviceUsageVo = new CmsDeviceUsageVo();
                deviceUsageVo.setId(deviceAllocation.getId());
                deviceUsageVo.setUserId(deviceAllocation.getUserId());
                CmsUser allocationUser = userMap.get(deviceAllocation.getUserId());
                if (allocationUser != null) {
                    deviceUsageVo.setUserName(allocationUser.getName());
                }
                deviceUsageVo.setType(cmsDevice.getType());
                deviceUsageVo.setModel(cmsDevice.getModel());
                deviceUsageVo.setName(cmsDevice.getName());
                deviceUsageVo.setInventoryUsage(deviceAllocation.getInventoryUsage());
                deviceUsageVo.setInventoryUnit(cmsDevice.getInventoryUnit());
                deviceUsageVo.setPrincipal(cmsDevice.getPrincipal());
                deviceUsageVo.setStatus(deviceAllocation.getStatus());
                deviceUsageVo.setCreateTime(deviceAllocation.getCreateTime());
                deviceUsageVo.setModifyTime(deviceAllocation.getModifyTime());
                return deviceUsageVo;
            });
        }

        return pagingResult;
    }
}