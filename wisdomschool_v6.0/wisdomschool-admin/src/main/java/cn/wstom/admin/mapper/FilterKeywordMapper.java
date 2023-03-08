package cn.wstom.admin.mapper;


import cn.wstom.admin.entity.FilterKeyword;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author dws
 */
public interface FilterKeywordMapper extends BaseMapper<FilterKeyword> {

    /**
     * 添加违禁关键词
     *
     * @param keyword 关键词
     * @return
     */
    int addFilterKeyword(FilterKeyword keyword);

    //按id删除违禁关键词
    int deleteFilterKeywordById(@Param("id") Long id);


    //按id查询并更新违禁关键词信息
    int updateFilterKeywordById(@Param("keyword") String keyword, @Param("id") Long id);


    //按id查询违禁关键词信息
    FilterKeyword findFilterKeywordById(@Param("id") Long id);

    /**
     * 查询违禁关键词是否存在
     *
     * @param keyword 关键词
     * @return
     */
    int checkFilterKeyword(@Param("keyword") String keyword);

    /**
     * 查询当前id以外该违禁关键词是否存在
     *
     * @param keyword 关键词
     * @return
     */
    int checkFilterKeywordNotId(@Param("keyword") String keyword, @Param("id") Long id);

    /**
     * 所有违禁关键词列表
     *
     * @return
     */
    List<String> getFilterKeywordAllList();

    //违禁关键词总数
    int getFilterKeywordCount();

    //违禁关键词列表
    List<FilterKeyword> getFilterKeywordList(@Param("offset") Integer offset, @Param("rows") Integer rows);
}
