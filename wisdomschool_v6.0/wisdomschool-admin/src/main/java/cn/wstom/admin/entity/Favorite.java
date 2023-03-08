package cn.wstom.admin.entity;


import cn.wstom.admin.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 收藏
 * @author dws
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Favorite extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String userId;

    /**
     *
     */
    private Integer infoType;

    /**
     *
     */
    private String infoId;
}
