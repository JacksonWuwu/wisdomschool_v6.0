package cn.wstom.admin.entity;

import cn.wstom.admin.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Setter
@Getter
public class ImagesInfoMerge extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String infoId;
    private String imgId;
    private String userId;
    private String picId;
    private Integer infoType;
}
