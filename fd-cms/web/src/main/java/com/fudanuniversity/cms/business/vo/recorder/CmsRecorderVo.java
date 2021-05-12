package com.fudanuniversity.cms.business.vo.recorder;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 演讲记录
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor

@ToString
public class CmsRecorderVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 演讲时间
     */
    private Date date;

    /**
     * 辅读人员1用户id
     */
    private Long recorder1Id;
    private String recorder1StuId;
    private String recorder1Name;

    /**
     * 辅读人员1文件名
     */
    private String recorder1File;
    private String recorder1Type;
    private String recorder1FileUrl;

    /**
     * 辅读人员2用户id
     */
    private Long recorder2Id;
    private String recorder2StuId;
    private String recorder2Name;

    /**
     * 辅读人员2文件名
     */
    private String recorder2File;
    private String recorder2Type;
    private String recorder2FileUrl;

    /**
     * 记录人员用户id
     */
    private Long summarizerId;
    private String summarizerStuId;
    private String summarizerName;

    /**
     * 记录人员文件名
     */
    private String summarizerFile;
    private String summarizerType;
    private String summarizerFileUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date modifyTime;
}

