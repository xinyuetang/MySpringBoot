package com.fudanuniversity.cms.business.vo.study.plan;

import com.fudanuniversity.cms.commons.enums.StudyPlanAllocationStatusEnum;
import com.fudanuniversity.cms.commons.validation.constraints.EnumValue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * 培养方案分配
 * <p>
 * Created by Xinyue.Tang at 2021-05-05 17:59:46
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanAllocationStatusVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    private Long id;

    /**
     * 状态
     */
    @EnumValue(enumClass = StudyPlanAllocationStatusEnum.class, property = "code")
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}

