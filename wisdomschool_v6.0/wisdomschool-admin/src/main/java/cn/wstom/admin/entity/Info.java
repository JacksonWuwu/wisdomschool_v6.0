package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dws
 */
@Setter
@Getter
public class Info extends BaseEntity {
    private static final long serialVersionUID = -3545788899599000862L;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 信息标题
     */
    private String title;
    /**
     * 信息id
     */
    private String infoId;
    /**
     * 信息类型
     */
    private Integer infoType;
    /**
     * 信息分类搜索id
     */
    private String categoryId;
    /**
     * 信息内容
     */
    private String content;

    /**
     * 图片
     */
    private String pic;
}
