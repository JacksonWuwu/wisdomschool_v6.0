package cn.wstom.student.entity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;


/**
 * 回复
 *
 * @author dws
 */
@Getter
@Setter
@ToString
public class Reply extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * 创建者类型：老师、学生、学校管理员=1，2，3；
     */
    private String userType;
    /**
     * 内容
     */
    private String content;
    /**
     * 发表文章时所用的IP地址
     */
    private String ipAddr;
    /**
     * 所属的主题
     */
    private Topic topic;

    private String tcid;

    /**
     * 0未采纳，1已采纳。
     */
    private Integer adopt = 0;
    /**
     * 楼中楼集合
     */
    private Set<Deck> decks;
    /**
     * 点赞数  因为 数据库默认字段
     */
    private Long thumbsUp;
    private String createName;
    private String modifyName;
}
