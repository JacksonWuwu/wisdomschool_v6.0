package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 */
@Setter
@Getter
public class TopicEdit extends BaseEntity {
    private static final long serialVersionUID = 1L;
    //用户id
    private String userId;
    //话题id
    private String topicId;
    //话题编辑内容
    private String content;
    //编辑时间
    private Date createTime;
    //审核状态
    private Integer status;
}
