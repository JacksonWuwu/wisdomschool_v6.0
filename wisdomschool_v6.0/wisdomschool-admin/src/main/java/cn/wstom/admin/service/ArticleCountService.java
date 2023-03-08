package cn.wstom.admin.service;

import cn.wstom.admin.entity.ArticleCount;


/**
 * @author dws
 * @date 2019/03/07
 */
public interface ArticleCountService extends BaseService<ArticleCount> {
    int updateComment(String targetId, int stepIncrease) throws Exception;

    int updateView(String targetId, int stepIncrease) throws Exception;
}
