package cn.wstom.student.service.impl;


import cn.wstom.student.entity.Forum;
import cn.wstom.student.mapper.ForumMapper;
import cn.wstom.student.mapper.TopicMapper;
import cn.wstom.student.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dws
 * @date 2019/03/31
 */
@Service
public class ForumServiceImpl extends BaseServiceImpl
        <ForumMapper, Forum>
        implements ForumService {

    @Autowired
    private ForumMapper forumMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Override
    public List<Forum> list(Forum forum) {
        StringBuilder sb = new StringBuilder();
        int sort;
        try {
            sort = (int) forum.getParams().get("sort");
        } catch (Exception e) {
            sort = -1;
            sb.append("create_time ASC");
        }
        switch (sort) {
            case 2:
                sb.append("(CASE type ")
                        .append("WHEN 2 THEN 2 ")
                        .append("ELSE 0 END) DESC, t.create_tate DESC");
                break;
            case 3:
                sb.append("(CASE type ")
                        .append("WHEN 2 THEN 2 ")
                        .append("ELSE 0 END) DESC, view DESC");
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

        forum.getParams().put("sort", sb.toString());
        System.out.println("forumsimpl="+forumMapper.selectList(forum));
        return forumMapper.selectList(forum);
    }
}
