package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;


public class PublicOptionAnswer extends BaseEntity {

	/**
	 * 选项
	 */

	private String stoption;

	/**
	 * 答案
	 */

	private String stanswer;

	/**
	 * 学校No
	 */
	private  String  schoolNo;

	/**
	 *修改者ID
	 */
	String updateId;
	/**
	 *添加者ID
	 */
	String createId;
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
	public String toString() {
		return "PublicOptionAnswer{" +
				"stoption='" + stoption + '\'' +
				", stanswer='" + stanswer + '\'' +
				", schoolNo='" + schoolNo + '\'' +
				", updateId='" + updateId + '\'' +
				", createId='" + createId + '\'' +
				'}';
	}
}
