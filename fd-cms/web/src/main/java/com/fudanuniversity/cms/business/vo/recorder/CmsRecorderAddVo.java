package com.fudanuniversity.cms.business.vo.recorder;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@ToString
public class CmsRecorderAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 演讲时间
     */
    private Date date;

    /**
     * 辅读人员1用户学号
     */
    private String recorder1StuId;

    /**
     * 辅读人员2用户学号
     */
    private String recorder2StuId;

    /**
     * 记录人员用户学号
     */
    private String summarizerStuId;
}

