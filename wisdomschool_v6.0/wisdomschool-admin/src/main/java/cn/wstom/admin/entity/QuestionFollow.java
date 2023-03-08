package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Setter
@Getter
public class QuestionFollow extends BaseEntity {
    private static final long serialVersionUID = 1L;
    private String questionId;
    private String userId;
}
