package com.fudanuniversity.cms.repository.query;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Xinyue.Tang at 2021-05-06 13:34:10
 */
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanInfo implements Serializable {

    private Long planId;

    private Long userId;

    private Long total;

    private Long underway;

    private Long delayUnfinished;

    private Long overtimeUnfinished;

    private Long normalFinished;

    private Long delayFinished;

    private Long overtimeFinished;
}
