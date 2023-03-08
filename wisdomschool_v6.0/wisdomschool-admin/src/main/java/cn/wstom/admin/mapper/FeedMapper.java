package cn.wstom.admin.mapper;

import cn.wstom.admin.entity.Feed;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
public interface FeedMapper extends BaseMapper<Feed> {
    /**
     * 添加用户feed信息
     *
     * @param feed
     * @return
     */
    int addUserFeed(Feed feed);

    /**
     * 按用户feed信息
     *
     * @param userId
     * @param infoType
     * @param infoId
     * @return
     */
    int deleteUserFeed(@Param("userId") String userId, @Param("infoType") Integer infoType, @Param("infoId") String infoId);

    /**
     * 修改该用户feed信息的审核状态
     *
     * @param infoType
     * @param infoId
     * @param status
     * @return
     */
    int updateuUserFeedById(@Param("infoType") Integer infoType, @Param("infoId") String infoId, @Param("status") Integer status);

    /**
     * 查询该用户feed是否存在
     *
     * @param userId
     * @param infoType
     * @param infoId
     * @return
     */
    int checkUserFeed(@Param("userId") String userId, @Param("infoType") Integer infoType, @Param("infoId") String infoId);

    /**
     * 查询用户信息流总数
     *
     * @param userId
     * @param status
     * @return
     */
    int getUserFeedCount(@Param("userId") String userId, @Param("status") Integer status);

    /**
     * 查询用户信息流列表
     *
     * @param userId
     * @param status
     * @param offset
     * @param rows
     * @return
     */
    List<Feed> getUserFeedList(@Param("userId") String userId,
                               @Param("infoType") Integer infoType,
                               @Param("offset") int offset,
                               @Param("rows") int rows);
}
