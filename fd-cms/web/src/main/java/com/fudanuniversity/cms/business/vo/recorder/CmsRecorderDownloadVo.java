package com.fudanuniversity.cms.business.vo.recorder;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 演讲记录
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsRecorderDownloadVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    private Long id;

    /**
     * 辅读人员1
     */
    @Min(1L)
    private Long recorder1Id;

    /**
     * 辅读人员2
     */
    @Min(1L)
    private Long recorder2Id;

    /**
     * 记录人员文
     */
    @Min(1L)
    private Long summarizerId;
}

