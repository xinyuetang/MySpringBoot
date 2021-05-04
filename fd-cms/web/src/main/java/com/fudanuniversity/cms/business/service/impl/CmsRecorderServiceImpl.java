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
import com.fudanuniversity.cms.commons.util.DateExUtils;
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
import java.util.Objects;

/**
 * CmsRecorderService 实现类
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
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
        Date recorderDate = addVo.getDate();
        CmsRecorderQuery query = CmsRecorderQuery.singletonQuery();
        query.setDate(recorderDate);
        List<CmsRecorder> cmsRecorders = cmsRecorderDao.selectInfoListByParam(query);
        if (CollectionUtils.isNotEmpty(cmsRecorders)) {
            throw new BusinessException("[" + DateExUtils.formatDate(recorderDate) + "]当天已经存在演讲记录安排");
        }

        Long recorder1Id = addVo.getRecorder1Id();
        Long recorder2Id = addVo.getRecorder2Id();
        Long summarizerId = addVo.getSummarizerId();
        Map<Long, CmsUser> userMap = cmsUserComponent
                .queryUserMap(recorder1Id, recorder2Id, summarizerId);

        CmsRecorder cmsRecorder = new CmsRecorder();
        cmsRecorder.setDate(recorderDate);

        if (ValueUtils.isLongId(recorder1Id)) {
            CmsUser recorder1User = userMap.get(recorder1Id);
            AssertUtils.notNull(recorder1User, "辅读人员1[" + recorder1Id + "]的用户不存在");
            cmsRecorder.setRecorder1Id(recorder1User.getId());
        } else {
            cmsRecorder.setRecorder1Id(0L);
        }
        cmsRecorder.setRecorder1File("");
        cmsRecorder.setRecorder1Type("");

        if (ValueUtils.isLongId(recorder2Id)) {
            CmsUser recorder2User = userMap.get(recorder2Id);
            AssertUtils.notNull(recorder2User, "辅读人员2[" + recorder2Id + "]的用户不存在");
            cmsRecorder.setRecorder2Id(recorder2User.getId());
        } else {
            cmsRecorder.setRecorder2Id(0L);
        }
        cmsRecorder.setRecorder2File("");
        cmsRecorder.setRecorder2Type("");

        if (ValueUtils.isLongId(summarizerId)) {
            CmsUser summarizerUser = userMap.get(summarizerId);
            AssertUtils.notNull(summarizerUser, "记录人员[" + summarizerId + "]的用户不存在");
            cmsRecorder.setSummarizerId(summarizerUser.getId());
        } else {
            cmsRecorder.setSummarizerId(0L);
        }
        cmsRecorder.setSummarizerFile("");
        cmsRecorder.setSummarizerType("");

        cmsRecorder.setCreateTime(new Date());
        int affect = cmsRecorderDao.insert(cmsRecorder);
        logger.info("保存CmsRecorder affect:{}, cmsRecorder: {}", affect, addVo);
    }

    private final static byte[] EMPTY_BYTE = new byte[0];

    /**
     * 根据id更新处理
     */
    @Override
    public void updateCmsRecorderById(CmsRecorderUpdateVo updateVo) {
        Long recorderId = updateVo.getId();
        CmsRecorderQuery query = CmsRecorderQuery.singletonQuery();
        query.setId(recorderId);
        List<CmsRecorder> cmsRecorders = cmsRecorderDao.selectInfoListByParam(query);
        if (CollectionUtils.isEmpty(cmsRecorders)) {
            throw new BusinessException("演讲记录安排不存在");
        }
        CmsRecorder cmsRecorder = cmsRecorders.get(0);

        Long recorder1Id = updateVo.getRecorder1Id();
        Long recorder2Id = updateVo.getRecorder2Id();
        Long summarizerId = updateVo.getSummarizerId();
        Map<Long, CmsUser> userMap = cmsUserComponent
                .queryUserMap(recorder1Id, recorder2Id, summarizerId);

        CmsRecorder updater = new CmsRecorder();
        updater.setId(recorderId);

        if (ValueUtils.isLongId(recorder1Id) && !Objects.equals(cmsRecorder.getRecorder1Id(), recorder1Id)) {
            CmsUser recorder1User = userMap.get(recorder1Id);
            AssertUtils.notNull(recorder1User, "辅读人员1[" + recorder1Id + "]的用户不存在");
            updater.setRecorder1Id(recorder1User.getId());
            updater.setRecorder1File("");
            updater.setRecorder1Type("");
            updater.setRecorder1Content(EMPTY_BYTE);
        }

        if (ValueUtils.isLongId(recorder2Id) && !Objects.equals(cmsRecorder.getRecorder2Id(), recorder2Id)) {
            CmsUser recorder2User = userMap.get(recorder2Id);
            AssertUtils.notNull(recorder2User, "辅读人员2[" + recorder2Id + "]的用户不存在");
            updater.setRecorder2Id(recorder2User.getId());
            updater.setRecorder2File("");
            updater.setRecorder2Type("");
            updater.setRecorder2Content(EMPTY_BYTE);
        }

        if (ValueUtils.isLongId(summarizerId) && !Objects.equals(cmsRecorder.getSummarizerId(), summarizerId)) {
            CmsUser summarizerUser = userMap.get(summarizerId);
            AssertUtils.notNull(summarizerUser, "记录人员[" + summarizerId + "]的用户不存在");
            updater.setSummarizerId(summarizerUser.getId());
            updater.setSummarizerFile("");
            updater.setSummarizerType("");
            updater.setSummarizerContent(EMPTY_BYTE);
        }

        updater.setModifyTime(new Date());
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
        CmsRecorder cmsRecorder = queryCmsInfoRecorder(recorderId);
        AssertUtils.notNull(cmsRecorder, "当前演讲记录安排已被删除");

        CmsRecorder updater = new CmsRecorder();
        updater.setId(recorderId);

        //上传文件标识，如果没有任何文件抛出错误
        boolean fileUploaded = false;

        MultipartFile recorder1MultipartFile = uploadVo.getRecorder1Content();
        if (recorder1MultipartFile != null) {
            //每个用户只能上传演讲安排自己的人员文件
            Long recorder1Id = cmsRecorder.getRecorder1Id();
            if (ValueUtils.isLongId(recorder1Id)) {
                AssertUtils.state(userId.equals(recorder1Id), "无权限上传辅读人员1用户文件");
            }
            updater.setRecorder1Id(userId);
            String contentType = recorder1MultipartFile.getContentType();
            updater.setRecorder1Type(contentType);
            String recorder1File = uploadVo.getRecorder1File();
            if (StringUtils.isEmpty(recorder1File)) {
                updater.setRecorder1File(recorder1MultipartFile.getOriginalFilename());
            } else {
                updater.setRecorder1File(recorder1File);
            }
            try {
                byte[] bytes = recorder1MultipartFile.getBytes();
                updater.setRecorder1Content(bytes);
            } catch (IOException e) {
                throw new BusinessException("上传文件出了错误，请稍后重试");
            }
            fileUploaded = true;
        }

        MultipartFile recorder2MultipartFile = uploadVo.getRecorder2Content();
        if (recorder2MultipartFile != null) {
            String recorder2File = uploadVo.getRecorder2File();
            Long recorder2Id = cmsRecorder.getRecorder2Id();
            if (ValueUtils.isLongId(recorder2Id)) {
                AssertUtils.state(userId.equals(recorder2Id), "无权限上传辅读人员2用户文件");
            }
            updater.setRecorder2Id(userId);
            String contentType = recorder2MultipartFile.getContentType();
            updater.setRecorder2Type(contentType);
            if (StringUtils.isEmpty(recorder2File)) {
                updater.setRecorder2File(recorder2MultipartFile.getOriginalFilename());
            } else {
                updater.setRecorder2File(recorder2File);
            }
            try {
                byte[] bytes = recorder2MultipartFile.getBytes();
                updater.setRecorder2Content(bytes);
            } catch (IOException e) {
                throw new BusinessException("上传文件出了错误，请稍后重试");
            }
            fileUploaded = true;
        }

        MultipartFile summarizerMultipartFile = uploadVo.getSummarizerContent();
        if (summarizerMultipartFile != null) {
            //每个用户只能上传演讲安排自己的人员文件
            Long summarizerId = cmsRecorder.getSummarizerId();
            if (ValueUtils.isLongId(summarizerId)) {
                AssertUtils.state(userId.equals(summarizerId), "无权限上传辅读人员1用户文件");
            }
            updater.setSummarizerId(userId);
            String contentType = summarizerMultipartFile.getContentType();
            updater.setSummarizerType(contentType);
            String summarizerFile = uploadVo.getSummarizerFile();
            if (StringUtils.isEmpty(summarizerFile)) {
                updater.setSummarizerFile(summarizerMultipartFile.getOriginalFilename());
            } else {
                updater.setSummarizerFile(summarizerFile);
            }
            try {
                byte[] bytes = summarizerMultipartFile.getBytes();
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

    private CmsRecorder queryCmsDetailRecorder(Long recorderId) {
        CmsRecorderQuery query = CmsRecorderQuery.singletonQuery();
        query.setId(recorderId);
        List<CmsRecorder> recorders = cmsRecorderDao.selectDetailListByParam(query);
        if (CollectionUtils.isNotEmpty(recorders)) {
            return recorders.get(0);
        }
        return null;
    }

    private CmsRecorder queryCmsInfoRecorder(Long recorderId) {
        CmsRecorderQuery query = CmsRecorderQuery.singletonQuery();
        query.setId(recorderId);
        List<CmsRecorder> recorders = cmsRecorderDao.selectInfoListByParam(query);
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
            List<CmsRecorder> cmsRecorderList = cmsRecorderDao.selectInfoListByParam(query);

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
            Map<Long, CmsUser> userMap = cmsUserComponent.queryUserMap(userIds);

            pagingResult.setRows(cmsRecorderList, recorder -> {
                CmsRecorderVo recorderVo = new CmsRecorderVo();
                recorderVo.setId(recorder.getId());
                recorderVo.setDate(recorder.getDate());
                recorderVo.setRecorder1Id(recorder.getRecorder1Id());
                recorderVo.setRecorder1File(recorder.getRecorder1File());
                if (StringUtils.isNotEmpty(recorder.getRecorder1Type())) {
                    String recorder1FileUrl = buildDownloadUrl(recorder.getId(), "recorder1Id", recorder.getRecorder1Id());
                    recorderVo.setRecorder1FileUrl(recorder1FileUrl);
                }
                CmsUser recorder1User = userMap.get(recorder.getRecorder1Id());
                if (recorder1User != null) {
                    recorderVo.setRecorder1StuId(recorder1User.getStuId());
                    recorderVo.setRecorder1Name(recorder1User.getName());
                }
                recorderVo.setRecorder2Id(recorder.getRecorder2Id());
                recorderVo.setRecorder2File(recorder.getRecorder2File());
                if (StringUtils.isNotEmpty(recorder.getRecorder2Type())) {
                    String recorder2FileUrl = buildDownloadUrl(recorder.getId(), "recorder2Id", recorder.getRecorder2Id());
                    recorderVo.setRecorder2FileUrl(recorder2FileUrl);
                }
                CmsUser recorder2User = userMap.get(recorder.getRecorder2Id());
                if (recorder2User != null) {
                    recorderVo.setRecorder2StuId(recorder2User.getStuId());
                    recorderVo.setRecorder2Name(recorder2User.getName());
                }
                recorderVo.setSummarizerId(recorder.getSummarizerId());
                recorderVo.setSummarizerFile(recorder.getSummarizerFile());
                if (StringUtils.isNotEmpty(recorder.getSummarizerType())) {
                    String summarizerFileUrl = buildDownloadUrl(recorder.getId(), "summarizerId", recorder.getSummarizerId());
                    recorderVo.setSummarizerFileUrl(summarizerFileUrl);
                }
                CmsUser summarizerUser = userMap.get(recorder.getSummarizerId());
                if (summarizerUser != null) {
                    recorderVo.setSummarizerStuId(summarizerUser.getStuId());
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
        CmsRecorder cmsRecorder = queryCmsDetailRecorder(recorderId);
        AssertUtils.notNull(cmsRecorder, "演讲记录安排为空");
        CmsRecorderDownloadResultVo downloadResultVo = new CmsRecorderDownloadResultVo();
        if (ValueUtils.isLongId(downloadVo.getRecorder1Id())) {
            byte[] recorder1Content = cmsRecorder.getRecorder1Content();
            if (recorder1Content == null || recorder1Content.length == 0) {
                throw new BusinessException("无对应辅读人员1下载的文件");
            }
            downloadResultVo.setFileName(cmsRecorder.getRecorder1File());
            downloadResultVo.setFileType(cmsRecorder.getRecorder1Type());
            downloadResultVo.setFileContent(recorder1Content);
        } else if (ValueUtils.isLongId(downloadVo.getRecorder2Id())) {
            byte[] recorder2Content = cmsRecorder.getRecorder2Content();
            if (recorder2Content == null || recorder2Content.length == 0) {
                throw new BusinessException("无对应辅读人员2下载的文件");
            }
            downloadResultVo.setFileName(cmsRecorder.getRecorder2File());
            downloadResultVo.setFileType(cmsRecorder.getRecorder2Type());
            downloadResultVo.setFileContent(recorder2Content);
        } else if (ValueUtils.isLongId(downloadVo.getSummarizerId())) {
            byte[] summarizerContent = cmsRecorder.getSummarizerContent();
            if (summarizerContent == null || summarizerContent.length == 0) {
                throw new BusinessException("无对应记录人员下载的文件");
            }
            downloadResultVo.setFileName(cmsRecorder.getSummarizerFile());
            downloadResultVo.setFileType(cmsRecorder.getSummarizerType());
            downloadResultVo.setFileContent(summarizerContent);
        } else {
            throw new BusinessException("无对应下载的文件");
        }
        return downloadResultVo;
    }
}

