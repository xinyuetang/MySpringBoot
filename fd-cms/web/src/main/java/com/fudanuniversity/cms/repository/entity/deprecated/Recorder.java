package com.fudanuniversity.cms.repository.entity.deprecated;

public class Recorder {
    private Integer id;

    private String date;//演讲时间， format="yyyy-mm-dd"

    private Integer recorder1ID;//辅读人员1
    private byte[] record1;
    private  String record1FileName;

    private Integer recorder2ID;//辅读人员2
    private byte[] record2;
    private  String record2FileName;

    private Integer summaryerID;//记录人员ID
    private byte[] summary;
    private String summaryFileName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getRecord1() {
        return record1;
    }

    public void setRecord1(byte[] record1) {
        this.record1 = record1;
    }


    public Integer getRecorder2ID() {
        return recorder2ID;
    }

    public void setRecorder2ID(Integer recorder2ID) {
        this.recorder2ID = recorder2ID;
    }

    public byte[] getRecord2() {
        return record2;
    }

    public void setRecord2(byte[] record2) {
        this.record2 = record2;
    }

    public Integer getSummaryerID() {
        return summaryerID;
    }

    public void setSummaryerID(Integer summaryerID) {
        this.summaryerID = summaryerID;
    }

    public byte[] getSummary() {
        return summary;
    }

    public void setSummary(byte[] summary) {
        this.summary = summary;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getRecorder1ID() {
        return recorder1ID;
    }

    public void setRecorder1ID(Integer recorder1ID) {
        this.recorder1ID = recorder1ID;
    }

    public String getRecord1FileName() {
        return record1FileName;
    }

    public void setRecord1FileName(String record1FileName) {
        this.record1FileName = record1FileName;
    }

    public String getRecord2FileName() {
        return record2FileName;
    }

    public void setRecord2FileName(String record2FileName) {
        this.record2FileName = record2FileName;
    }

    public String getSummaryFileName() {
        return summaryFileName;
    }

    public void setSummaryFileName(String summaryFileName) {
        this.summaryFileName = summaryFileName;
    }
}