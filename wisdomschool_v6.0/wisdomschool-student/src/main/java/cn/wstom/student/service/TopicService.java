package cn.wstom.student.service;





import cn.wstom.student.entity.Topic;

import java.util.List;

/**
 * @author dws
 * @date 2019/03/07
 */
public interface TopicService extends BaseService<Topic> {
    List<Topic> getCourseAllTopic(String tcId, int sortType);
}
