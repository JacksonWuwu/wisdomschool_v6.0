package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *环境配置
 *
 * @author zzr
 * @date 2019/02/22
 */
@Getter
@ToString
@Setter
public class Outpeizhi extends BaseEntity {
    private static final long serialVersionUID = 22824849907902375L;

    /**
     * 教师课程id
     */
    private String tcId;

    /**
     * 内容
     */
    private String content;


}
