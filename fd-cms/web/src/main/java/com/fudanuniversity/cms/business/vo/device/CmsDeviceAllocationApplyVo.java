package com.fudanuniversity.cms.business.vo.device;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


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
     * id
     */
	private Long id;

    /**
     * 演讲用户id
     */
	private Long userId;

    /**
     * 设备id
     */
	private Long deviceId;

    /**
     * 使用库存
     */
	private Integer inventoryUsage;

    /**
     * 库存单位
     */
	private String inventoryUnit;

    /**
     * 状态
     */
	private Integer status;

    /**
     * 创建时间
     */
	private Date createTime;

    /**
     * 更新时间
     */
	private Date modifyTime;
}

