package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.repository.dao.CmsBulletinDao;
import com.fudanuniversity.cms.repository.entity.CmsBulletin;
import com.fudanuniversity.cms.repository.query.CmsBulletinQuery;
import com.fudanuniversity.cms.business.service.CmsBulletinService;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsBulletinService 实现类
 * <p>
 * Created by tidu at 2021-05-02
 */
@Service
public class CmsBulletinServiceImpl implements CmsBulletinService {

    private static final Logger logger = LoggerFactory.getLogger(CmsBulletinServiceImpl.class);

    @Resource
    private CmsBulletinDao cmsBulletinDao;

    /**
     * 保存处理
     */
    @Override
    public void saveCmsBulletin(CmsBulletin cmsBulletin) {
        //TODO 校验与赋值映射

        int affect = cmsBulletinDao.insert(cmsBulletin);
        logger.info("保存CmsBulletin affect:{}, cmsBulletin: {}", affect, cmsBulletin);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsBulletinById(CmsBulletin cmsBulletin) {
        CmsBulletin updater = new CmsBulletin();
        //TODO 值映射校验与赋值映射

        int affect = cmsBulletinDao.updateById(updater);
        logger.info("更新CmsBulletin affect:{}, updater: {}", affect, updater);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsBulletinById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsBulletinDao.deleteById(id);
        logger.info("删除CmsBulletin affect:{}, id: {}", affect, id);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsBulletin> queryPagingResultByParam(CmsBulletinQuery query) {
        PagingResult<CmsBulletin> pagingResult = PagingResult.create(query);

        //TODO 设置参数（分页参数除外）

        Long count = cmsBulletinDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            //query.setSorts(new SortColumn("create_at", SortMode.DESC));
            List<CmsBulletin> cmsBulletinList = cmsBulletinDao.selectListByParam(query);
            pagingResult.setRows(cmsBulletinList);
        }

        return pagingResult;
    }
}