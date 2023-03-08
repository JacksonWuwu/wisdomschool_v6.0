package cn.wstom.admin.service;


import cn.wstom.admin.entity.Feed;
import cn.wstom.admin.entity.PageVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author dws
 */
@Service
public interface FeedService extends BaseService<Feed> {

    @Transactional(rollbackFor = Exception.class)
    int addUserFeed(String userId, Integer infoType, String infoId) throws Exception;

    int deleteUserFeed(String userId, Integer infoType, String infoId);

    /**
     * 修改该用户feed信息的审核状态
     *
     * @param infoType
     * @param infoId
     * @param status
     * @return
     */
    int updateuUserFeedById(Integer infoType, String infoId, Integer status);

    /**
     * 按id查询用户是否存在
     *
     * @param userId
     * @param infoType
     * @param infoId
     * @return
     */
    boolean checkUserFeed(String userId, Integer infoType, String infoId);

    PageVo<Feed> getUserListFeedPage(String userId, Integer infoType, int pageNum, int rows);
}
