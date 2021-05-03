package com.fudanuniversity.cms.business.component;

import com.fudanuniversity.cms.business.vo.bulletin.CmsBulletinStateQueryVo;
import com.fudanuniversity.cms.commons.model.wrapper.TripleTuple;
import com.fudanuniversity.cms.repository.dao.CmsBulletinStateDao;
import com.fudanuniversity.cms.repository.entity.CmsBulletinState;
import com.fudanuniversity.cms.repository.query.CmsBulletinStateQuery;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Xinyue.Tang at 2021-05-03 00:22:19
 */
@Component
public class CmsBulletinComponent {

    @Resource
    private CmsBulletinStateDao bulletinStateDao;

    public CmsBulletinState queryCmsBulletinState(Long userId, Long bulletinId) {
        CmsBulletinStateQuery stateQuery = CmsBulletinStateQuery.singletonQuery();
        stateQuery.setUserId(userId);
        stateQuery.setBulletinId(bulletinId);
        List<CmsBulletinState> bulletinStateList = bulletinStateDao.selectListByParam(stateQuery);
        if (CollectionUtils.isNotEmpty(bulletinStateList)) {
            return bulletinStateList.get(0);
        }
        return null;
    }

    public Map<Long, CmsBulletinState> queryCmsBulletinStateMap(Long userId, List<Long> bulletinIds) {
        if (CollectionUtils.isNotEmpty(bulletinIds)) {
            CmsBulletinStateQuery stateQuery = CmsBulletinStateQuery.listQuery();
            stateQuery.setUserId(userId);
            stateQuery.setBulletinIdList(bulletinIds);
            stateQuery.setLimit(bulletinIds.size());
            List<CmsBulletinState> bulletinStateList = bulletinStateDao.selectListByParam(stateQuery);
            if (CollectionUtils.isNotEmpty(bulletinStateList)) {
                return bulletinStateList.stream().collect(Collectors.toMap(CmsBulletinState::getBulletinId, Function.identity()));
            }
        }
        return Collections.emptyMap();
    }

    public TripleTuple<Long, Long, Long> queryCmsBulletinReadCount(Long userId, CmsBulletinStateQueryVo stateQueryVo) {
        CmsBulletinStateQuery stateQuery = CmsBulletinStateQuery.singletonQuery();
        stateQuery.setUserId(userId);
        stateQuery.setEltCreateTime(stateQueryVo.getEltCreateTime());
        stateQuery.setEgtCreateTime(stateQueryVo.getEgtCreateTime());
        stateQuery.setEltModifyTime(stateQueryVo.getEltModifyTime());
        stateQuery.setEgtModifyTime(stateQueryVo.getEgtModifyTime());
        return bulletinStateDao.queryCmsBulletinReadCount(stateQuery);
    }
}
