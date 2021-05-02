package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsBulletinComponent;
import com.fudanuniversity.cms.business.service.CmsBulletinService;
import com.fudanuniversity.cms.business.vo.bulletin.*;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.enums.BooleanEnum;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.model.wrapper.TripleTuple;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.repository.dao.CmsBulletinDao;
import com.fudanuniversity.cms.repository.dao.CmsBulletinStateDao;
import com.fudanuniversity.cms.repository.entity.CmsBulletin;
import com.fudanuniversity.cms.repository.entity.CmsBulletinState;
import com.fudanuniversity.cms.repository.query.CmsBulletinQuery;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Resource
    private CmsBulletinComponent cmsBulletinComponent;

    @Resource
    private CmsBulletinStateDao cmsBulletinStateDao;

    /**
     * 保存处理
     */
    @Override
    public void addNewBulletin(CmsBulletinAddVo addVo) {
        CmsBulletin bulletin = new CmsBulletin();
        bulletin.setTitle(addVo.getTitle());
        bulletin.setContent(addVo.getContent());
        bulletin.setCreateTime(new Date());
        int affect = cmsBulletinDao.insert(bulletin);
        logger.info("保存CmsBulletin affect:{}, cmsBulletin: {}", affect, addVo);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void editBulletin(CmsBulletinEditVo editVo) {
        //TODO 补充状态检测业务逻辑
        CmsBulletin updater = new CmsBulletin();
        updater.setId(editVo.getId());
        updater.setTitle(editVo.getTitle());
        updater.setContent(editVo.getContent());
        updater.setModifyTime(new Date());
        int affect = cmsBulletinDao.updateById(updater);
        logger.info("更新CmsBulletin affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsBulletinById(Long bulletinId) {
        //TODO 补充状态检测业务逻辑
        int affect = cmsBulletinDao.deleteById(bulletinId);
        logger.info("删除CmsBulletin affect:{}, id: {}", affect, bulletinId);
        AssertUtils.state(affect == 1);
    }

    @Override
    public void readBulletin(Long userId, Long id) {
        CmsBulletinState bulletinState = cmsBulletinComponent.queryCmsBulletinState(userId, id);
        //存在已读的记录不做操作了
        if (BooleanEnum.isTrue(bulletinState.getRead())) {
            return;
        }
        CmsBulletinState readState = new CmsBulletinState();
        readState.setUserId(userId);
        readState.setBulletinId(id);
        readState.setRead(BooleanEnum.True.getCode());
        readState.setCreateTime(new Date());
        cmsBulletinStateDao.replace(readState);
    }

    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsBulletinVo> queryPagingResult(Long userId, CmsBulletinQueryVo queryVo, Paging paging) {
        PagingResult<CmsBulletinVo> pagingResult = PagingResult.create(paging);

        CmsBulletinQuery query = new CmsBulletinQuery();
        query.setId(queryVo.getId());
        query.setTitle(queryVo.getTitle());
        query.setEltCreateTime(queryVo.getEltCreateTime());
        query.setEgtCreateTime(queryVo.getEgtCreateTime());
        query.setEltModifyTime(queryVo.getEltModifyTime());
        query.setEgtModifyTime(queryVo.getEgtModifyTime());

        Long count = cmsBulletinDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsBulletin> bulletins = cmsBulletinDao.selectListByParam(query);

            List<Long> bulletinIds = Lists.transform(bulletins, CmsBulletin::getId);
            Map<Long, CmsBulletinState> stateMap = cmsBulletinComponent.queryCmsBulletinStateMap(userId, bulletinIds);

            pagingResult.setRows(bulletins, bulletin -> {
                CmsBulletinVo bulletinVo = new CmsBulletinVo();
                bulletinVo.setId(bulletin.getId());
                bulletinVo.setTitle(bulletin.getTitle());
                bulletinVo.setContent(bulletin.getContent());
                CmsBulletinState bulletinState = stateMap.get(bulletin.getId());
                if (bulletinState == null) {
                    bulletinVo.setRead(Boolean.FALSE);
                } else {
                    bulletinVo.setRead(Objects.equals(bulletinState.getRead(), BooleanEnum.True.getCode()));
                }
                bulletinVo.setCreateTime(bulletin.getCreateTime());
                bulletinVo.setModifyTime(bulletin.getModifyTime());
                return bulletinVo;
            });
        }

        return pagingResult;
    }

    @Override
    public CmsBulletinStateVo queryCmsBulletinReadState(Long userId, CmsBulletinStateQueryVo stateQueryVo) {
        CmsBulletinStateVo stateVo = new CmsBulletinStateVo();
        TripleTuple<Long, Long, Long> tuple = cmsBulletinComponent.queryCmsBulletinReadCount(userId, stateQueryVo);
        stateVo.setTotalCount(tuple.getLeft());
        stateVo.setReadCount(tuple.getMiddle());
        stateVo.setUnreadCount(tuple.getRight());
        return stateVo;
    }
}