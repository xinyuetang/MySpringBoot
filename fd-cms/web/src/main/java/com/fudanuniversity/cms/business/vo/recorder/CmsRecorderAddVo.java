package com.fudanuniversity.cms.business.vo.recorder;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Date date;

    /**
     * 辅读人员1用户学号
     */
    private Long recorder1Id;

    /**
     * 辅读人员2用户学号
     */
    private Long recorder2Id;

    /**
     * 记录人员用户学号
     */
    private Long summarizerId;
}

