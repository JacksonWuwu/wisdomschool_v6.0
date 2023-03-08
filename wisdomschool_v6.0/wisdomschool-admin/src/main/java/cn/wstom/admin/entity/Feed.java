package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 */
@Setter
@Getter
@ToString
public class Feed extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;
    /**
     * 信息类型，0问题，1文章，2分享
     */
    private Integer infoType;
    /**
     * 信息id
     */
    private String infoId;

}
