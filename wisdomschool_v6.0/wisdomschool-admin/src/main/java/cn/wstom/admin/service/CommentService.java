package cn.wstom.admin.service;

import cn.wstom.admin.entity.Comment;

import java.util.List;

/**
 * @author dws
 * @date 2019/03/07
 */
public interface CommentService extends BaseService<Comment> {
    List<Comment> listByPids(List<String> childrenIds);
}
