package cn.wstom.student.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 */
@Setter
@Getter
public class TopicInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long infoId;
    private Long topicId;
    private Long userId;
    private Integer infoType;
}
