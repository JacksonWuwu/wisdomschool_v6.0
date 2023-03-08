package cn.wstom.admin.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author dws
 * @date 2019/04/07
 */
@Getter
@Setter
@ToString
public class CommentVo extends Comment {

    private static final long serialVersionUID = 1501905733731849001L;
    private Comment parent;

    private SysUser user;

    private SysUser parentUser;
}
