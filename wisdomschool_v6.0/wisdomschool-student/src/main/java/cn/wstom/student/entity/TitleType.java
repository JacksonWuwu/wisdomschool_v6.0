package cn.wstom.student.entity;


public class TitleType extends BaseEntity {

	/**
	 * 题型编号
	 */

	private String titleTypeNum;

	/**
	 * 课程题型名称
	 */

	private String name;

	/**
	 * 题型编辑模版
	 */

	private String templateNum;

	/**
	 * 题型备注
	 */

	private String titleTypeBZ;
	/**
	 *修改者ID
	 */
	private String updateId;
	/**
	 *添加者ID
	 */
	private String createId;

	/**
	 * 平台题型名称
	 */
	 private String titleTypeName;
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

	public String getTitleTypeBZ() {
		return titleTypeBZ;
	}

	public void setTitleTypeBZ(String titleTypeBZ) {
		this.titleTypeBZ = titleTypeBZ;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitleTypeName() {
		return titleTypeName;
	}

	public void setTitleTypeName(String titleTypeName) {
		this.titleTypeName = titleTypeName;
	}

	@Override
	public String toString() {
		return "TitleType{" +
				"titleTypeNum='" + titleTypeNum + '\'' +
				", name='" + name + '\'' +
				", templateNum='" + templateNum + '\'' +
				", titleTypeBZ='" + titleTypeBZ + '\'' +
				", updateId='" + updateId + '\'' +
				", createId='" + createId + '\'' +
				", titleTypeName='" + titleTypeName + '\'' +
				'}';
	}
}
