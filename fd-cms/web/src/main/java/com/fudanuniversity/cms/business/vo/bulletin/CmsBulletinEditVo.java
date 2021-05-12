package com.fudanuniversity.cms.business.vo.bulletin;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 通知
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Data
@NoArgsConstructor
@ToString
public class CmsBulletinEditVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    private Long id;

    /**
     * 名称
     */
    @NotEmpty(message = "标题不能为空")
    private String title;

    /**
     * 内容
     */
    @NotEmpty(message = "内容不能为空")
    private String content;
}

