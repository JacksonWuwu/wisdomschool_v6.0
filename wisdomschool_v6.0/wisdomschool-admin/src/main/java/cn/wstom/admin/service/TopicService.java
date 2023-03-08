package cn.wstom.admin.service;



import cn.wstom.admin.entity.Topic;

import java.util.List;

/**
 * @author dws
 * @date 2019/03/07
 */
public interface TopicService extends BaseService<Topic> {
    List<Topic> getCourseAllTopic(String tcId, int sortType);
}
