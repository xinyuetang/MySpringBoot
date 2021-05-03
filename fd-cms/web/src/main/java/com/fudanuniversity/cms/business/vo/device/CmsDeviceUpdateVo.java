package com.fudanuniversity.cms.business.vo.device;

import com.fudanuniversity.cms.commons.enums.DeviceTypeEnum;
import com.fudanuniversity.cms.commons.validation.constraints.EnumValue;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 设备
 * <p>
 * Created by tidu at 2021-05-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CmsDeviceUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    private Long id;

    /**
     * 设备类型
     */
    @EnumValue(enumClass = DeviceTypeEnum.class, property = "code")
    private Long type;

    /**
     * 设备型号
     */
    private String model;

    /**
     * 设备型号
     */
    private String name;

    /**
     * 负责人姓名s
     */
    private String principal;

    /**
     * 库存
     */
    private Integer inventory;

    /**
     * 库存单位
     */
    private String inventoryUnit;
}

