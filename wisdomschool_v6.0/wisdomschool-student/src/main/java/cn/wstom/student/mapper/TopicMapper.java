package cn.wstom.student.mapper;


import cn.wstom.student.entity.Topic;
import cn.wstom.student.entity.TopicCategory;
import cn.wstom.student.entity.TopicEdit;
import cn.wstom.student.entity.TopicInfo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 *
 */
public interface TopicMapper extends BaseMapper<Topic> {
    //添加话题
    int addTopic(Topic topic);

    /**
     * 添加话题与个信息关联
     *
     * @param infoId   用户id
     * @param topicId  话题id
     * @param infoType 信息类型，0问题，1文章，2分享
     * @param status   信息显示状态，默认为显示，0不显示，1显示
     * @return
     */
    int addTopicAndInfo(@Param("infoId") String infoId, @Param("topicId") String topicId, @Param("infoType") Integer infoType, @Param("status") Integer status);

    /**
     * 添加用户关注的关注的标签id
     *
     * @param userId     用户id
     * @param topicId    话题id
     * @param createTime 添加时间
     * @return
     */
    int addTopicAndUser(@Param("userId") String userId, @Param("topicId") String topicId, @Param("createTime") Date createTime);

    //添加话题分类信息
    int addTopicCategory(TopicCategory topicCategory);

    //用户增加编辑话题信息
    int addUserEditTopic(TopicEdit topicEdit);

    /**
     * 按用户id和话题id删除关注的话题信息
     *
     * @param userId  用户id
     * @param topicId 话题id
     * @return
     */
    int deleteTopicbyUserId(@Param("userId") String userId, @Param("topicId") String topicId);


    /**
     * 按用户id和话题id删除关注的话题信息
     *
     * @param infoId   信息id
     * @param topicId  话题id
     * @param infoType 信息类型
     * @return
     */
    int deleteTopicAndInfo(@Param("infoId") String infoId, @Param("topicId") String topicId, @Param("infoType") Integer infoType);
    // ///////////////////////////////
    // /////        修改      ////////
    // ///////////////////////////////

    /**
     * 按id更新标签信息
     *
     * @param topic
     * @return
     */
    int updateTopicById(Topic topic);

    /**
     * 按id更新话题统计信息
     *
     * @param id
     * @return
     */
    int updateTopicByCount(@Param("id") String id);

    /**
     * 按id更新审核状态
     *
     * @param status
     * @param id
     * @return
     */
    int updateTagStatus(@Param("status") Integer status, @Param("id") String id);

    /**
     * 该话题被关注数量
     *
     * @param id
     * @return
     */
    int updateTopicFollowByCount(@Param("id") String id);

    /**
     * 按话题查询该话题信息
     *
     * @param topic 话题
     * @return
     */
    Topic findTopicByTopic(@Param("topic") String topic);

    /**
     * 按话题查询是否存在
     *
     * @param topic 话题
     * @return
     */
    int checkTopicByTopic(@Param("topic") String topic);

    /**
     * 查询用户下是否该关注标签
     *
     * @param userId  用户id
     * @param topicId 话题id
     * @return
     */
    int checkTopicByUserId(@Param("userId") String userId, @Param("topicId") String topicId);

    /**
     * 查询标签所有数量
     *
     * @return
     */
    int getTopicCount(
            @Param("topic") String topic,
            @Param("type") String type,
            @Param("isgood") Integer isgood,
            @Param("status") Integer status
    );

    /**
     * 查标签列表
     *
     * @param offset
     * @param rows
     * @return
     */
    List<Topic> getTopicList(
            @Param("topic") String topic,
            @Param("type") String type,
            @Param("isgood") Integer isgood,
            @Param("status") Integer status,
            @Param("orderBy") String orderBy,
            @Param("order") String order,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    /**
     * 查标签列表
     *
     * @param offset
     * @param rows
     * @return
     */
    List<Topic> getTopicRandList(
            @Param("topic") String topic,
            @Param("type") String type,
            @Param("isgood") Integer isgood,
            @Param("status") Integer status,
            @Param("orderBy") String orderBy,
            @Param("order") String order,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);

    /**
     * 查询所有tag信息
     *
     * @return
     */
    List<Topic> allWord();

    /**
     * 查询话题关联的信息数量
     *
     * @param infoType 分类id，0问答，1文章，2分享
     * @param topicId  话题id
     * @param status   信息显示状态，默认为显示，0不显示，1显示
     * @return
     */
    int getTopicAndInfoCount(
            @Param("infoType") Integer infoType,
            @Param("topicId") String topicId,
            @Param("status") Integer status);

    /**
     * 查询话题关联的信息list
     *
     * @param infoType 分类id，0问答，1文章，2分享
     * @param topicId  话题id
     * @param status   信息显示状态，默认为显示，0不显示，1显示
     * @param orderBy
     * @param order
     * @param offset
     * @param rows
     * @return
     */
    List<TopicInfo> getTopicAndInfoList(
            @Param("infoType") Integer infoType,
            @Param("topicId") String topicId,
            @Param("status") Integer status,
            @Param("orderBy") String orderBy,
            @Param("order") String order,
            @Param("offset") Integer offset,
            @Param("rows") Integer rows);


    /**
     * 按文章id查询文章所有关联的话题
     *
     * @param infoType 分类id，0问答，1文章，2分享
     * @param infoId   文章id
     * @return
     */
    List<Topic> getInfoByTopicList(@Param("infoType") Integer infoType, @Param("infoId") String infoId);

    /**
     * 按用户id查询所有关注标签数量
     *
     * @param userId
     * @return
     */
    int getUserTagsCount(@Param("user_id") String userId);

    /**
     * 按用户id查询所有关注标签列表
     *
     * @param userId
     * @return
     */
    List<Topic> getUserTagsList(@Param("user_id") String userId,
                                @Param("offset") Integer offset,
                                @Param("rows") Integer rows);
}
