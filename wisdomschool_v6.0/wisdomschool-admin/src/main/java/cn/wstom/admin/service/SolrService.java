package cn.wstom.admin.service;


import cn.wstom.admin.entity.Info;
import cn.wstom.admin.entity.PageVo;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * @author dws
 * @date 2019/03/07
 */
public interface SolrService {

    @SuppressWarnings("deprecation")
    HttpSolrClient getServer();

    boolean indexQuestionId(String id) throws ParseException;

    boolean indexAllQuestion() throws ParseException;

    boolean indexAllArticle() throws ParseException;

    boolean indexArticleId(String id) throws ParseException;

    /*boolean indexAllShare() throws ParseException;

    boolean indexShareId(String id) throws ParseException;*/

    void indexDeleteInfo(Integer infoType, String infoId);

    void deleteAllInfoindex();

    PageVo<Info> searchInfo(String title, String userId, Integer infoType, List<Integer> excludeInfoType, String categoryId, String notId, String orderby, int page, int rows) throws IOException, SolrServerException, ParseException;

    //企业搜索翻页处理
    String labelPage(
            String fullName,
            String city,
            String industry,
            String scale,
            String capital,
            int page,
            int rows,
            int maxdoc
    ) throws IOException;
}
