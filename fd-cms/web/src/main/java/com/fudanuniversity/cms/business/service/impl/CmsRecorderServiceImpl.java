package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.repository.dao.CmsRecorderDao;
import com.fudanuniversity.cms.repository.entity.CmsRecorder;
import com.fudanuniversity.cms.repository.query.CmsRecorderQuery;
import com.fudanuniversity.cms.business.service.CmsRecorderService;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * CmsRecorderService 实现类
 * <p>
 * Created by tidu at 2021-05-02
 */
@Service
public class CmsRecorderServiceImpl implements CmsRecorderService {

    private static final Logger logger = LoggerFactory.getLogger(CmsRecorderServiceImpl.class);

    @Resource
    private CmsRecorderDao cmsRecorderDao;

    /**
     * 保存处理
     */
    @Override
    public void saveCmsRecorder(CmsRecorder cmsRecorder) {
        //TODO 校验与赋值映射

        int affect = cmsRecorderDao.insert(cmsRecorder);
        logger.info("保存CmsRecorder affect:{}, cmsRecorder: {}", affect, cmsRecorder);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsRecorderById(CmsRecorder cmsRecorder) {
        CmsRecorder updater = new CmsRecorder();
        //TODO 值映射校验与赋值映射

        int affect = cmsRecorderDao.updateById(updater);
        logger.info("更新CmsRecorder affect:{}, updater: {}", affect, updater);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsRecorderById(Long id) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsRecorderDao.deleteById(id);
        logger.info("删除CmsRecorder affect:{}, id: {}", affect, id);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsRecorder> queryPagingResultByParam(CmsRecorderQuery query) {
        PagingResult<CmsRecorder> pagingResult = PagingResult.create(query);

        //TODO 设置参数（分页参数除外）

        Long count = cmsRecorderDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            //query.setSorts(new SortColumn("create_at", SortMode.DESC));
            List<CmsRecorder> cmsRecorderList = cmsRecorderDao.selectListByParam(query);
            pagingResult.setRows(cmsRecorderList);
        }

        return pagingResult;
    }
}