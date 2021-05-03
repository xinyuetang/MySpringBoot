package com.fudanuniversity.cms.business.vo.recorder;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 演讲记录
 * <p>
 * Created by tidu at 2021-05-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsRecorderUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    private Long id;

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

