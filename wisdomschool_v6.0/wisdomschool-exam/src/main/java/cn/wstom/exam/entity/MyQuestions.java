package cn.wstom.exam.entity;




import cn.wstom.exam.annotation.Excel;

import java.util.List;


/**
 * 题库表
 * @author hzh
 *
 */

public class MyQuestions extends BaseEntity {



	private List<MyQuestions> myQuestionsList;



	private String title;

	/**
	 * 选项与答案
	 */
	@Excel(name = "答案(必填)")
	private String myoptionAnswerArr;



	private String myoptionAnswerArrContent;
	/**
	 * 解析
	 */
	@Excel(name = "解析")
	private String parsing;

	/**
	 * 难度
	 */
	@Excel(name = "难度等级1~5")
	private String difficulty;

	/**
	 *年份
	 */
	private Integer year;

	/**
	 * 科目
	 */
	private String xzsubjectsId;

	/**
	 * 章
	 */
	//@ManyToOne
	//@JoinColumn(name = "chapterId")
	private String chapterId;

	/**
	 * 节
	 */
	private String jchapterId;


	/**
	 * 题型
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
	 * 分值
	 */
	private int questionscore;

	/**
	 * 题型名称
	 */
	private String titleTypeName;

	/**
	 * 题型编号
	 */
	private String titleTypeNum;

	/**
	 *科目名称
	 */
	private String subjectsName;


	/**
	 *科目Id
	 */
	private String subjectsId;
	/**
	 *章名称
	 */
	private String chapterName;
	/**
	 *节名称
	 */
	private String chapterName2;

	/**
	 *系别名
	 */
	private String departmentName;
	/**
	 *专业名称
	 */
	private String professionalName;
	/**
	 *修改者Id
	 */
	String updateId;
	/**
	 *添加者Id
	 */
	String createId;

	/**
	 * 题型
	 */
	private TitleType titleType;


	/**
	 * 题型模板
	 */
	private String  templateNum;

	/**
	 * 题型模板ID
	 */
	private String pubilcTitleId;


	private String testPaperId;

	public List<MyQuestions> getMyQuestionsList() {
		return myQuestionsList;
	}

	public void setMyQuestionsList(List<MyQuestions> myQuestionsList) {
		this.myQuestionsList = myQuestionsList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMyoptionAnswerArr() {
		return myoptionAnswerArr;
	}

	public void setMyoptionAnswerArr(String myoptionAnswerArr) {
		this.myoptionAnswerArr = myoptionAnswerArr;
	}

	public String getMyoptionAnswerArrContent() {
		return myoptionAnswerArrContent;
	}

	public void setMyoptionAnswerArrContent(String myoptionAnswerArrContent) {
		this.myoptionAnswerArrContent = myoptionAnswerArrContent;
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

	public String getJchapterId() {
		return jchapterId;
	}

	public void setJchapterId(String jchapterId) {
		this.jchapterId = jchapterId;
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

	public int getQuestionscore() {
		return questionscore;
	}

	public void setQuestionscore(int questionscore) {
		this.questionscore = questionscore;
	}

	public String getTitleTypeName() {
		return titleTypeName;
	}

	public void setTitleTypeName(String titleTypeName) {
		this.titleTypeName = titleTypeName;
	}

	public String getSubjectsName() {
		return subjectsName;
	}

	public void setSubjectsName(String subjectsName) {
		this.subjectsName = subjectsName;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public String getChapterName2() {
		return chapterName2;
	}

	public void setChapterName2(String chapterName2) {
		this.chapterName2 = chapterName2;
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


	public String getSubjectsId() {
		return subjectsId;
	}

	public void setSubjectsId(String subjectsId) {
		this.subjectsId = subjectsId;
	}

	public String getTitleTypeNum() {
		return titleTypeNum;
	}

	public void setTitleTypeNum(String titleTypeNum) {
		this.titleTypeNum = titleTypeNum;
	}

	public String getTemplateNum() {
		return templateNum;
	}

	public void setTemplateNum(String templateNum) {
		this.templateNum = templateNum;
	}

	public String getPubilcTitleId() {
		return pubilcTitleId;
	}

	public void setPubilcTitleId(String pubilcTitleId) {
		this.pubilcTitleId = pubilcTitleId;
	}

	public String getTestPaperId() {
		return testPaperId;
	}

	public void setTestPaperId(String testPaperId) {
		this.testPaperId = testPaperId;
	}

	@Override
	public String toString() {
		return "MyQuestions{" +
				"myQuestionsList=" + myQuestionsList +
				", title='" + title + '\'' +
				", myoptionAnswerArr='" + myoptionAnswerArr + '\'' +
				", myoptionAnswerArrContent='" + myoptionAnswerArrContent + '\'' +
				", parsing='" + parsing + '\'' +
				", difficulty='" + difficulty + '\'' +
				", year=" + year +
				", xzsubjectsId='" + xzsubjectsId + '\'' +
				", chapterId='" + chapterId + '\'' +
				", jchapterId='" + jchapterId + '\'' +
				", titleTypeId='" + titleTypeId + '\'' +
				", qmaxexposure='" + qmaxexposure + '\'' +
				", qexposed='" + qexposed + '\'' +
				", ststatus=" + ststatus +
				", questionscore=" + questionscore +
				", titleTypeName='" + titleTypeName + '\'' +
				", titleTypeNum='" + titleTypeNum + '\'' +
				", subjectsName='" + subjectsName + '\'' +
				", subjectsId='" + subjectsId + '\'' +
				", chapterName='" + chapterName + '\'' +
				", chapterName2='" + chapterName2 + '\'' +
				", departmentName='" + departmentName + '\'' +
				", professionalName='" + professionalName + '\'' +
				", updateId='" + updateId + '\'' +
				", createId='" + createId + '\'' +
				", titleType=" + titleType +
				", templateNum='" + templateNum + '\'' +
				", pubilcTitleId='" + pubilcTitleId + '\'' +
				", testPaperId='" + testPaperId + '\'' +
				'}';
	}
}
