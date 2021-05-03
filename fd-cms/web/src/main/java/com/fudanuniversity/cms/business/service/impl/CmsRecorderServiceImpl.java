package com.fudanuniversity.cms.business.service.impl;

import com.fudanuniversity.cms.business.component.CmsUserComponent;
import com.fudanuniversity.cms.business.service.CmsRecorderService;
import com.fudanuniversity.cms.business.vo.recorder.*;
import com.fudanuniversity.cms.commons.constant.CmsConstants;
import com.fudanuniversity.cms.commons.exception.BusinessException;
import com.fudanuniversity.cms.commons.model.paging.Paging;
import com.fudanuniversity.cms.commons.model.paging.PagingResult;
import com.fudanuniversity.cms.commons.model.query.SortColumn;
import com.fudanuniversity.cms.commons.model.query.SortMode;
import com.fudanuniversity.cms.commons.util.AssertUtils;
import com.fudanuniversity.cms.commons.util.ValueUtils;
import com.fudanuniversity.cms.repository.dao.CmsRecorderDao;
import com.fudanuniversity.cms.repository.entity.CmsRecorder;
import com.fudanuniversity.cms.repository.entity.CmsUser;
import com.fudanuniversity.cms.repository.query.CmsRecorderQuery;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Resource
    private CmsUserComponent cmsUserComponent;

    /**
     * 保存处理
     */
    @Override
    public void saveCmsRecorder(CmsRecorderAddVo addVo) {
        CmsRecorder cmsRecorder = new CmsRecorder();
        cmsRecorder.setDate(addVo.getDate());
        Long recorder1Id = ValueUtils.defaultLong(addVo.getRecorder1Id());
        cmsRecorder.setRecorder1Id(recorder1Id);
        Long recorder2Id = ValueUtils.defaultLong(addVo.getRecorder2Id());
        cmsRecorder.setRecorder2Id(recorder2Id);
        Long summarizerId = ValueUtils.defaultLong(addVo.getSummarizerId());
        cmsRecorder.setSummarizerId(summarizerId);
        cmsRecorder.setCreateTime(new Date());
        int affect = cmsRecorderDao.insert(cmsRecorder);
        logger.info("保存CmsRecorder affect:{}, cmsRecorder: {}", affect, addVo);
    }

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsRecorderById(CmsRecorderUpdateVo updateVo) {
        CmsRecorder updater = new CmsRecorder();
        updater.setId(updateVo.getId());
        updater.setRecorder1Id(updateVo.getRecorder1Id());
        updater.setRecorder2Id(updateVo.getRecorder2Id());
        updater.setSummarizerId(updateVo.getSummarizerId());
        int affect = cmsRecorderDao.updateById(updater);
        logger.info("更新CmsRecorder affect:{}, updater: {}", affect, updater);
    }

    /**
     * 根据id删除处理
     */
    @Override
    public void deleteCmsRecorderById(Long id) {
        int affect = cmsRecorderDao.deleteById(id);
        logger.info("删除CmsRecorder affect:{}, id: {}", affect, id);
    }

    @Override
    public void uploadRecorderFile(Long userId, CmsRecorderUploadVo uploadVo) {
        Long recorderId = uploadVo.getId();
        CmsRecorder cmsRecorder = queryCmsRecorder(recorderId);
        AssertUtils.notNull(cmsRecorder, "当前演讲记录安排已被删除");

        CmsRecorder updater = new CmsRecorder();
        updater.setId(recorderId);

        //上传文件标识，如果没有任何文件抛出错误
        boolean fileUploaded = false;

        String recorder1File = uploadVo.getRecorder1File();
        if (StringUtils.isNotEmpty(recorder1File)) {
            updater.setRecorder1File(recorder1File);
            //每个用户只能上传演讲安排自己的人员文件
            Long recorder1Id = cmsRecorder.getRecorder1Id();
            if (ValueUtils.isLongId(recorder1Id)) {
                AssertUtils.state(userId.equals(recorder1Id), "无权限上传辅读人员1用户文件");
            }
            MultipartFile recorder1MultipartFile = uploadVo.getRecorder1Content();
            String contentType = recorder1MultipartFile.getContentType();
            updater.setRecorder1Type(contentType);
            try {
                byte[] bytes = recorder1MultipartFile.getBytes();
                updater.setRecorder1Content(bytes);
            } catch (IOException e) {
                throw new BusinessException("上传文件出了错误，请稍后重试");
            }
            fileUploaded = true;
        }

        String recorder2File = uploadVo.getRecorder2File();
        if (StringUtils.isNotEmpty(recorder2File)) {
            updater.setRecorder2File(recorder2File);
            Long recorder2Id = cmsRecorder.getRecorder2Id();
            if (ValueUtils.isLongId(recorder2Id)) {
                AssertUtils.state(userId.equals(recorder2Id), "无权限上传辅读人员2用户文件");
            }
            MultipartFile recorder1MultipartFile = uploadVo.getRecorder1Content();
            String contentType = recorder1MultipartFile.getContentType();
            updater.setRecorder1Type(contentType);
            try {
                byte[] bytes = recorder1MultipartFile.getBytes();
                updater.setRecorder2Content(bytes);
            } catch (IOException e) {
                throw new BusinessException("上传文件出了错误，请稍后重试");
            }
            fileUploaded = true;
        }

        String summarizerFile = uploadVo.getSummarizerFile();
        if (StringUtils.isNotEmpty(summarizerFile)) {
            updater.setSummarizerFile(summarizerFile);
            //每个用户只能上传演讲安排自己的人员文件
            Long summarizerId = cmsRecorder.getSummarizerId();
            if (ValueUtils.isLongId(summarizerId)) {
                AssertUtils.state(userId.equals(summarizerId), "无权限上传辅读人员1用户文件");
            }
            MultipartFile recorder1MultipartFile = uploadVo.getRecorder1Content();
            String contentType = recorder1MultipartFile.getContentType();
            updater.setRecorder1Type(contentType);
            try {
                byte[] bytes = recorder1MultipartFile.getBytes();
                updater.setSummarizerContent(bytes);
            } catch (IOException e) {
                throw new BusinessException("上传文件出了错误，请稍后重试");
            }
            fileUploaded = true;
        }

        if (!fileUploaded) {
            throw new BusinessException("未上传任何文件");
        }

        updater.setModifyTime(new Date());
        int affect = cmsRecorderDao.updateById(updater);
        logger.info("更新CmsRecorder affect:{}, updater: {}", affect, updater);
        AssertUtils.state(affect == 1);
    }

    private CmsRecorder queryCmsRecorder(Long recorderId) {
        CmsRecorderQuery query = CmsRecorderQuery.singletonQuery();
        query.setId(recorderId);
        List<CmsRecorder> recorders = cmsRecorderDao.selectListByParam(query);
        if (CollectionUtils.isNotEmpty(recorders)) {
            return recorders.get(0);
        }
        return null;
    }


    /**
     * 分页查询数据列表
     */
    @Override
    public PagingResult<CmsRecorderVo> queryPagingResult(CmsRecorderQueryVo queryVo, Paging paging) {
        PagingResult<CmsRecorderVo> pagingResult = PagingResult.create(paging);

        CmsRecorderQuery query = CmsRecorderQuery.listQuery();
        query.setId(queryVo.getId());
        query.setDate(queryVo.getDate());
        query.setEltCreateTime(queryVo.getEltCreateTime());
        query.setEgtCreateTime(queryVo.getEgtCreateTime());
        query.setEltModifyTime(queryVo.getEltModifyTime());
        query.setEgtModifyTime(queryVo.getEgtModifyTime());
        Long count = cmsRecorderDao.selectCountByParam(query);
        pagingResult.setTotal(count);

        if (count > 0L) {
            query.setOffset(query.getOffset());
            query.setLimit(query.getLimit());
            query.setSorts(SortColumn.create(CmsConstants.CreatedTimeColumn, SortMode.DESC));
            List<CmsRecorder> cmsRecorderList = cmsRecorderDao.selectListByParam(query);

            List<Long> userIds = Lists.newArrayList();
            cmsRecorderList.forEach(recorder -> {
                if (recorder.getRecorder1Id() > 0) {
                    userIds.add(recorder.getRecorder1Id());
                }
                if (recorder.getRecorder2Id() > 0) {
                    userIds.add(recorder.getRecorder2Id());
                }
                if (recorder.getSummarizerId() > 0) {
                    userIds.add(recorder.getSummarizerId());
                }
            });
            Map<Long, CmsUser> userMap = cmsUserComponent.queryUsersMap(userIds);

            pagingResult.setRows(cmsRecorderList, recorder -> {
                CmsRecorderVo recorderVo = new CmsRecorderVo();
                recorderVo.setId(recorder.getId());
                recorderVo.setDate(recorder.getDate());
                recorderVo.setRecorder1Id(recorder.getRecorder1Id());
                recorderVo.setRecorder1File(recorder.getRecorder1File());
                String recorder1FileUrl = buildDownloadUrl(recorder.getId(), "recorder1Id", recorder.getRecorder1Id());
                recorderVo.setRecorder1FileUrl(recorder1FileUrl);
                CmsUser recorder1User = userMap.get(recorder.getRecorder1Id());
                if (recorder1User != null) {
                    recorderVo.setRecorder1Name(recorder1User.getName());
                }
                recorderVo.setRecorder2Id(recorder.getRecorder2Id());
                recorderVo.setRecorder2File(recorder.getRecorder2File());
                String recorder2FileUrl = buildDownloadUrl(recorder.getId(), "recorder2Id", recorder.getRecorder2Id());
                recorderVo.setRecorder2FileUrl(recorder2FileUrl);
                CmsUser recorder2User = userMap.get(recorder.getRecorder2Id());
                if (recorder2User != null) {
                    recorderVo.setRecorder2Name(recorder2User.getName());
                }
                recorderVo.setSummarizerId(recorder.getSummarizerId());
                recorderVo.setSummarizerFile(recorder.getSummarizerFile());
                String summarizerFileUrl = buildDownloadUrl(recorder.getId(), "summarizerId", recorder.getSummarizerId());
                recorderVo.setSummarizerFileUrl(summarizerFileUrl);
                CmsUser summarizerUser = userMap.get(recorder.getSummarizerId());
                if (summarizerUser != null) {
                    recorderVo.setSummarizerName(summarizerUser.getName());
                }
                recorderVo.setCreateTime(recorder.getCreateTime());
                recorderVo.setModifyTime(recorder.getModifyTime());
                return recorderVo;
            });
        }

        return pagingResult;
    }

    private String buildDownloadUrl(Long recorderId, String recorderUserParam, Long recorderUserId) {
        return "/recorder/download?id=" + recorderId + "&" + recorderUserParam + "=" + recorderUserId;
    }

    @Override
    public CmsRecorderDownloadResultVo downloadRecorderFile(CmsRecorderDownloadVo downloadVo) {
        Long recorderId = downloadVo.getId();
        CmsRecorder cmsRecorder = queryCmsRecorder(recorderId);
        AssertUtils.notNull(cmsRecorder, "演讲记录安排为空");
        CmsRecorderDownloadResultVo downloadResultVo = new CmsRecorderDownloadResultVo();
        if (ValueUtils.isLongId(downloadVo.getRecorder1Id())) {
            downloadResultVo.setFileName(cmsRecorder.getRecorder1File());
            downloadResultVo.setFileType(cmsRecorder.getRecorder1Type());
            downloadResultVo.setFileContent(cmsRecorder.getRecorder1Content());
        } else if (ValueUtils.isLongId(downloadVo.getRecorder2Id())) {
            downloadResultVo.setFileName(cmsRecorder.getRecorder2File());
            downloadResultVo.setFileType(cmsRecorder.getRecorder2Type());
            downloadResultVo.setFileContent(cmsRecorder.getRecorder2Content());
        } else if (ValueUtils.isLongId(downloadVo.getSummarizerId())) {
            downloadResultVo.setFileName(cmsRecorder.getSummarizerFile());
            downloadResultVo.setFileType(cmsRecorder.getSummarizerType());
            downloadResultVo.setFileContent(cmsRecorder.getSummarizerContent());
        } else {
            throw new BusinessException("无对应下载的文件");
        }
        return downloadResultVo;
    }
}

