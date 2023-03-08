package cn.wstom.student.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author dws
 * @date 2019/03/12
 */
@Getter
@Setter
public class ArticleCommentVo extends Comment {
    private static final long serialVersionUID = 7316089802322338489L;

    private Comment parent;

    private List<ArticleCommentVo> children;

    private SysUser parentUser;
    /**
     * 评论者信息
     */
    private SysUser user;

    //=================================
    /**
     * 子评论
     */
    private List<ArticleCommentVo> replyComment;
}
