package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author dws
 */
@Setter
@Getter
public class ArticleVotes extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 内容类型，0文章，1评论内容
     */
    private Integer infoType;
    /**
     * 信息id
     */
    private String infoId;
    /**
     * 顶
     */
    private Integer dig;
    /**
     * 踩
     */
    private Integer bury;
    /**
     * 添加时间
     */
    private Date createTime;
}
