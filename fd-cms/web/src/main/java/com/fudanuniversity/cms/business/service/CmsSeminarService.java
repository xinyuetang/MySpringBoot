package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.seminar.CmsSeminarAddVo;
import com.fudanuniversity.cms.business.vo.seminar.CmsSeminarQueryVo;
import com.fudanuniversity.cms.business.vo.seminar.CmsSeminarUpdateVo;
import com.fudanuniversity.cms.business.vo.seminar.CmsSeminarVo;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;

/**
 * CmsSeminarService
 * <p>
 * Created by tidu at 2021-05-02
 */
public interface CmsSeminarService {

    /**
     * 新增处理
     */
    void addNewSeminar(CmsSeminarAddVo seminarVo);

    /**
     * 根据id更新处理
     */
    void updateSeminarById(CmsSeminarUpdateVo seminarUpdateVo);

    /**
     * 查询最近的演讲
     */
    PagingResult<CmsSeminarVo> queryPagingResult(CmsSeminarQueryVo queryVo, Paging paging);

    /**
     * 根据id删除处理
     */
    void deleteCmsSeminarById(Long seminarId);

}