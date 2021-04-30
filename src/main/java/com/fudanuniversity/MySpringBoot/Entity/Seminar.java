package com.fudanuniversity.MySpringBoot.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Seminar {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String theme; //演讲主题

    private String date;//演讲时间， format="yyyy-mm-dd"

    private String link; //演讲资源保存链接地址

    private Integer speakerID;

    public Integer getSpeakerID() {
        return speakerID;
    }

    public void setSpeakerID(Integer speakerID) {
        this.speakerID = speakerID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
