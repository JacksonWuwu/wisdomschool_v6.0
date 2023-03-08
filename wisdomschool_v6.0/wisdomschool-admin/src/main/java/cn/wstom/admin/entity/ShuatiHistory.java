package cn.wstom.admin.entity;


import cn.wstom.admin.entity.BaseEntity;
import lombok.Data;

/**
 * 刷题历史表 tk_shuati_history
 *
 * @author lph
 * @date 20191008
 */
@Data
public class ShuatiHistory extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private String id;
    /**
     * 题目Id
     */
    private String titleId;
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 学生答案
     */
    private String stuanswer;




    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getTitleId() {
        return titleId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setStuanswer(String stuanswer) {
        this.stuanswer = stuanswer;
    }

    public String getStuanswer() {
        return stuanswer;
    }





    @Override
    public String toString() {
        return "ShuatiHistory{" +
                "titleId='" + titleId + '\'' +
                ", userId='" + userId + '\'' +
                ", stuanswer='" + stuanswer + '\'' +
                '}';
    }
}
