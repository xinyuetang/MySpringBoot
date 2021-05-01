package com.fudanuniversity.cms.inner.entity.deprecated;

public class Device {
    private Integer id;

    private Integer type;// 设备类型，0：台式设备，1：移动设备， 2：服务器，3：其他

    private String date;// 上一次修改时间

    private Integer userId;// 使用者id

    private String deviceVersion; // 设备型号  仅适用于0，1

    private String personInCharge; // 负责人姓名  仅适用于 2
    private String serverName; // 服务器名字    仅适用于2
    private Integer memory; //使用内存, 以G为单位，仅适用于2

    private String deviceType;// 设备类型， 仅适用于 3
    private String deviceModel;//设备型号，仅适用于 3

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }
}
