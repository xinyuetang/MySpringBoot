package com.fudanuniversity.cms.business.vo.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 设备分配
 * <p>
 * Created by tidu at 2021-05-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsDeviceAllocationApplyVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备id
     */
    private Long deviceId;

    /**
     * 使用库存
     */
    private Integer inventoryUsage;
}

