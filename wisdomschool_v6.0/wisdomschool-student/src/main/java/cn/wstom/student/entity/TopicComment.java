package cn.wstom.student.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author liniec
 * @date 2020/01/26 11:52
 *  评论实体：reply和deck的组合查询
 */
@Getter
@Setter
@ToString
public class TopicComment extends BaseEntity {

    private String topicId;

    private Long thumbsUp;

    private String content;

    private String createName;

    /**
     * 发表文章时所用的IP地址
     */
    private String ipAddr;

    //  评论的类型
    private String type;
}
