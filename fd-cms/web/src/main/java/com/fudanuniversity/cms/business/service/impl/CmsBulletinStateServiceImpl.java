package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.service.CmsBulletinStateService;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsBulletinStateDao;
import com.fudanuniversity.cms.repository.entity.CmsBulletinState;
import com.fudanuniversity.cms.repository.query.CmsBulletinStateQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsBulletinStateService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Service
public class CmsBulletinStateServiceImpl implements CmsBulletinStateService {

    private static final Logger logger = LoggerFactory.getLogger(CmsBulletinStateServiceImpl.class);

    @Resource
    private CmsBulletinStateDao cmsBulletinStateDao;

    /**
     * 保存处理
     */
    @Override
    public void saveCmsBulletinState(CmsBulletinState cmsBulletinState) {
        //TODO 校验与赋值映射

        int affect = cmsBulletinStateDao.insert(cmsBulletinState);
        logger.info("保存CmsBulletinState affect:{}, cmsBulletinState: {}", affect, cmsBulletinState);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsBulletinStateById(CmsBulletinState cmsBulletinState) {
        CmsBulletinState updater = new CmsBulletinState();
        //TODO 值映射校验与赋值映射

        int affect = cmsBulletinStateDao.updateById(updater);
        logger.info("更新CmsBulletinState affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsBulletinStateById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsBulletinStateDao.deleteById(id);
        logger.info("删除CmsBulletinState affect:{}, id: {}", affect, id);
        AssertUtils.state(affect == 1);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsBulletinState> queryPagingResult(CmsBulletinStateQuery query, Paging paging) {
        PagingResult<CmsBulletinState> pagingResult = PagingResult.create(query);

        //TODO 设置参数（分页参数除外）

        Long count = cmsBulletinStateDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(paging.getOffset());
            query.setLimit(paging.getLimit());
            //query.setSorts(SortColumn.create("create_at", SortMode.DESC));
            List<CmsBulletinState> cmsBulletinStateList = cmsBulletinStateDao.selectListByParam(query);
            pagingResult.setRows(cmsBulletinStateList);
        }

        return pagingResult;
    }
}