package com.fudanuniversity.cms.business.vo.recorder;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;


/**
 * 演讲记录
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor

@ToString
public class CmsRecorderUploadVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 辅读人员1文件名
     */
    private String recorder1File;

    /**
     * 辅读人员1记录内容
     */
    private MultipartFile recorder1Content;

    /**
     * 辅读人员2文件名
     */
    private String recorder2File;

    /**
     * 辅读人员2记录内容
     */
    private MultipartFile recorder2Content;

    /**
     * 记录人员文件名
     */
    private String summarizerFile;

    /**
     * 记录人员记录内容
     */
    private MultipartFile summarizerContent;
}

