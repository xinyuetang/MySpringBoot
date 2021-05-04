package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.bulletin.*;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;

/**
 * CmsBulletinService
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
public interface CmsBulletinService {

    /**
     * 保存处理
     */
    void addNewBulletin(CmsBulletinAddVo addVo);

    /**
     * 根据id更新处理
     */
    void editBulletin(CmsBulletinEditVo editVo);

    /**
     * 根据id删除处理
     */
    void deleteCmsBulletinById(Long bulletinId);

    /**
     * 读取消息
     */
    void readBulletin(Long userId, Long bulletinId);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsBulletinVo> queryPagingResult(Long userId, CmsBulletinQueryVo queryVo, Paging paging);

    /**
     * 查询通知读取状态数量等信息
     */
    CmsBulletinStateVo queryCmsBulletinReadState(Long userId, CmsBulletinStateQueryVo stateQueryVo);

}