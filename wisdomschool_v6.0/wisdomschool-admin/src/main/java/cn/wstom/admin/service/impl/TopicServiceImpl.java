/*
 *	Copyright © 2015 Zhejiang SKT Science Technology Development Co., Ltd. All rights reserved.
 *	浙江斯凯特科技发展有限公司 版权所有
 *	http://www.28844.com
 */

package cn.wstom.admin.service.impl;

import cn.wstom.admin.service.TopicService;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 话题服务
 */
@Service
public class TopicServiceImpl extends BaseServiceImpl
        <TopicMapper, Topic>
        implements TopicService {

    @Resource
    private TopicMapper topicMapper;
    @Resource
    private ForumMapper forumMapper;

    @Override
    public List<Topic> list(Topic topic) {

        //sort = 1,2,3,4 ==最新回复|最新发表|最热|精华。
        int sort;
        try {
            sort = (int) topic.getParams().get("sort");
        } catch (Exception e) {
            sort = 0;
        }

        topic.getParams().put("sort", swtichSort(sort));
        return topicMapper.selectList(topic);
    }


    @Override
    public List<Topic> getCourseAllTopic(String tcId, int sortType) {

        //sort = 1,2,3,4 ==最新回复|最新发表|最热|精华。

        List<Topic> list;
        Map<String, Object> params = Maps.newLinkedHashMap();
        Topic topic = new Topic();
        topic.setTcid(tcId);
        topic.setParams(params);
        topic.getParams().put("sort", swtichSort(sortType));
        list = topicMapper.selectList(topic);
        Collections.reverse(list);

        list.forEach(t -> {
            t.setForum(forumMapper.selectById(t.getForumId()));
        });
        return list;
    }

    private String swtichSort(int sortType) {
        StringBuilder sb = new StringBuilder();
        switch (sortType) {
            case 2:
                sb.append("(CASE type ")
                        .append("WHEN 2 THEN 2 ")
                        .append("ELSE 0 END) DESC, t.create_tate DESC");
                break;
            case 3:
                sb.append("(CASE type ")
                        .append("WHEN 2 THEN 2 ")
                        .append("ELSE 0 END) DESC, browse DESC");
                break;
            case 4:
                sb.append("(CASE type ")
                        .append("WHEN 2 THEN 2 ")
                        .append("ELSE 0 END) DESC, update_time DESC");
                break;
            default:
                sb.append("(CASE type ")
                        .append("WHEN 2 THEN 2 ")
                        .append("ELSE 0 END) DESC, update_time DESC");
                break;
        }
        return sb.toString();
    }
}
