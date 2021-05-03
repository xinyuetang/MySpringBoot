package com.fudanuniversity.cms.business.service;

import com.fudanuniversity.cms.business.vo.recorder.*;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;

/**
 * CmsRecorderService
 * <p>
 * Created by tidu at 2021-05-02
 */
public interface CmsRecorderService {

    /**
     * 保存处理
     */
    void saveCmsRecorder(CmsRecorderAddVo addVo);

    /**
     * 根据id更新处理
     */
    void updateCmsRecorderById(CmsRecorderUpdateVo updateVo);

    /**
     * 根据id删除处理
     */
    void deleteCmsRecorderById(Long id);

    /**
     * 上传文稿
     */
    void uploadRecorderFile(Long userId, CmsRecorderUploadVo uploadVo);

    /**
     * 分页查询数据列表
     */
    PagingResult<CmsRecorderVo> queryPagingResult(CmsRecorderQueryVo queryVo, Paging paging);

    /**
     * 下载文稿
     */
    CmsRecorderDownloadResultVo downloadRecorderFile(CmsRecorderDownloadVo downloadVo);
}