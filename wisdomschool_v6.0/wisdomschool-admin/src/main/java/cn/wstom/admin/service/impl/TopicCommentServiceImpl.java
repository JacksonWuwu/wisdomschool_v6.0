package cn.wstom.admin.service.impl;

import cn.wstom.admin.service.TopicCommentService;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author liniec
 * @date 2020/01/26 12:24
 */
@Service
public class TopicCommentServiceImpl extends BaseServiceImpl<TopicCommentMapper, TopicComment> implements TopicCommentService {
    @Autowired
    private TopicCommentMapper commentMapper;
}
