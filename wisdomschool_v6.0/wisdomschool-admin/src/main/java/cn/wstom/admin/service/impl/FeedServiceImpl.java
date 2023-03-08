package cn.wstom.admin.service.impl;

import cn.wstom.admin.service.FeedService;
import cn.wstom.admin.mapper.*;
import cn.wstom.admin.entity.*;
import cn.wstom.admin.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author dws
 */
@Service
public class FeedServiceImpl extends BaseServiceImpl
        <FeedMapper, Feed>
        implements FeedService {

    @Resource
    private TagMapper tagMapper;

    @Resource
    private FeedMapper feedMapper;

    /**
     * 用户注册基本信息
     *
     * @param userId   用户id
     * @param infoType 信息类型：0是问题，1是文章，2是分享
     * @param infoId   用户注册密码
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addUserFeed(String userId, Integer infoType, String infoId) throws Exception {
        if (this.checkUserFeed(userId, infoType, infoId)) {
            throw new Exception("该feed已存在");
        }
        Feed feed = new Feed();
        feed.setUserId(userId);
        feed.setInfoType(infoType);
        feed.setInfoId(infoId);
        return feedMapper.addUserFeed(feed);
    }

    @Override
    public int deleteUserFeed(String userId, Integer infoType, String infoId) {
        return feedMapper.deleteUserFeed(userId, infoType, infoId);
    }

    /**
     * 修改该用户feed信息的审核状态
     *
     * @param infoType
     * @param infoId
     * @param status
     * @return
     */
    @Override
    public int updateuUserFeedById(Integer infoType, String infoId, Integer status) {
        return feedMapper.updateuUserFeedById(infoType, infoId, status);
    }

    @Override
    public boolean checkUserFeed(String userId, Integer infoType, String infoId) {
        int totalCount = feedMapper.checkUserFeed(userId, infoType, infoId);
        return totalCount > 0;
    }

    /**
     * 用户信息流翻页查询
     *
     * @param userId
     * @param pageNum
     * @param rows
     * @return
     * @throws Exception
     */
    @Override
    public PageVo<Feed> getUserListFeedPage(String userId, Integer infoType, int pageNum, int rows) {
        PageVo<Feed> pageVo = new PageVo<>(pageNum);
        pageVo.setRows(rows);
        pageVo.setList(feedMapper.getUserFeedList(userId, infoType, pageVo.getOffset(), pageVo.getRows()));
        pageVo.setCount(feedMapper.getUserFeedCount(userId, infoType));
        return pageVo;
    }
}
