package cn.wstom.admin.entity;


public class PublicQuestions extends BaseEntity {

    /**
     * 学校No
     */
    private String schoolNo;
    /**
     * 系名
     */
    private String departmentName;
    /**
     * 专业名
     */
    private String professionalName;
    /**
     * 科目
     */
    private String subjectsName;
    /**
     * 题型
     */
    private String titleTypeName;
    /**
     * 题目
     */
    private String title;
    /**
     * 选项与答案
     */
    private String publicoptionAnswerArr;

    private String publicoptionAnswerArrContent;

    /**
     * 解析
     */

    private String parsing;

    /**
     * 难度
     */

    private String difficulty;

    /**
     * 年份
     */

    private Integer year;


    /**
     * 题型id
     */

    private String titleTypeId;

    /**
     * 试题最大曝光
     */

    private String qmaxexposure;

    /**
     * 试题已曝光数
     */

    private String qexposed;

    /**
     *状态0：公开、1：不公开、
     */
    private Integer ststatus;

    /**
     *修改者ID
     */
    String updateId;
    /**
     *添加者ID
     */
    String createId;

    /**
     * 题型
     */
    private TitleType titleType;

    /**
     * 专业Id
     */
    String  xzsubjectsId;

    /**
     * 专业
     */
    private Major major;
    /**
     * 系部
     */
    private Department department;
    /**
     * 专业名
     */
    private String majorName;
    /**
     * 章ID
     */
    private String chapterId;
    /**
     * 节ID
     */
    private String jChapterId;
    /**
     * 题型模板
     */
    private  String templateNum;
    public String getSchoolNo() {
        return schoolNo;
    }

    public void setSchoolNo(String schoolNo) {
        this.schoolNo = schoolNo;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public String getSubjectsName() {
        return subjectsName;
    }

    public void setSubjectsName(String subjectsName) {
        this.subjectsName = subjectsName;
    }

    public String getTitleTypeName() {
        return titleTypeName;
    }

    public void setTitleTypeName(String titleTypeName) {
        this.titleTypeName = titleTypeName;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublicoptionAnswerArr() {
        return publicoptionAnswerArr;
    }

    public void setPublicoptionAnswerArr(String publicoptionAnswerArr) {
        this.publicoptionAnswerArr = publicoptionAnswerArr;
    }

    public String getParsing() {
        return parsing;
    }

    public void setParsing(String parsing) {
        this.parsing = parsing;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitleTypeId() {
        return titleTypeId;
    }

    public void setTitleTypeId(String titleTypeId) {
        this.titleTypeId = titleTypeId;
    }

    public String getQmaxexposure() {
        return qmaxexposure;
    }

    public void setQmaxexposure(String qmaxexposure) {
        this.qmaxexposure = qmaxexposure;
    }

    public String getQexposed() {
        return qexposed;
    }

    public void setQexposed(String qexposed) {
        this.qexposed = qexposed;
    }

    public Integer getStstatus() {
        return ststatus;
    }

    public void setStstatus(Integer ststatus) {
        this.ststatus = ststatus;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public TitleType getTitleType() {
        return titleType;
    }

    public void setTitleType(TitleType titleType) {
        this.titleType = titleType;
    }


    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getPublicoptionAnswerArrContent() {
        return publicoptionAnswerArrContent;
    }

    public void setPublicoptionAnswerArrContent(String publicoptionAnswerArrContent) {
        this.publicoptionAnswerArrContent = publicoptionAnswerArrContent;
    }

    public String getXzsubjectsId() {
        return xzsubjectsId;
    }

    public void setXzsubjectsId(String xzsubjectsId) {
        this.xzsubjectsId = xzsubjectsId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getjChapterId() {
        return jChapterId;
    }

    public void setjChapterId(String jChapterId) {
        this.jChapterId = jChapterId;
    }

    public String getTemplateNum() {
        return templateNum;
    }

    public void setTemplateNum(String templateNum) {
        this.templateNum = templateNum;
    }

    @Override
    public String toString() {
        return "PublicQuestions{" +
                "schoolNo='" + schoolNo + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", professionalName='" + professionalName + '\'' +
                ", subjectsName='" + subjectsName + '\'' +
                ", titleTypeName='" + titleTypeName + '\'' +
                ", title='" + title + '\'' +
                ", publicoptionAnswerArr='" + publicoptionAnswerArr + '\'' +
                ", publicoptionAnswerArrContent='" + publicoptionAnswerArrContent + '\'' +
                ", parsing='" + parsing + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", year=" + year +
                ", titleTypeId='" + titleTypeId + '\'' +
                ", qmaxexposure='" + qmaxexposure + '\'' +
                ", qexposed='" + qexposed + '\'' +
                ", ststatus=" + ststatus +
                ", updateId='" + updateId + '\'' +
                ", createId='" + createId + '\'' +
                ", titleType=" + titleType +
                ", xzsubjectsId='" + xzsubjectsId + '\'' +
                ", major=" + major +
                ", department=" + department +
                ", majorName='" + majorName + '\'' +
                ", chapterId='" + chapterId + '\'' +
                ", jChapterId='" + jChapterId + '\'' +
                ", templateNum='" + templateNum + '\'' +
                '}';
    }
}
