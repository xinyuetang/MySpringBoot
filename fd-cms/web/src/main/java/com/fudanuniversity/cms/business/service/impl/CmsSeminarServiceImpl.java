package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.repository.dao.CmsSeminarDao;
import com.fudanuniversity.cms.repository.entity.CmsSeminar;
import com.fudanuniversity.cms.repository.query.CmsSeminarQuery;
import com.fudanuniversity.cms.business.service.CmsSeminarService;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsSeminarService 实现类
 * <p>
 * Created by tidu at 2021-05-02
 */
@Service
public class CmsSeminarServiceImpl implements CmsSeminarService {

    private static final Logger logger = LoggerFactory.getLogger(CmsSeminarServiceImpl.class);

    @Resource
    private CmsSeminarDao cmsSeminarDao;

    /**
     * 保存处理
     */
    @Override
    public void saveCmsSeminar(CmsSeminar cmsSeminar) {
        //TODO 校验与赋值映射

        int affect = cmsSeminarDao.insert(cmsSeminar);
        logger.info("保存CmsSeminar affect:{}, cmsSeminar: {}", affect, cmsSeminar);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsSeminarById(CmsSeminar cmsSeminar) {
        CmsSeminar updater = new CmsSeminar();
        //TODO 值映射校验与赋值映射

        int affect = cmsSeminarDao.updateById(updater);
        logger.info("更新CmsSeminar affect:{}, updater: {}", affect, updater);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsSeminarById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsSeminarDao.deleteById(id);
        logger.info("删除CmsSeminar affect:{}, id: {}", affect, id);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsSeminar> queryPagingResultByParam(CmsSeminarQuery query) {
        PagingResult<CmsSeminar> pagingResult = PagingResult.create(query);

        //TODO 设置参数（分页参数除外）

        Long count = cmsSeminarDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            //query.setSorts(new SortColumn("create_at", SortMode.DESC));
            List<CmsSeminar> cmsSeminarList = cmsSeminarDao.selectListByParam(query);
            pagingResult.setRows(cmsSeminarList);
        }

        return pagingResult;
    }
}