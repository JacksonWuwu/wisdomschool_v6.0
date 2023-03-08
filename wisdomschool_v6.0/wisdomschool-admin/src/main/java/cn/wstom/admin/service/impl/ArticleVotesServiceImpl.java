package cn.wstom.admin.service.impl;

import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.ArticleVotesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author dws
 * @date 2019/03/07
 */
@Service
public class ArticleVotesServiceImpl extends BaseServiceImpl
        <ArticleVotesMapper, ArticleVotes>
        implements ArticleVotesService {

    @Resource
    private ArticleVotesMapper articleVotesMapper;
}
