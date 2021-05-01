package com.fudanuniversity.cms.inner.entity.deprecated;


public class User {
    private Integer id;

    private String name; //姓名

    private String studentID; //学号

    private Integer roleID; //权限身份

    private String telephone;//手机

    private String email;//邮箱

    private String mentor;//导师

    private String leader;//汇报人

    private Boolean isKeShuo; //是否科硕

    private Integer type;//就读类型 0-学术型，1-结合型，2-技术型

    private String enrollDate;//入学时间

    private String papers;//论文

    private String patents;//专利

    private String services;//服务

    private String projects;//项目



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;//密码，默认初始化与学号相同


    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String email) {
        this.studentID = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public Boolean getKeShuo() {
        return isKeShuo;
    }

    public void setKeShuo(Boolean keShuo) {
        isKeShuo = keShuo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(String enrollDate) {
        this.enrollDate = enrollDate;
    }

    public String getPapers() {
        return papers;
    }

    public void setPapers(String papers) {
        this.papers = papers;
    }

    public String getPatents() {
        return patents;
    }

    public void setPatents(String patents) {
        this.patents = patents;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }
}