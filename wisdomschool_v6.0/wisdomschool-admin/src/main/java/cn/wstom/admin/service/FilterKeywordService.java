package cn.wstom.admin.service;


import cn.wstom.admin.entity.FilterKeyword;

/**
 * @author dws
 * @date 2019/03/07
 */
public interface FilterKeywordService extends BaseService<FilterKeyword> {
    /**
     * 敏感词过滤
     *
     * @param content
     * @return
     */
    String doFilter(String content);
}
