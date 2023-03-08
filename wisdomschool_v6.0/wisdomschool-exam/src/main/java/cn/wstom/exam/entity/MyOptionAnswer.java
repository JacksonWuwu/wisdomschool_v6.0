package cn.wstom.exam.entity;


import cn.wstom.exam.annotation.Excel;

public class MyOptionAnswer extends BaseEntity {


	/*
	题目
	* */
	private String stoption;

	/**
	 * 答案
	 */
    @Excel(name = "答案")
	private String stanswer;
	/**
	 * 学校No
	 */
	private String  schoolNo;

	/**
	 *修改者Id
	 */
	String updateId;
	/**
	 *添加者ID
	 */
	String createId;
    /**
     * 修改人名
     */
    String updateBy;
    /**
     * 创建人名
     */
    String createBy;
	public String getStoption() {
		return stoption;
	}

	public void setStoption(String stoption) {
		this.stoption = stoption;
	}

	public String getStanswer() {
		return stanswer;
	}

	public void setStanswer(String stanswer) {
		this.stanswer = stanswer;
	}

	public String getSchoolNo() {
		return schoolNo;
	}

	public void setSchoolNo(String schoolNo) {
		this.schoolNo = schoolNo;
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

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

	@Override
	public String toString() {
		return "MyOptionAnswer{" +
				"stoption='" + stoption + '\'' +
				", stanswer='" + stanswer + '\'' +
				", schoolNo='" + schoolNo + '\'' +
				", updateId='" + updateId + '\'' +
				", createId='" + createId + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", createBy='" + createBy + '\'' +
				'}';
	}
}
