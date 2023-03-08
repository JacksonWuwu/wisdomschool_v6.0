package cn.wstom.admin.service.impl;


import cn.wstom.admin.entity.Comment;
import cn.wstom.admin.mapper.CommentMapper;
import cn.wstom.admin.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dws
 * @date 2019/03/07
 */
@Service
public class CommentServiceImpl extends BaseServiceImpl
        <CommentMapper, Comment>
        implements CommentService {

    @Resource
    private CommentMapper articleCommentMapper;

    @Override
    public List<Comment> listByPids(List<String> childrenIds) {
        return articleCommentMapper.listByPids(childrenIds);
    }
}
